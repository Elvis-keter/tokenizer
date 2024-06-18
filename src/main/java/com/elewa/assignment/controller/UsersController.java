package com.elewa.assignment.controller;

import com.elewa.assignment.model.Users;
import com.elewa.assignment.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class UsersController {

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    private UsersService usersService;

    @GetMapping
    public String showSignUpForm(Model model) {
        model.addAttribute("user", new Users());
        return "signup";
    }

    @PostMapping
    public String signUp(@ModelAttribute Users users) {
        users.setRole("EMPLOYEE"); // Default role
        usersService.saveUser(users);
        return "redirect:/login"; // Redirect to login page after successful sign-up
    }
}
