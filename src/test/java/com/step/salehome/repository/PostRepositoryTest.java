package com.step.salehome.repository;

import com.step.salehome.model.City;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Test
    public void getAllCity() {
        List<City> cities = postRepository.getAllCity();
        System.out.println(cities);
    }

    public void deletePost() {
        postRepository.deletePost(2);
    }
}