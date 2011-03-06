package org.neo4j.movies.domain;

import org.springframework.data.graph.annotation.EndNode;
import org.springframework.data.graph.annotation.RelationshipEntity;
import org.springframework.data.graph.annotation.StartNode;
import org.springframework.persistence.RelatedEntity;

/**
 * @author mh
 * @since 04.03.11
 */
@RelationshipEntity
public class Rating {
    @StartNode
    User user;
    @EndNode Movie movie;

    int stars;
    String comment;

    public User getUser() {
        return user;
    }

    public Movie getMovie() {
        return movie;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Rating rate(int stars, String comment) {
        this.stars=stars;
        this.comment = comment;
        return this;
    }
}
