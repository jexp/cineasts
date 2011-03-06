package org.neo4j.movies.domain;

import org.neo4j.helpers.collection.IteratorUtil;
import org.springframework.data.annotation.Indexed;
import org.springframework.data.graph.annotation.NodeEntity;
import org.springframework.data.graph.annotation.RelatedTo;
import org.springframework.data.graph.annotation.RelatedToVia;
import org.springframework.data.graph.core.Direction;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * @author mh
 * @since 04.03.11
 */
@NodeEntity
public class Movie {

    @Indexed(indexName = "movies")
    String id;

    @Indexed(fulltext = true, indexName = "search")
    String title;

    String description;

    int year;

    @RelatedTo(elementClass = Actor.class, type = "ACTS_IN", direction = Direction.INCOMING)
    Set<Actor> actors;

    @RelatedToVia(elementClass = Role.class, type = "ACTS_IN", direction = Direction.INCOMING)
    Iterable<Role> roles;

    @RelatedToVia(elementClass = Rating.class, type = "RATED", direction = Direction.INCOMING)
    Iterable<Rating> ratings;

    public Movie() {
    }

    public Movie(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public Collection<Actor> getActors() {
        return actors;
    }

    public Collection<Role> getRoles() {
        return IteratorUtil.asCollection(roles);
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return String.format("%s (%d) [%s]", title, year, id);
    }

    public String getDescription() {
        return description;
    }

    public float getStars() {
        Iterable<Rating> allRatings = ratings;

        if (allRatings == null) return 0;
        int stars=0, count=0;
        for (Rating rating : allRatings) {
            stars += rating.getStars();
            count++;
        }
        return (float) stars / count;
    }

    public Collection<Rating> getRatings() {
        Iterable<Rating> allRatings = ratings;
        return allRatings == null ? Collections.<Rating>emptyList() : IteratorUtil.asCollection(allRatings);
    }
}

