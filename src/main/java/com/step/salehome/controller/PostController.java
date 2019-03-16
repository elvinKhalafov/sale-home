package com.step.salehome.controller;


import com.step.salehome.constants.MessageConstants;
import com.step.salehome.model.Post;
import com.step.salehome.service.PostService;
import com.step.salehome.util.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.websocket.server.PathParam;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Controller
public class PostController {


    @Autowired
    private PostService postService;

    @Autowired
    private EmailUtil emailUtil;

    @RequestMapping("/send-email/{id}")
    public String sendEmail(@PathVariable("id") int id,
                            @RequestParam("email") String email,
                            @RequestParam("phone") String phone,
                            @RequestParam("message") String message,
                            RedirectAttributes redirectAttributes) {
        Post post = postService.getPostById(id);
        if (post.isEmailAllowed() && (email != null && !email.trim().isEmpty()) && (phone != null && !phone.trim().isEmpty()) && message != null) {

            String subject = "Someone is interested in about your post";
            String emailMessage = message+"\n \n \n" +phone+"\n\n\n"+email;
            Executors
                    .newSingleThreadExecutor()
                    .submit(() -> emailUtil.sendMessageToPostOwner(post.getUser().getEmail(), subject, message));
            redirectAttributes.addFlashAttribute("message", MessageConstants.SUCCES_SENDING_EMAIL);
            return "redirect:/post/"+id;
        }else {
            redirectAttributes.addFlashAttribute("message", MessageConstants.ERROR_SENDING_EMAIL);
            return "redirect:/post/"+id;
        }
    }
}
