package org.neo4j.movies.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Handles and retrieves the login or denied page depending on the URI template
 */
@Controller
@RequestMapping("/auth")
public class AuthController {

    private final static Logger logger = LoggerFactory.getLogger(AuthController.class);

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(@RequestParam(value = "login_error", required = false) boolean error, Model model) {
        logger.debug("Received request to show login page, error "+error);
        if (error) {
            model.addAttribute("error", "You have entered an invalid username or password!");
        }
        return "/auth/loginpage";
    }

    @RequestMapping(value = "/denied", method = RequestMethod.GET)
    public String denied() {
        logger.debug("Received request to show denied page");
        return "/auth/deniedpage";
    }
}