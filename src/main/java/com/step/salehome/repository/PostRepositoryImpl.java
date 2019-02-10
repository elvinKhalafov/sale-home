package com.step.salehome.repository;

import com.step.salehome.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PostRepositoryImpl implements PostRepository {

    private final String ADD_POST = "insert into post (id_user, id_city, address, title, desc, post_type, room, home_type, area, price, status, email_allowed) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private final String GET_POST_BY_ID = "select * from post p inner join user u on p.id_user=u.id_user where p.id_post=?";

    private final String ADVANCED_SEARCH_POST_SQL = "select * from post p inner join city c on p.id_city = c.id_city inner join user u on p.id_user = u.id_user inner join (select * from post_image where id_image_path in  (select min(id_image_path) from post_image pti group by pti.id_post)) pi on p.id_post = pi.id_post ";

    @Autowired
    JdbcTemplate jdbcTemplate;




    @Override
    public List<Post> searchPost(AdvancedSearchPost advancedSearchPost) {
        List<Object> objects = new ArrayList<>();
        StringBuilder sql = new StringBuilder(ADVANCED_SEARCH_POST_SQL);
       boolean condition= false;
        if (!advancedSearchPost.isAllFieldsNull()){
            sql.append(" where");


            if (advancedSearchPost.getIdCity()!=null){
                if(condition){
                    sql.append(" and");
                }
                sql.append(" idCity = ?");
                objects.add(advancedSearchPost.getIdCity());
                condition = true;
            }
            if (advancedSearchPost.getAddress()!=null){
                if(condition){
                    sql.append((" and"));
                }
                sql.append(" address = ?");
                objects.add(advancedSearchPost.getAddress());
                condition = true;
            }
            if (advancedSearchPost.getKeywords() != null) {
                if(condition){
                    sql.append(" and");
                }
                sql.append(" keywords=?");
                objects.add(advancedSearchPost.getKeywords());
                condition = true;
            }
            if (advancedSearchPost.getPostType() != null) {
                if(condition){
                    sql.append(" and");
                }
                sql.append(" postType=?");
                objects.add(advancedSearchPost.getPostType());
                condition = true;
            }

            if (advancedSearchPost.getRoomCount() != null) {
                if(condition){
                    sql.append(" and");
                }
                sql.append(" roomType=?");
                objects.add(advancedSearchPost.getRoomCount());
                condition = true;
            }
            if (advancedSearchPost.getMaxPrice() != null) {
                if(condition){
                    sql.append(" and");
                }
                sql.append(" maxPrice=?");
                objects.add(advancedSearchPost.getMaxPrice());
                condition = true;
            }
            if (advancedSearchPost.getMiniPrice() != null) {
                if(condition){
                    sql.append(" and");
                }
                sql.append(" miniPrices=?");
                objects.add(advancedSearchPost.getMiniPrice());
                condition = true;
            }
            if (advancedSearchPost.getHomeType() != null) {
                if(condition){
                    sql.append(" and");
                }
                sql.append(" homeType=?");
                objects.add(advancedSearchPost.getHomeType());
                condition = true;
            }
            if (advancedSearchPost.getMaxArea() != null) {
                if(condition){
                    sql.append(" and");
                }
                sql.append(" maxArea=?");
                objects.add(advancedSearchPost.getMaxArea());
                condition = true;
            }
            if (advancedSearchPost.getMiniPrice() != null) {
                if(condition){
                    sql.append(" and");
                }
                sql.append(" miniArea=?");
                objects.add(advancedSearchPost.getMiniArea());
                condition = true;
            }




        }

        List<Post>postList = jdbcTemplate.query(ADVANCED_SEARCH_POST_SQL, objects.toArray(),new RowMapper<Post>(){

            @Nullable
            @Override
            public Post mapRow(ResultSet rs, int i) throws SQLException {
                Post post = new Post();
                post.setIdPost(rs.getInt("id_post"));
                post.setAddress(rs.getString("address"));
                post.setTitle(rs.getString("title"));
                post.setDesc(rs.getString("desc"));
                post.setPostType(rs.getString("post_type"));
                post.setRoomCount(rs.getInt("room_count"));
                post.setHomeType(rs.getString("home_type"));
                post.setArea(rs.getDouble("area"));
                post.setPrice(rs.getDouble("price"));
                post.setShareDate(LocalDateTime.parse(rs.getString("adding_time")));
                post.setStatus(rs.getString("status"));
                post.setEmailAllowed(Boolean.parseBoolean(rs.getString("email_allowed")));
                User user = new User();
                user.setIdUser(rs.getInt("id_user"));
                user.setFirstName(rs.getString("first_name"));
                post.setUser(user);
                PostImage postImage = new PostImage();
                postImage.setIdPostImage(rs.getInt("id_image_path"));
                postImage.setImagePath(rs.getString("image_path"));

                post.addImage(postImage);
                City city = new City();
                city.setIdCity(rs.getInt("id_city"));
                city.setCityName(rs.getString("city_name"));
                post.setCity(city);

                return post;
            }
        });

        return postList;
    }

}
