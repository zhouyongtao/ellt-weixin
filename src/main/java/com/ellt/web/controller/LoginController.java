package com.ellt.web.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Irving on 2014/7/6.
 */
@Controller
public class LoginController {

    private static Logger logger = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping(value = "/signin" ,method = RequestMethod.POST)
    public String signIn(String returnUrl) {
        return "login/login";
    }

    @RequestMapping(value = "/login" ,method = RequestMethod.GET)
    public String login() {
        return "login/login";
    }
}
