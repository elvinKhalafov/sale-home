package com.step.salehome.model;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class Post {
    private int idPost;
    private int idUser;
    private int idCity;
    private String address;
    private String title;
    private String desc;
    private String postType;
    private int roomCount;
    private String homeType;
    private int area;
    private int size;
    private int idImagePath;
    private LocalDateTime shareDate;
    private String status;
    private boolean emailAllowed;

}
