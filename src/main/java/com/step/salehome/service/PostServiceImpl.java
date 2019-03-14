package com.step.salehome.service;

import com.step.salehome.model.AdvancedSearchPost;
import com.step.salehome.model.City;
import com.step.salehome.model.Post;
import com.step.salehome.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {


    private PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }


    @Override
    public List<Post> searchPost(AdvancedSearchPost advancedSearchPost) {
        return postRepository.searchPost(advancedSearchPost);
    }

    @Override
    public List<Post> getRecentlyPost() {
        return postRepository.getRecentlyPost();
    }

    @Override
    public void addPost(Post post) {
        postRepository.addPost(post);
    }

    @Override
    public Post getPostById(int id) {
        return postRepository.getPostById(id);
    }

    @Override
    public List<Post> getmyPosts(int id) {
        return postRepository.getmyPosts(id);
    }

    @Override
    public List<City> getAllCity() {
        return postRepository.getAllCity();
    }

    @Override
    public void deletePost(int id) {
        postRepository.deletePost(id);
    }

    @Override
    public void addToFavorite(int postId, int userId) {
        postRepository.addToFavorite(postId, userId);
    }

    @Override
    public List<Post> getMyFavoritePosts(int id) {
        return postRepository.getMyFavoritePosts(id);
    }

    @Override
    public List<Post> getRandomPost() {
        return postRepository.getRandomPost();
    }
}
