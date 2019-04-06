package com.step.salehome.controller;

import com.step.salehome.constants.MessageConstants;
import com.step.salehome.model.City;
import com.step.salehome.model.Post;
import com.step.salehome.model.PostImage;
import com.step.salehome.model.User;
import com.step.salehome.service.PostService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import javax.websocket.server.PathParam;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class AuthenticatedActionsController {


    @Autowired
    private PostService postService;

    @Autowired
    private List<City> cities;

    @Value("#{getImagePath}")
    private String imageUploadPath;

    @RequestMapping("/post")
    public String addPost(@Valid @ModelAttribute("post") Post post) {
        return "redirect/";
    }

    @GetMapping("/myPosts")
    public String myPosts(@RequestParam(name = "page", required = false) Integer page, Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int postCount = postService.getMyPostCount(user.getIdUser());
        int totalPage = (int) Math.ceil( (double) postCount / 12);

        int offset = 0;
        if (page != null && page >= totalPage) {
            offset = postCount - 12;
        } else if (page != null && page > 1){
            offset = (page - 1) * 12;
        }
        model.addAttribute("totalPage", totalPage);

        List<Post> myPosts = postService.getMyPosts(user.getIdUser(), offset);
        model.addAttribute("myPosts", myPosts);

        List<Post> favoritePosts = postService.getFavoritePosts(user.getIdUser());
        model.addAttribute("favoritePost", favoritePosts);
        return "view/myPosts";


    }

    @RequestMapping("/delete")
    public String deletePost(@RequestParam("id") int id, Model model) {
        postService.deletePost(id);
        model.addAttribute("message", MessageConstants.SUCCESS_DELETE);
        return "view/myPosts";
    }

    @PostMapping("/add-post")
    public String processSubmit(@Valid @ModelAttribute("post") Post post,
                                BindingResult bindingResult, Model model,
                                @RequestParam("idCity") int idCity,
                                @RequestParam("post_photo") MultipartFile[] files,
                                HttpServletRequest httpServletRequest
    ) throws IOException {
        System.out.println(post);
        httpServletRequest.setCharacterEncoding("UTF-8");
        if (bindingResult.hasErrors()) {
            model.addAttribute("invalidPost", post);
            model.addAttribute("postValidationExceptionList", bindingResult.getAllErrors());
            model.addAttribute("cities", cities);

            return "view/post";
        } else if (files.length < 1 || files.length > 6) {
            model.addAttribute("invalidePost", post);
            model.addAttribute("uploadTypeError", files.length < 1 ? MessageConstants.ERROE_FILES_LENGTH_LESS_THAN_ONE : MessageConstants.ERROR_FILES_MORE_THAN_SIX);
            model.addAttribute("cities", cities);

            System.out.println("length");
            return "view/post";
        } else if (!Arrays.stream(files).allMatch(multipartFile -> multipartFile.getOriginalFilename().endsWith(".jpg") || multipartFile.getOriginalFilename().endsWith(".png") || multipartFile.getOriginalFilename().endsWith(".jpeg"))) {
            model.addAttribute("invalidePost", post);
            model.addAttribute("pictureSizeError", MessageConstants.INVALID_FILE_TYPE);
            model.addAttribute("cities", cities);

            System.out.println("extention");

            return "view/post";
        } else {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            post.setUser(user);
            City city = new City();
            city.setIdCity(idCity);
            post.setCity(city);
            post.setStatus("active");
            Path pathToSaveFile = Paths.get(imageUploadPath, user.getEmail());

            if (!Files.exists(pathToSaveFile)) {
                Files.createDirectories(pathToSaveFile);
            }


            for (MultipartFile multipartFile : files) {

                String fileName = UUID.randomUUID() + multipartFile.getOriginalFilename();

                Path file = Paths.get(pathToSaveFile.toString(), fileName);

                Files.copy(multipartFile.getInputStream(), file, StandardCopyOption.REPLACE_EXISTING);

                Path pathToSaveDb = Paths.get(user.getEmail(), fileName);

                PostImage postImage = new PostImage();


                postImage.setImagePath(DatatypeConverter.printBase64Binary(pathToSaveDb.toString().getBytes()));

                post.addImage(postImage);
            }

            postService.addPost(post);

            System.out.println(post);

            model.addAttribute("message", MessageConstants.SUCCESS_ADD_POST);
            return "redirect:/";
        }
    }

    @GetMapping("/add-post")
    public String addPost(Model model) {
        model.addAttribute("post", new Post());
        model.addAttribute("cities", cities);
        return "view/post";
    }

    @GetMapping("/favorite-posts")
    public String getMyFavoritePosts(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Post> posts = postService.getFavoritePosts(user.getIdUser());
        model.addAttribute("posts", posts);
        return "view/favoritePosts";

    }

    @RequestMapping("/addToFavorite/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void addToFavorite(@PathVariable("id") int id, HttpServletRequest httpServletRequest) {
//        if ("XMLHttpRequest".equals(httpServletRequest.getHeader("X-Requested-With"))) {
//            return;
//        }
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!user.getIdFavoritePost().contains(id)) {
            user.addIdFavoritePost(id);
            postService.addToFavorite(id, user.getIdUser());
        }
    }

    @RequestMapping("/deleteFromFavoritePost/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFromFavorite(@PathVariable("id") int id, HttpServletRequest httpServletRequest) {
//        if ("XMLHttpRequest".equals(httpServletRequest.getHeader("X-Requested-With"))) {
//            return;
//        }
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user.getIdFavoritePost().removeIf(idPost -> idPost.equals(id))) {
            postService.deleteFromFavoritePost(user.getIdUser(), id);
        }

    }

}
