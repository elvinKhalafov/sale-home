package com.step.salehome.controller;

import com.step.salehome.constants.MessageConstants;
import com.step.salehome.model.Post;
import com.step.salehome.model.User;
import com.step.salehome.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import com.step.salehome.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/user")
public class AuthenticatedActionsController {

    @Autowired
    private PostService postService;

    @RequestMapping("/post")
    public String addPost(@Valid @ModelAttribute("post") Post post){
        return "redirect/";
    }

    @RequestMapping("/myPosts")
    public String myPosts(Model model){
        User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Post> myPosts = postService.getmyPosts(user.getIdUser());
        System.out.println("Size"+myPosts.size());
        model.addAttribute("myPosts", myPosts);
        return "view/myPosts";


    }

    @RequestMapping("/delete")
    public String deletePost(@RequestParam("id") int id, Model model){
        postService.deletePost(id);
        model.addAttribute("message", MessageConstants.SUCCESS_DELETE);
        return "view/myPosts";
    }
}
