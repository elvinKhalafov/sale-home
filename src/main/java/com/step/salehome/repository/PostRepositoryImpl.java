package com.step.salehome.repository;

import com.step.salehome.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostRepositoryImpl implements PostRepository {

    private String INSERT_POST = "insert into post (id_user, "

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public boolean insertPost(Post post) {
        boolean result = jdbcTemplate.update();
    }
}
