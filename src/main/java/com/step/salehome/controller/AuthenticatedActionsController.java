package com.step.salehome.controller;

import com.step.salehome.constants.MessageConstants;
import com.step.salehome.model.City;
import com.step.salehome.model.Post;
import com.step.salehome.model.PostImage;
import com.step.salehome.model.User;
import com.step.salehome.service.PostService;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

@Controller
@RequestMapping("/user")
public class AuthenticatedActionsController {

    @Autowired
    private PostService postService;

    @Value("#{getImagePath}")
    private String imageUploadPath;

    @RequestMapping("/post")
    public String addPost(@Valid @ModelAttribute("post") Post post) {
        return "redirect/";
    }

    @RequestMapping("/myPosts")
    public String myPosts(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Post> myPosts = postService.getmyPosts(user.getIdUser());
        System.out.println("Size"+myPosts.size());
        model.addAttribute("myPosts", myPosts);

        List<Post> favoritePosts = postService.getMyFavoritePosts(user.getIdUser());
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
    public String processSubmit(@Valid @ModelAttribute("post") Post post, BindingResult bindingResult, Model model, @RequestParam("idCity") int idCity, @RequestParam("post_photo") MultipartFile[] files) throws IOException {

        Arrays.asList(files).forEach(file -> System.out.println(file.getContentType()));

        if (bindingResult.hasErrors()) {
            model.addAttribute("invalidPost", post);
            model.addAttribute("postValidationExceptionList", bindingResult.getAllErrors());
            model.addAttribute("cities", postService.getAllCity());

            return "view/post";
        } else if (files.length < 1 || files.length > 6) {
            model.addAttribute("invalidePost", post);
            model.addAttribute("uploadTypeError", files.length < 1 ? MessageConstants.ERROE_FILES_LENGTH_LESS_THAN_ONE : MessageConstants.ERROR_FILES_MORE_THAN_SIX);
            model.addAttribute("cities", postService.getAllCity());

            return "view/post";
        } else if (!Arrays.stream(files).noneMatch(multipartFile -> multipartFile.getContentType().endsWith(".jpg") || multipartFile.getOriginalFilename().endsWith(".png") || multipartFile.getOriginalFilename().endsWith(".jpeg"))) {
            model.addAttribute("invalidePost", post);
            model.addAttribute("pictureSizeError", MessageConstants.INVALID_FILE_TYPE);
            model.addAttribute("cities", postService.getAllCity());

            return "view/post";
        } else {

            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            post.setUser(user);
            City city = new City();
            city.setIdCity(idCity);
            post.setCity(city);
            Path pathToSaveFile = Paths.get(imageUploadPath, user.getEmail());

            if (!Files.exists(pathToSaveFile)) {
                Files.createDirectories(pathToSaveFile);
            }


            for (MultipartFile multipartFile : files) {

                Path file = Paths.get(pathToSaveFile.toString(), multipartFile.getOriginalFilename());

                Files.copy(multipartFile.getInputStream(), file, StandardCopyOption.REPLACE_EXISTING);

                Path pathToSaveDb = Paths.get(user.getEmail(), multipartFile.getOriginalFilename());

                PostImage postImage = new PostImage();


                postImage.setImagePath(DatatypeConverter.printBase64Binary(pathToSaveDb.toString().getBytes()));

                post.addImage(postImage);
            }

            postService.addPost(post);

            model.addAttribute("message", MessageConstants.SUCCESS_ADD_POST);
            return "redirect:/";
        }
    }

    @GetMapping("/add-post")
    public String addPost(Model model) {
        model.addAttribute("post", new Post());
        model.addAttribute("cities", postService.getAllCity());
        return "view/post";
    }

    @RequestMapping("/addToFavorite/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void addToFavorite(@PathParam("id") int id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        postService.addToFavorite(id, user.getIdUser());

    }


}
