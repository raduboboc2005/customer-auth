package com.rb.authentication.controller;

import com.rb.authentication.entity.CustomerDetails;
import com.rb.authentication.service.CustomerDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CustomerController {

    @Autowired
    private CustomerDetailsService customerDetailsService;

    @GetMapping(value = {"/", "/index"})
    public String getHomePage(Model model) {
        return "index";
    }

    @GetMapping(value = "/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping(value = "/logout-success")
    public String getLogoutPage() {
        return "logout";
    }

    @GetMapping(value = "/customer/details")
    public String getUsers(Model model) {
        List<CustomerDetails> customerDetails = this.customerDetailsService.getCustomerDetails();
        model.addAttribute("customerDetails", customerDetails);
        return "customer-details";
    }
}