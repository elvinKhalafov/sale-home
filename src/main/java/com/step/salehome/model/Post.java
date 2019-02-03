package com.step.salehome.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;


@Data
public class Post {
    private int idPost;
    private User user;

    @NotNull
    @NotBlank(message = "City must not be blank")
    private String city;

    @NotNull
    @NotBlank(message = "Adress must not be blank")
    private String address;

    @NotNull
    @NotBlank(message = "Title must not be blank")
    @Size(min = 30, max = 150, message = "Title length must be between 30 and 150")
    private String title;


    @NotNull
    @NotBlank(message = "Description must not be blank")
    @Size(min = 100, max = 150, message = "Title length must be between 100 and 250")
    private String desc;

    @NotNull
    @NotBlank(message = "Description must not be blank")
    @Pattern(regexp = "rent|sale")
    private String postType;

    @NotNull
    @NotBlank(message = "Description must not be blank")
    private int roomCount;

    @NotNull
    @NotBlank(message = "Home type must not be blank")
    @Pattern(regexp = "apartment|flat|studio")
    private String homeType;

    @NotNull
    @NotBlank(message = "Description must not be blank")
    private double area;


    private LocalDateTime shareDate;
    private String status;

    @NotNull
    @NotBlank(message = "Description must not be blank")
    private boolean emailAllowed;

}
