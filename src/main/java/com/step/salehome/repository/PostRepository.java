package com.step.salehome.repository;

import com.step.salehome.model.AdvancedSearchPost;
import com.step.salehome.model.Post;
import com.step.salehome.model.User;

import java.util.List;

public interface PostRepository {

    List<Post> searchPost(AdvancedSearchPost advancedSearchPost);

}
