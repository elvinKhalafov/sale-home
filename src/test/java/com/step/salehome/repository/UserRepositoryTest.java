package com.step.salehome.repository;

import com.step.salehome.model.Post;
import com.step.salehome.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Test
    public void loginUser() throws Exception {
        User user = userRepository.loginUser("v.nesirov91@gmail.com");
        System.out.println(user);
        System.out.println(user.getIdFavoritePost().contains(10));
    }

    @Test
    public void getFavoritePosts() throws Exception {
        List<String> i = Arrays.asList("salam", "sagol", "necesen");
        System.out.println(i.stream().noneMatch(integer -> integer.endsWith("l")));
    }


    @Test
    public void mathTest(){
        int value = 121;

        System.out.println( (int) Math.ceil( (double) value /20));
    }

}