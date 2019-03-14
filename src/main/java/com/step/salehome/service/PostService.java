package com.step.salehome.service;

import com.step.salehome.model.AdvancedSearchPost;
import com.step.salehome.model.City;
import com.step.salehome.model.Post;

import java.util.List;

public interface PostService {
    List<Post> searchPost(AdvancedSearchPost advancedSearchPost);
    List<Post> getRecentlyPost();
    void addPost(Post post);
    Post getPostById(int id);
    List<Post> getmyPosts(int id);

    List<Post> getFavoritePosts(int id);

    List<City> getAllCity();
    void deletePost(int id);
}
