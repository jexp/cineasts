package org.neo4j.movies.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.graph.neo4j.finder.FinderFactory;
import org.springframework.data.graph.neo4j.finder.NodeFinder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

/**
 * @author mh
 * @since 04.03.11
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/movies-test-context.xml"})
@Transactional
public class DomainTest {

    @Autowired
    FinderFactory finderFactory;

    @Test
    public void actorCanPlayARoleInAMovie() {
        Actor tomHanks = new Actor("1","Tom Hanks").persist();
        Movie forestGump = new Movie("1", "Forest Gump").persist();
        forestGump.setYear(1994);

        Role role = tomHanks.playedIn(forestGump, "Forest");

        NodeFinder<Movie> movieFinder = finderFactory.createNodeEntityFinder(Movie.class);
        Movie foundForestGump = movieFinder.findByPropertyValue("movies", "id", "1");

        assertEquals("created and looked up movie equal", forestGump, foundForestGump);
        Role firstRole = foundForestGump.getRoles().iterator().next();
        assertEquals("role forest",role, firstRole);
        assertEquals("role forest","Forest", firstRole.getName());
    }

    @Test
    public void userCanRateMovie() {
        Movie movie= new Movie("1","Forest Gump").persist();
        User user = new User("ich","Micha","password").persist();
        Rating awesome = user.rate(movie, 5, "Awesome");

        NodeFinder<User> userFinder = finderFactory.createNodeEntityFinder(User.class);

        User foundUser = userFinder.findByPropertyValue("users", "login", "ich");
        Rating rating = user.getRatings().iterator().next();
        assertEquals(awesome,rating);
        assertEquals("Awesome",rating.getComment());
        assertEquals(5,rating.getStars());
        assertEquals(5,movie.getStars(),0);
    }
}
