package com.step.salehome.repository;

import com.step.salehome.model.City;
import com.step.salehome.model.Post;
import com.step.salehome.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostRepositoryImplTest {

    @Autowired
    private PostRepository postRepository;

    @Test
    public void addPost() {
        Post post = new Post();
        post.setTitle("adasdsadsadasdasd");
        post.setDesc(" hovsanda 1200 sot torpaq");
        post.setAddress("hovsan");
        City city = new City();
        city.setIdCity(1);
        post.setCity(city);
        post.setArea(45.2);
        post.setEmailAllowed(true);
        post.setHomeType("Flat");
        post.setPrice(654.23);
        post.setPostType("rent");
        post.setRoomCount(85);
        post.setStatus("active");
        User user = new User();
        user.setIdUser(2);
        post.setUser(user);
        List<String> imagePath = new ArrayList<>();
        imagePath.add("asdasdasdsad");
        imagePath.add("adsadsadxcsasd");
        imagePath.add("dsfjdsifdsibfidsbfidsbfidsbfib");
        post.setImagePath(imagePath);
        postRepository.addPost(post);
    }

    @Test
    public void getPostById() {
        Post post = postRepository.getPostById(1);
        System.out.println(post);
    }
}