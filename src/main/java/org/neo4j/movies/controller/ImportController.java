package org.neo4j.movies.controller;

import org.neo4j.movies.domain.Movie;
import org.neo4j.movies.movieimport.MovieDbImportService;
import org.neo4j.movies.service.CineastsUserDetailsService;
import org.neo4j.movies.service.DatabasePopulator;
import org.neo4j.movies.service.MoviesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author mh
 * @since 04.03.11
 */
@Controller
public class ImportController {

    private MoviesRepository moviesRepository;
    private CineastsUserDetailsService userDetailsService;
    private MovieDbImportService importService;
    private static final Logger log = LoggerFactory.getLogger(ImportController.class);

    @Autowired
    public ImportController(MoviesRepository moviesRepository, MovieDbImportService importService, CineastsUserDetailsService userDetailsService) {
        this.moviesRepository = moviesRepository;
        this.importService = importService;
        this.userDetailsService = userDetailsService;
    }

    // for web service (JSON) clients
    @RequestMapping(value = "/import/{id}", method = RequestMethod.POST)
    public
    @ResponseBody
    Movie getMovie(@PathVariable String id) {
        importService.importMovie(id);
        return moviesRepository.getMovie(id);
    }
}
