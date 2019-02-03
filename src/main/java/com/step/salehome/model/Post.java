package com.step.salehome.model;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class Post {
    private int idPost;
    private User user;
    private String city;
    private String address;
    private String title;
    private String desc;
    private String postType;
    private int roomCount;
    private String homeType;
    private double area;
    private LocalDateTime shareDate;
    private String status;
    private boolean emailAllowed;

}
