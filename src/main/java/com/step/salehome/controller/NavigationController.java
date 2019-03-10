package com.step.salehome.controller;

import com.step.salehome.model.AdvancedSearchPost;
import com.step.salehome.model.Post;
import com.step.salehome.model.User;
import com.step.salehome.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class NavigationController {

    @Autowired
    private PostService postService;

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
    public String openSearchPage(Model model, @RequestParam(name = "keywords", required = false) String keywords,
                                 @RequestParam(name = "postType", required = false) String postType,
                                 @RequestParam(required = false, name ="miniPrice") String miniPrice,
                                 @RequestParam(required = false, name ="maxPrice") String maxprice,
                                 @RequestParam(required = false, name ="roomCount") String roomCount,
                                 @RequestParam(required = false, name ="cityName") String cityname,
                                 @RequestParam(required = false, name ="homeType") String homeType,
                                 @RequestParam(required = false, name ="miniArea") String miniArea,
                                 @RequestParam(required = false, name ="maxArea") String maxArea){
        AdvancedSearchPost advancedSearchPost = new AdvancedSearchPost();
        advancedSearchPost.setIdCity(cityname);
        advancedSearchPost.setHomeType(homeType);
        advancedSearchPost.setKeywords(keywords);
        advancedSearchPost.setMaxArea(maxArea);
        advancedSearchPost.setMiniArea(miniArea);
        advancedSearchPost.setMaxPrice(maxprice);
        advancedSearchPost.setMiniPrice(miniPrice);
        advancedSearchPost.setRoomCount(roomCount);
        advancedSearchPost.setPostType(postType);

        List<Post>searchPostList = postService.searchPost(advancedSearchPost);
        model.addAttribute("searchPostlist", searchPostList);
        return "/view/search";
    }

    @RequestMapping("/add-post")
    public String openAddPostPage(Model model) {
        model.addAttribute("post", new Post());
        model.addAttribute("cities", postService.getAllCity());
        return "view/post";
    }

    @RequestMapping("/login")
    public String openLoginPage() {

        return "/view/login";
    }


}
