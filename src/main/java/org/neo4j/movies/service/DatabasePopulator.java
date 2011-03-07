package org.neo4j.movies.service;

import org.neo4j.kernel.AbstractGraphDatabase;
import org.neo4j.movies.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.graph.neo4j.support.GraphDatabaseContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * @author mh
 * @since 04.03.11
 */
@Service
public class DatabasePopulator {

    @Autowired
    GraphDatabaseContext ctx;
    @Autowired
    MoviesRepository repository;
    private final static Logger log = LoggerFactory.getLogger(DatabasePopulator.class);

    @Transactional
    public List<Movie> populateDatabase() {
        Actor tomHanks = new Actor("1", "Tom Hanks").persist();
        Movie forestGump = new Movie("1", "Forest Gump").persist();
        forestGump.setYear(1994);
        tomHanks.playedIn(forestGump,"Forest");

        User me = new User("micha", "Micha", "password", User.Roles.ROLE_ADMIN,User.Roles.ROLE_USER).persist();
        Rating awesome = me.rate(forestGump, 5, "Awesome");

        User ollie = new User("ollie", "Olliver", "password",User.Roles.ROLE_USER).persist();
        ollie.rate(forestGump, 2, "ok");
        me.addFriend(ollie);
        return asList(forestGump);
    }

    @Transactional
    public void cleanDb() {
        new Neo4jDatabaseCleaner((AbstractGraphDatabase) ctx.getGraphDatabaseService()).cleanDb();
    }
}
