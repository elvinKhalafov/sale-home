package com.step.salehome.repository;

import com.step.salehome.model.Post;
import javafx.geometry.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.Map;

@Repository
public class PostRepositoryImpl implements PostRepository {

    private final String ADD_POST = "insert into post (id_user, id_city, address, title, `desc`, post_type, room_count, home_type, area, price, status, email_allowed) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final String ADD_IMAGE = "insert into post_image (id_post, image_path) values (?, ?)";
    private final String SELECT_POST_BY_ID = "select * from post p left join post_image pi using(id_post) where p.id_post = ?";

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
                statement.setInt(2, post.getCity().getIdCity());
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
        if (post.getImagePath() != null && post.getImagePath().size() > 0) {
            jdbcTemplate.batchUpdate(ADD_IMAGE, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                    String imagePath = post.getImagePath().get(i);
                    preparedStatement.setInt(1, primarKey);
                    preparedStatement.setString(2, imagePath);
                }

                @Override
                public int getBatchSize() {
                    return post.getImagePath().size();
                }
            });
        }
    }

    @Override
    public Post getPostById(int id) {
        return jdbcTemplate.query(SELECT_POST_BY_ID, new Object[]{id}, new ResultSetExtractor<Post>() {
            @Override
            public Post extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                Map<Integer>
            }
        });
    }

}
