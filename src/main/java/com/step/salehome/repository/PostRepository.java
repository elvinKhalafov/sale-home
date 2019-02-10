package com.step.salehome.repository;

import com.step.salehome.model.Post;
import com.step.salehome.model.User;

import java.util.List;

public interface PostRepository {


    void addPost(Post post);
    Post getPostById(int id);


}
