package org.neo4j.movies.service;

import org.neo4j.movies.domain.Actor;
import org.neo4j.movies.domain.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.graph.neo4j.finder.FinderFactory;
import org.springframework.data.graph.neo4j.finder.NodeFinder;
import org.springframework.data.graph.neo4j.support.GraphDatabaseContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author mh
 * @since 04.03.11
 */
@Repository
@Transactional
public class MoviesRepository {

    private FinderFactory finderFactory;
    private GraphDatabaseContext graphDatabaseContext;
    protected NodeFinder<Actor> actorFinder;
    protected NodeFinder<Movie> movieFinder;

    @Autowired
    public MoviesRepository(FinderFactory finderFactory, GraphDatabaseContext graphDatabaseContext) {
        this.finderFactory = finderFactory;
        this.graphDatabaseContext = graphDatabaseContext;
        movieFinder = finderFactory.createNodeEntityFinder(Movie.class);
        actorFinder = finderFactory.createNodeEntityFinder(Actor.class);

    }

    public Movie getMovie(String id) {
        return movieFinder.findByPropertyValue("movies", "id", id);
    }

    public Actor getActor(String id) {
        return actorFinder.findByPropertyValue("actors","id",id);
    }
}
