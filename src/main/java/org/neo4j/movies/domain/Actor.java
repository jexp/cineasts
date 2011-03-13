package org.neo4j.movies.domain;

import org.springframework.data.annotation.Indexed;
import org.springframework.data.graph.annotation.NodeEntity;
import org.springframework.data.graph.annotation.RelatedToVia;

/**
 * @author mh
 * @since 04.03.11
 */
public class Actor extends Person {


    public Actor() {
    }

    public Actor(String id, String name) {
        super(id,name);
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
