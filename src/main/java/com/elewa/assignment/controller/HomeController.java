package com.elewa.assignment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String landingPage(Model model) {
        model.addAttribute("companyName", "Duffenschmirtz Evil Incorporated");
        model.addAttribute("companyDetails", "Offering rewards for bringing Perry the Platipus");
        return "landing";
    }
}
