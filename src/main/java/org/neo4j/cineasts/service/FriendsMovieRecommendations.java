package org.neo4j.cineasts.service;

import org.neo4j.cineasts.domain.Movie;
import org.neo4j.cineasts.domain.Rating;
import org.neo4j.cineasts.domain.User;
import org.neo4j.cineasts.repository.MovieRepository;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.PropertyContainer;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.traversal.Evaluation;
import org.neo4j.graphdb.traversal.Evaluator;
import org.neo4j.graphdb.traversal.Evaluators;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.helpers.collection.IterableWrapper;
import org.neo4j.kernel.Traversal;
import org.neo4j.kernel.Uniqueness;
import org.springframework.data.graph.neo4j.support.EntityPath;
import org.springframework.data.graph.neo4j.support.GraphDatabaseContext;

import java.util.HashMap;
import java.util.Map;

import static org.neo4j.graphdb.Direction.OUTGOING;
import static org.neo4j.graphdb.DynamicRelationshipType.withName;

/**
 * @author mh
 * @since 04.04.11
 */
public class FriendsMovieRecommendations {
    private final Map<Movie, MovieRating> ratings = new HashMap<Movie, MovieRating>();
    private final int ratingDistance;

    public static class MovieRating {
        int stars;
        int count;
        public void update(Rating rating, int weight) {
            this.stars += rating.getStars()*weight;
            this.count += weight;
        }
        public int average() {
            return count == 0 ? 0 : stars / count;
        }
        public String toString() {
            return String.valueOf(average());
        }

    }
    public FriendsMovieRecommendations(int ratingDistance) {
        this.ratingDistance = ratingDistance;
    }

    public Map<Movie, ?> getRecommendationsFor(User user) {
        TraversalDescription traversal = Traversal.description().breadthFirst()
                .uniqueness(Uniqueness.NODE_GLOBAL).relationships(withName(User.FRIEND))
                .evaluator(Evaluators.toDepth(ratingDistance)).evaluator(Evaluators.excludeStartPosition());

        Iterable<EntityPath<User,User>> friends = user.findAllPathsByTraversal(traversal);
        for (EntityPath<User,User> path : friends) {
            int weight = ratingDistance - path.length();
            User friend = path.endEntity();
            aggregateRatings(friend,weight);
        }
        for (Rating rating : user.getRatings()) {
            ratings.remove(rating.getMovie());
        }
        return ratings;
    }

    private void aggregateRatings(User friend, int weight) {
        for (Rating rating : friend.getRatings()) {
            obtainRating(rating.getMovie()).update(rating, weight);
        }
    }

    private MovieRating obtainRating(Movie movie) {
        if (!ratings.containsKey(movie)) {
            ratings.put(movie, new MovieRating());
        }
        return ratings.get(movie);
    }
}
