package com.step.salehome.repository;

import com.step.salehome.model.City;
import com.step.salehome.model.Post;
import com.step.salehome.model.User;
import javafx.geometry.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PostRepositoryImpl implements PostRepository {

    private final String ADD_POST = "insert into post (id_user, id_city, address, title, `desc`, post_type, room_count, home_type, area, price, status, email_allowed) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final String ADD_IMAGE = "insert into post_image (id_post, image_path) values (?, ?)";
    private final String SELECT_POST_BY_ID = "select * from post p left join post_image pi on p.id_post=pi.id_post inner join user u on p.id_user=u.id_user inner join city c on c.id_city=p.id_city where p.id_post = ?";

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
                statement.setInt(11, post.getStatus());
                statement.setBoolean(12, post.isEmailAllowed());
                return statement;
            }
        }, holder);
        int primarKey = holder.getKey().intValue();
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

    @Override
    public Post getPostById(int id) {
        return jdbcTemplate.query(SELECT_POST_BY_ID, new Object[]{id}, new ResultSetExtractor<Post>() {
            @Override
            public Post extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                Post post = new Post();
                List<String> imagePaths = new ArrayList<>();
                while (resultSet.next()) {
                    if (post.getIdPost() == 0) {
                        post.setIdPost(resultSet.getInt("id_post"));
                        post.setAddress(resultSet.getString("address"));
                        post.setArea(resultSet.getDouble("area"));
                        City city = new City();
                        city.setCity(resultSet.getString("name"));
                        city.setIdCity(resultSet.getInt("id_city"));
                        post.setCity(city);
                        post.setDesc(resultSet.getString("desc"));
                        post.setEmailAllowed(resultSet.getBoolean("email_allowed"));
                        post.setHomeType(resultSet.getString("home_type"));
                        post.setPrice(resultSet.getDouble("price"));
                        post.setRoomCount(resultSet.getInt("room_count"));
                        post.setPostType(resultSet.getString("home_type"));
                        post.setStatus(resultSet.getInt("status"));
                        post.setAddingTime(resultSet.getTimestamp("adding_time").toLocalDateTime());
                        User user = new User();
                        user.setIdUser(resultSet.getInt("id_user"));
                        user.setEmail(resultSet.getString("email"));
                        user.setFirstName(resultSet.getString("first_name"));
                        user.setLastName(resultSet.getString("last_name"));
                        post.setUser(user);
                    }
                    imagePaths.add(resultSet.getString("image_path"));
                    post.setImagePath(imagePaths);
                }
                return post;
            }
        });
    }

}
