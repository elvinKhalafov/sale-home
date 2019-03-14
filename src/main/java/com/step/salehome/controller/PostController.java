package com.step.salehome.controller;


import com.step.salehome.model.Post;
import com.step.salehome.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class PostController {


    @Autowired
    private PostService postService;



    @PostMapping("/addpost")
    public String addPost(@ModelAttribute("post") Post post, @RequestParam("idCity") int idCity, @RequestParam("post_photo") MultipartFile[] files){
        for (MultipartFile file :
                files) {
            System.out.println(file.getOriginalFilename());
        }

        return "/view/home";
    }




}
