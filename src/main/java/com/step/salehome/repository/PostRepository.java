package com.step.salehome.repository;

import com.step.salehome.model.Post;

import java.util.List;

public interface PostRepository {
    List<Post> getRecentlyAddedPosts();

}
