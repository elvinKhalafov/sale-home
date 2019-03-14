package com.step.salehome.repository;

import com.step.salehome.model.AdvancedSearchPost;
import com.step.salehome.model.City;
import com.step.salehome.model.Post;
import com.step.salehome.model.User;

import java.util.List;

public interface PostRepository {

    List<Post> searchPost(AdvancedSearchPost advancedSearchPost);
    void addPost(Post post);
    Post getPostById(int id);
    List<Post> getRecentlyPost();
    List<Post> getmyPosts(int id);

    List<Post> getFavoritePosts(int id);


    List<City> getAllCity();
    void deletePost(int id);
}
