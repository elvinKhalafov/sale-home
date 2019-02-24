package com.step.salehome.controller;


import com.step.salehome.model.Post;
import com.step.salehome.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private PostService postService;

    @RequestMapping("/")
    public String index(Model model) {
        List<Post> postList = postService.getRecentlyPost();
        model.addAttribute("postList", postList);
        System.out.println(postList);
        return "view/home";
    }

}
