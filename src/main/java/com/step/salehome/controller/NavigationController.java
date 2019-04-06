package com.step.salehome.controller;

import com.step.salehome.model.AdvancedSearchPost;
import com.step.salehome.model.City;
import com.step.salehome.model.Post;
import com.step.salehome.model.User;
import com.step.salehome.service.PostService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.websocket.server.PathParam;
import java.util.List;

@Controller
public class NavigationController {

    private static final Logger logger = LogManager.getLogger(NavigationController.class);

    @Autowired
    private PostService postService;

    @Autowired
    private List<City> cityList;

    @RequestMapping("/post/{idPost}")
    public String openPostPage(Model model, @PathVariable("idPost") int idPost) {
        Post post = postService.getPostById(idPost);

        model.addAttribute("post", post);


        return "view/pre";
    }

    @GetMapping("/register")
    public String openRegisterPage(Model model) {
        model.addAttribute("user", new User());
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
                                 @RequestParam(required = false, name ="maxArea") String maxArea,
                                 @RequestParam(name = "page", required = false) Integer page){
        logger.info("REQUEST: openSearchPage");
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

        int postCount = postService.getSearchedPostCount(advancedSearchPost);
        int totalPage = (int) Math.ceil( (double) postCount / 15);

        int offset = 0;
        if (page != null && page >= totalPage) {
            offset = postCount - 15;
        } else if (page != null && page > 1){
            offset = (page - 1) * 15;
        }
        model.addAttribute("totalPage", totalPage);

        List<Post>searchPostList = postService.searchPost(advancedSearchPost, offset);
        System.out.println(searchPostList);
        model.addAttribute("searchPostList", searchPostList);
        model.addAttribute("cities", cityList);
        model.addAttribute("advancedSearchPost", advancedSearchPost);
        logger.info("RESPONSE: completed" );
        return "view/search";
    }

    @RequestMapping("/login")
    public String openLoginPage() {

        return "/view/login";
    }


    @RequestMapping("/something")
    public String doSomething(HttpServletRequest httpServletRequest) {
        System.out.println(httpServletRequest.getRemoteAddr());
        return "view/home";
    }
}
