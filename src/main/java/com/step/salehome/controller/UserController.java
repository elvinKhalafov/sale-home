package com.step.salehome.controller;

import com.step.salehome.constants.MessageConstants;
import com.step.salehome.constants.UserConstants;
import com.step.salehome.model.Role;
import com.step.salehome.model.User;
import com.step.salehome.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.UUID;

@Controller
public class UserController {


    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private UserService userService;



    @PostMapping("/register")
    public String addUser(@Valid @ModelAttribute("user") User user, @RequestParam("password2")String password2, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("message", bindingResult.getFieldError().getDefaultMessage());
            return "redirect:/register";
        }
        if(!user.getPassword().equals(password2)){
            redirectAttributes.addFlashAttribute("message", MessageConstants.ERROR_PASSWORD_IS_NOT_MATCH);
            return "redirect:/register";
        }
        user.setRole(new Role(UserConstants.ROLE_ID_USER));
        user.setStatus(UserConstants.USER_STATUS_INACTIVE);
        user.setToken(UUID.randomUUID().toString());
        user.setPassword(encoder.encode(user.getPassword()));

        userService.addUser(user);



        return "redirect:/";
    }


}
