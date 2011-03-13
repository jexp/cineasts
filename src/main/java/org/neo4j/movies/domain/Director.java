package org.neo4j.movies.domain;

import org.springframework.data.annotation.Indexed;
import org.springframework.data.graph.annotation.NodeEntity;
import org.springframework.data.graph.annotation.RelatedTo;
import org.springframework.data.graph.annotation.RelatedToVia;

import java.util.Set;

/**
 * @author mh
 * @since 04.03.11
 */
public class Director extends Person {

    public Director() {
    }

    public Director(String id, String name) {
        super(id,name);
    }

    @Override
    public String toString() {
        return String.format("%s [%s]", name, id);
    }

    @RelatedTo(elementClass = Movie.class, type = "DIRECTED")
    Set<Movie> movies;

    public Set<Movie> getMovies() {
        return movies;
    }

    public void directed(Movie movie) {
        this.movies.add(movie);
    }
}
