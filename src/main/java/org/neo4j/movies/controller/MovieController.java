package org.neo4j.movies.controller;

import org.neo4j.movies.domain.Actor;
import org.neo4j.movies.domain.Movie;
import org.neo4j.movies.service.DatabasePopulator;
import org.neo4j.movies.service.MoviesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationProcessingFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

/**
 * @author mh
 * @since 04.03.11
 */
@Controller
public class MovieController {

    private MoviesRepository moviesRepository;
    private DatabasePopulator populator;
    private static final Logger log = LoggerFactory.getLogger(MovieController.class);

    @Autowired
    public MovieController(MoviesRepository moviesRepository, DatabasePopulator populator) {
        this.moviesRepository = moviesRepository;
        this.populator = populator;
    }

    // for web service (JSON) clients

    /**
     * Only matches 'GET /moviies/{id}}' requests for JSON content; a 404 is sent otherwise.
     * TODO send a 406 if an unsupported representation, such as XML, is requested.  See SPR-7353.
     */
    @RequestMapping(value = "/movies/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    public
    @ResponseBody
    Movie getMovie(@PathVariable String id) {
        return moviesRepository.getMovie(id);
    }


    @RequestMapping(value = "/movies/{id}", method = RequestMethod.GET, headers = "Accept=text/html")
    public String singleMovieView(Model model, @PathVariable String id) {
        Movie movie = moviesRepository.getMovie(id);
        model.addAttribute("movie", movie);
        model.addAttribute("id", id);
        return "/movies/show";
    }

    @RequestMapping(value = "/movies", method = RequestMethod.GET, headers = "Accept=text/html")
    public String findMovies(Model model, @RequestParam("q") String query) {
        List<Movie> movies = moviesRepository.findMovies(query, 20);
        model.addAttribute("movies", movies);
        System.out.println("movies = " + movies);
        model.addAttribute("query", query);
        return "/movies/list";
    }

    @RequestMapping(value = "/actors/{id}", method = RequestMethod.GET, headers = "Accept=text/html")
    public String singleActorView(Model model, @PathVariable String id) {
        Actor actor = moviesRepository.getActor(id);
        model.addAttribute("actor", actor);
        model.addAttribute("id", id);
        return "/actors/show";
    }

    @RequestMapping(value = "/admin/populate", method = RequestMethod.GET)
    public String populateDatabase(Model model) {
        Collection<Movie> movies=populator.populateDatabase();
        model.addAttribute("movies",movies);
        return "/movies/list";
    }

    @RequestMapping(value = "/admin/clean", method = RequestMethod.GET)
    public String clean(Model model) {
        populator.cleanDb();
        return "movies/list";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        return "index";
    }

    /**
     * Post a tweet about a session.
     */
/*    @RequestMapping(value="/events/{eventId}/sessions/{sessionId}/tweets", method=RequestMethod.POST)
    public ResponseEntity<String> postSessionTweet(@PathVariable Long eventId, @PathVariable Integer sessionId, @RequestParam String status) {
        return new ResponseEntity<String>(HttpStatus.OK);
    }
*/

}
