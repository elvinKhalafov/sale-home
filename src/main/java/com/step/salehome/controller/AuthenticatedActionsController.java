package com.step.salehome.controller;

import com.step.salehome.model.Post;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class AuthenticatedActionsController {

    @RequestMapping("/post")
    public String addPost(@Valid @ModelAttribute("post") Post post){
        return "redirect/";

    }
}
