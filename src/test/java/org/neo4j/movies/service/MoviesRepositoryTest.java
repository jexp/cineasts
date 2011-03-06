package org.neo4j.movies.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.movies.domain.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author mh
 * @since 04.03.11
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/movies-test-context.xml"})
@Transactional
public class MoviesRepositoryTest {
    @Autowired MoviesRepository repository;
    @Test
    public void testGetMovie() throws Exception {
        Movie movie = new Movie("1","Test-Movie").persist();
        Movie found = repository.getMovie("1");
        Assert.assertEquals("movie found by id",movie,found);

    }
}
