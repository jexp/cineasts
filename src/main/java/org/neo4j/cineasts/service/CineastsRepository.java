package org.neo4j.cineasts.service;

import org.neo4j.cineasts.domain.Movie;
import org.neo4j.cineasts.domain.Person;
import org.neo4j.cineasts.domain.Rating;
import org.neo4j.cineasts.domain.User;
import org.neo4j.cineasts.repository.MovieRepository;
import org.neo4j.cineasts.repository.PersonRepository;
import org.neo4j.graphdb.*;
import org.neo4j.graphdb.traversal.Evaluation;
import org.neo4j.graphdb.traversal.Evaluator;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.kernel.Traversal;
import org.neo4j.kernel.Uniqueness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.neo4j.graphdb.Direction.OUTGOING;
import static org.neo4j.graphdb.DynamicRelationshipType.withName;

/**
 * @author mh
 * @since 04.03.11
 */
@Repository
@Transactional
public class CineastsRepository {

    protected PersonRepository personRepository;
    protected MovieRepository movieRepository;

    @Autowired
    public CineastsRepository(MovieRepository movieRepository, PersonRepository personRepository) {
        this.movieRepository = movieRepository;
        this.personRepository = personRepository;
    }

    public Movie getMovie(String id) {
        return movieRepository.findByPropertyValue(null, "id", id);
    }

    public List<Movie> findMovies(String query, int max) {
        if (query.isEmpty()) return Collections.emptyList();
        if (max < 1 || max > 1000) max = 100;

        Iterable<Movie> searchResult = movieRepository.findAllByQuery("search", "title", query);
        List<Movie> result=new ArrayList<Movie>(max);
        for (Movie movie : searchResult) {
            result.add(movie);
            if (--max == 0) break;
        }
        return result;
    }

    public Person getPerson(String id) {
        return personRepository.findByPropertyValue(null,"id",id);
    }

    public Rating rateMovie(Movie movie, User user, int stars, String comment) {
        if (user == null || movie==null) return null;
        return user.rate(movie, stars,comment);
    }

    public Map<Movie,Integer> recommendMovies(User user, final int ratingDistance) {
        final MovieRecommendations movieRecommendations = new MovieRecommendations(movieRepository, ratingDistance);
        return movieRecommendations.getRecommendationsFor(user);
    }

    private static class MovieRecommendations implements Evaluator {
        private final static RelationshipType RATED = withName(User.RATED);
        private final Map<Long,int[]> ratings=new HashMap<Long, int[]>();
        private final int ratingDistance;
        private MovieRepository movieRepository;

        public MovieRecommendations(MovieRepository movieRepository, int ratingDistance) {
            this.movieRepository = movieRepository;
            this.ratingDistance = ratingDistance;
        }

        public Map<Movie, Integer> getRecommendationsFor(User user) {
            TraversalDescription traversal= Traversal.description().breadthFirst()
                    .uniqueness(Uniqueness.NODE_GLOBAL)
                    .relationships(withName(User.FRIEND))
                    .relationships(RATED, OUTGOING)
                    .evaluator(this);
            return averageRecommendations(user, traversal);
        }

        private Map<Movie, Integer> averageRecommendations(User user, TraversalDescription traversal) { // todo sort, limit
            Map<Movie,Integer> result=new HashMap<Movie, Integer>();
            for (Movie movie : movieRepository.findAllByTraversal(user, traversal)) {
                final int[] rating = ratings.get(movie.getNodeId());
                result.put(movie, rating[1]==0 ? 0 : rating[0]/rating[1]);
            }
            return result;
        }

        @Override
        public Evaluation evaluate(Path path) {
            final int distance = path.length() - 1;
            if (distance > ratingDistance) return Evaluation.EXCLUDE_AND_PRUNE;
            Relationship rated = path.lastRelationship();
            if (rated != null && rated.getType().equals(RATED)) {
                if (distance == 0) return Evaluation.EXCLUDE_AND_PRUNE; // my rated movies
                updateRating(rated, distance);
                return Evaluation.INCLUDE_AND_PRUNE;
            }
            return Evaluation.EXCLUDE_AND_CONTINUE;
        }

        private void updateRating(Relationship rated, int distance) {
            final long movieId = rated.getEndNode().getId();
            int[] rating = obtainRating(movieId);

            int weight = ratingDistance - distance;
            final Integer stars = (Integer) rated.getProperty("stars", 0);
            rating[0] += weight * stars;
            rating[1] += weight;
        }

        private int[] obtainRating(long movieId) {
            if (!ratings.containsKey(movieId)) {
                ratings.put(movieId,new int[2]);
            }
            return ratings.get(movieId);
        }
    }
}
