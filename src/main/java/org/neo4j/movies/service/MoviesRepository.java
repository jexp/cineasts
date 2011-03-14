package org.neo4j.movies.service;

import org.neo4j.movies.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.graph.neo4j.finder.FinderFactory;
import org.springframework.data.graph.neo4j.finder.NodeFinder;
import org.springframework.data.graph.neo4j.support.GraphDatabaseContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author mh
 * @since 04.03.11
 */
@Repository
@Transactional
public class MoviesRepository {

    private FinderFactory finderFactory;
    private GraphDatabaseContext graphDatabaseContext;
    protected NodeFinder<Person> peopleFinder;
    protected NodeFinder<Movie> movieFinder;

    @Autowired
    public MoviesRepository(FinderFactory finderFactory, GraphDatabaseContext graphDatabaseContext) {
        this.finderFactory = finderFactory;
        this.graphDatabaseContext = graphDatabaseContext;
        movieFinder = finderFactory.createNodeEntityFinder(Movie.class);
        peopleFinder = finderFactory.createNodeEntityFinder(Person.class);

    }

    public Movie getMovie(String id) {
        return movieFinder.findByPropertyValue("movies", "id", id);
    }

    public List<Movie> findMovies(String query, int max) {
        if (query.isEmpty()) return Collections.emptyList();
        if (max < 1 || max > 1000) max = 100;

        Iterable<Movie> searchResult = movieFinder.findAllByQuery("search", "title", query);
        List<Movie> result=new ArrayList<Movie>(max);
        for (Movie movie : searchResult) {
            result.add(movie);
            if (--max == 0) break;
        }
        return result;
    }

    public Person getPerson(String id) {
        return peopleFinder.findByPropertyValue("people","id",id);
    }

    public Rating rateMovie(Movie movie, User user, int stars, String comment) {
        if (user == null || movie==null) return null;
        return user.rate(movie, stars,comment);
    }
}
