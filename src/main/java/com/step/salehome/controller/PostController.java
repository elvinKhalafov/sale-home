package com.step.salehome.controller;


import com.step.salehome.model.Post;
import com.step.salehome.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;

@Controller
public class PostController {


    @Autowired
    private PostService postService;



    @RequestMapping("/post/{id}")
    public String getPostByid(Model model, @PathParam("id") int id){
        Post post = postService.getPostById(id);
        model.addAttribute("post", post);

        return "view/pre";
    }




}
