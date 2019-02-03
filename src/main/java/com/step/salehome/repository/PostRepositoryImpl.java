package com.step.salehome.repository;

import com.step.salehome.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
public class PostRepositoryImpl implements PostRepository {

    private final String ADD_POST = "insert into post (id_user, id_city, address, title, desc, post_type, room, home_type, area, price, status, email_allowed) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";


    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void addPost(Post post) {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement statement = connection.prepareStatement(ADD_POST, Statement.RETURN_GENERATED_KEYS);
                 statement.setInt(1, post.getUser().getIdUser());
                 statement.setInt(2, post.getCity().getId());
                statement.setString(3, post.getAddress());
                statement.setString(4, post.getTitle());
                statement.setString(5, post.getDesc());
                statement.setString(6, post.getPostType());
                statement.setInt(7, post.getRoomCount());
                statement.setString(8, post.getHomeType());
                statement.setDouble(9, post.getArea());
                statement.setDouble(10, post.getPrice());
                statement.setString(11, post.getStatus());
                statement.setBoolean(12, post.isEmailAllowed());
                return statement;
            }
        }, holder);
        int primarKey = holder.getKey().intValue();

    }
}
