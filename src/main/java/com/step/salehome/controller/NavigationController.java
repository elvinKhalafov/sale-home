package com.step.salehome.controller;

import com.step.salehome.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class NavigationController {

    @RequestMapping("/post")
    public String openPostPage(){


        return "view/pre";
    }

    @GetMapping("/register")
    public String openRegisterPage(Model model){
        model.addAttribute("user" , new User());
        model.addAttribute("message", model.asMap().get("message"));
        return "view/register";
    }

    @RequestMapping("/search")
    public String openSearchPage() {


        return "/view/search";
    }

    @RequestMapping("/add-post")
    public String openAddPostPage() {

        return "/view/post";
    }

    @RequestMapping("/login")
    public String openLoginPage() {

        return "/view/login";
    }


}
