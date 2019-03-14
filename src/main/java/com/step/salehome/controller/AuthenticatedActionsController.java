package com.step.salehome.controller;

import com.step.salehome.constants.MessageConstants;
import com.step.salehome.model.Post;
import com.step.salehome.model.User;
import com.step.salehome.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import com.step.salehome.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
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
        model.addAttribute("myPosts", myPosts);

        List<Post> favoritePosts = postService.getMyFavoritePosts(user.getIdUser());
        model.addAttribute("favoritePost", favoritePosts);
        return "view/myPosts";


    }

    @RequestMapping("/delete")
    public String deletePost(@RequestParam("id") int id, Model model){
        postService.deletePost(id);
        model.addAttribute("message", MessageConstants.SUCCESS_DELETE);
        return "view/myPosts";
    }

    @RequestMapping("/addToFavorite/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void addToFavorite(@PathParam("id") int id) {
        User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        postService.addToFavorite(id, user.getIdUser());

    }


}
