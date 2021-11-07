package com.dummy.authentication.controller;

import com.dummy.authentication.entity.UserDetails;
import com.dummy.authentication.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserDetailsService userDetailsService;

    @GetMapping(value = {"/", "/index"})
    public String getHomePage(Model model) {
        return "index";
    }

    @GetMapping(value = "/login")
    public String getLoginPage(Model model) {
        return "login";
    }

    @GetMapping(value = "/logout-success")
    public String getLogoutPage(Model model) {
        return "logout";
    }

    @GetMapping(value = "/user/details")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getUsers(Model model) {
        List<UserDetails> userDetails = this.userDetailsService.getUserDetails();
        model.addAttribute("userDetails", userDetails);
        return "user-details";
    }
}