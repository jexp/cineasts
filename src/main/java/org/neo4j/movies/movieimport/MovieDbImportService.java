package org.neo4j.movies.movieimport;

import org.neo4j.movies.domain.*;
import org.neo4j.movies.service.MoviesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Map;

@Service
public class MovieDbImportService {

    private static final Logger logger = LoggerFactory.getLogger(MovieDbImportService.class);
    MovieDbJsonMapper movieDbJsonMapper = new MovieDbJsonMapper();

    @Autowired
    MoviesRepository moviesRepository;

    @Autowired
    MovieDbApiClient client;

    @Autowired
    MovieDbLocalStorage localStorage;


    @Transactional
    public Movie importMovie(String movieId) {
        logger.debug("Importing movie " + movieId);

        Movie movie = moviesRepository.getMovie(movieId);
        if (movie == null) { // Not found: Create fresh
            movie = new Movie(movieId,null);
        }

        Map data = loadMovieData(movieId);

        movieDbJsonMapper.mapToMovie(data, movie);
        movie.persist();
        relatePersonsToMovie(movie, data);
        return movie;
    }

    private Map loadMovieData(String movieId) {
        Map movieJson;
        if (localStorage.hasMovie(movieId)) {
            movieJson = localStorage.loadMovie(movieId);
        } else {
            movieJson = client.getMovie(movieId);
            localStorage.storeMovie(movieId, movieJson);
        }
        return movieJson;
    }

    private void relatePersonsToMovie(Movie movie, Map data) {
        Collection<Map> cast = (Collection<Map>) data.get("cast");
        for (Map entry : cast) {
            String id = "" + entry.get("id");
            Roles job = movieDbJsonMapper.mapToRole((String) entry.get("job"));
            switch (job) {
                case DIRECTED:
                    Director director = importPerson(id, new Director());
                    director.directed(movie);
                    break;
                case ACTS_IN:
                    Actor actor = importPerson(id, new Actor());
                    actor.playedIn(movie, (String) entry.get("character"));
                    break;
            }
        }
    }

    @Transactional
    public <T extends Person> T importPerson(String personId, T newPerson) {
        logger.debug("Importing person " + personId);
        T person = (T) moviesRepository.getPerson(personId);
        if (person!=null) return person;
        Map data = loadPersonData(personId);
        movieDbJsonMapper.mapToPerson(data, newPerson);
        return newPerson.persist();
    }

    private Map loadPersonData(String personId) {
        if (!localStorage.hasPerson(personId)) {
            Map data = client.getPerson(personId);
            localStorage.storePerson(personId, data);
        }
        return localStorage.loadPerson(personId);
    }
}
