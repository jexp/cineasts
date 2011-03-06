package org.neo4j.movies.domain;

import org.neo4j.helpers.collection.IteratorUtil;
import org.springframework.data.annotation.Indexed;
import org.springframework.data.graph.annotation.NodeEntity;
import org.springframework.data.graph.annotation.RelatedTo;
import org.springframework.data.graph.annotation.RelatedToVia;

import java.util.Collection;
import java.util.Set;

/**
 * @author mh
 * @since 04.03.11
 */
@NodeEntity
public class User {
    @Indexed(indexName = "users")
    String login;
    String name;
    String password;

    public User() {
    }

    public User(String login, String name, String password) {
        this.login = login;
        this.name = name;
        this.password = password;
    }

    @RelatedToVia(elementClass = Rating.class, type = "RATED")
    Iterable<Rating> ratings;

    @RelatedTo(elementClass = Movie.class, type = "RATED")
    Set<Movie> favorites;


    @RelatedTo(elementClass = User.class, type = "FRIEND")
    Set<User> friends;

    public void addFriend(User friend) {
        this.friends.add(friend);
    }

    public Rating rate(Movie movie, int stars, String comment) {
        return relateTo(movie, Rating.class, "RATED").rate(stars,comment);
    }

    public Collection<Rating> getRatings() {
        return IteratorUtil.asCollection(ratings);
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", name, login);
    }

    public String getName() {
        return name;
    }

    public Set<User> getFriends() {
        return friends;
    }
}
