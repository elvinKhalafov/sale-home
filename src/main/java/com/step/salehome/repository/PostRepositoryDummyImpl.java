package com.step.salehome.repository;

import com.step.salehome.model.Post;

import java.util.ArrayList;
import java.util.List;

public class PostRepositoryDummyImpl implements PostRepository {

    @Override
    public List<Post> getRecentlyAddedPosts() {
        List<Post>listPost = new ArrayList<>();
        return listPost;
    }
}
