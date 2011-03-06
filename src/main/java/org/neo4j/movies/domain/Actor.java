package org.neo4j.movies.domain;

import org.springframework.data.annotation.Indexed;
import org.springframework.data.graph.annotation.NodeEntity;
import org.springframework.data.graph.annotation.RelatedToVia;

/**
 * @author mh
 * @since 04.03.11
 */
@NodeEntity
public class Actor {

    @Indexed(indexName = "actors")
    String id;
    String name;

    public Actor() {
    }

    public Actor(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("%s [%s]", name, id);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @RelatedToVia(elementClass = Role.class, type = "ACTS_IN")
    Iterable<Role> roles;

    public Iterable<Role> getRoles() {
        return roles;
    }

    public Role playedIn(Movie movie, String roleName) {
        Role role = relateTo(movie, Role.class, "ACTS_IN");
        role.setName(roleName);
        return role;
    }
}
