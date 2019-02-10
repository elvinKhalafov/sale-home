package com.step.salehome.repository;

import com.step.salehome.model.AdvancedSearchPost;
import com.step.salehome.model.Post;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostRepositoryImplTest {

    @Autowired
    private PostRepository postRepository;

    @Test
    public void searchPost() throws Exception {

        AdvancedSearchPost advancedSearchPost = new AdvancedSearchPost();
//        advancedSearchPost.setAddress("");
        List<Post> list = postRepository.searchPost(advancedSearchPost);
        System.out.println(list);
    }

}