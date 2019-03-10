package com.step.salehome.repository;

import com.step.salehome.constants.PostConstants;
import com.step.salehome.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PostRepositoryImpl implements PostRepository {

    private final String GET_POST_BY_ID = "select * from post p inner join user u on p.id_user=u.id_user where p.id_post=?";

    private final String GET_POST_BY_USER_ID ="select * from post p inner join user u on p.id_user=u.id_user where u.id_user=?";

    private final String ADVANCED_SEARCH_POST_SQL = "select * from post p inner join city c on p.id_city = c.id_city inner join user u on p.id_user = u.id_user inner join (select * from post_image where id_image_path in  (select min(id_image_path) from post_image pti group by pti.id_post)) pi on p.id_post = pi.id_post  where p.status = ? ";
    private final String GET_ALL_POST_SQL = "select * from post p inner join city c on p.id_city = c.id_city inner join user u on p.id_user = u.id_user inner join (select * from post_image where id_image_path in  (select min(id_image_path) from post_image pti group by pti.id_post)) pi on p.id_post = pi.id_post where p.status = ? order by p.adding_time desc limit 7";
    private final String ADD_POST = "insert into post (id_user, id_city, address, title, `desc`, post_type, room_count, home_type, area, price, status, email_allowed) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final String ADD_IMAGE = "insert into post_image (id_post, image_path) values (?, ?)";
    private final String SELECT_POST_BY_USER_ID = "select * from post p inner join city c on p.id_city = c.id_city inner join user u on p.id_user = u.id_user inner join (select * from post_image where id_image_path in  (select min(id_image_path) from post_image pti group by pti.id_post)) pi on p.id_post = pi.id_post  where p.id_user=?";


    private final String SELECT_POST_BY_ID = "select * from post p left join post_image pi on p.id_post=pi.id_post inner join user u on p.id_user=u.id_user inner join city c on c.id_city=p.id_city where p.id_post = ?";
    private final String GET_ALL_CITY = "select * from city";
    private final String DELETE_POST_BY_ID = "delete from post where id_post = ?";



    @Autowired
    JdbcTemplate jdbcTemplate;





    @Override
    public List<Post> searchPost(AdvancedSearchPost advancedSearchPost) {
        List<Object> objects = new ArrayList<>();
        StringBuilder sql = new StringBuilder(ADVANCED_SEARCH_POST_SQL);
       boolean condition= false;
       objects.add(PostConstants.POST_STATUS_ACTIVE);
        if (!advancedSearchPost.isAllFieldsNull()){



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
                sql.append(" maxPrice<=?");
                objects.add(advancedSearchPost.getMaxPrice());
                condition = true;
            }
            if (advancedSearchPost.getMiniPrice() != null) {
                if(condition){
                    sql.append(" and");
                }
                sql.append(" miniPrices>=?");
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
                sql.append(" maxArea<=?");
                objects.add(advancedSearchPost.getMaxArea());
                condition = true;
            }
            if (advancedSearchPost.getMiniPrice() != null) {
                if(condition){
                    sql.append(" and");
                }
                sql.append(" miniArea>=?");
                objects.add(advancedSearchPost.getMiniArea());

            }






        }
        sql.append("order by p.adding_time");


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
                post.setShareDate(rs.getTimestamp("adding_time").toLocalDateTime());
                post.setStatus(rs.getString("status"));
                post.setEmailAllowed(Boolean.parseBoolean(rs.getString("email_allowed")));

                User user = new User();
                user.setIdUser(rs.getInt("id_user"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
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


    @Override
    public List<Post> getRecentlyPost() {
        List<Post>postList = jdbcTemplate.query(GET_ALL_POST_SQL, new Object[]{PostConstants.POST_STATUS_ACTIVE},new RowMapper<Post>(){

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
                   post.setShareDate(rs.getTimestamp("adding_time").toLocalDateTime());
                   post.setStatus(rs.getString("status"));
                   post.setEmailAllowed(rs.getBoolean("email_allowed"));

                   User user = new User();
                   user.setIdUser(rs.getInt("id_user"));
                   user.setFirstName(rs.getString("first_name"));
                   user.setLastName(rs.getString("last_name"));
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

    @Override
    public List<Post> getmyPosts(int id) {
        List<Post> myposts = jdbcTemplate.query(SELECT_POST_BY_USER_ID, new Object[]{id}, new RowMapper<Post>() {
            @Nullable
            @Override
            public Post mapRow(ResultSet resultSet, int i) throws SQLException {
                Post post = new Post();


                        post.setIdPost(resultSet.getInt("id_post"));
                        post.setAddress(resultSet.getString("address"));
                        post.setArea(resultSet.getDouble("area"));
                        City city = new City();
                        city.setCityName(resultSet.getString("city_name"));
                        city.setIdCity(resultSet.getInt("id_city"));
                        post.setCity(city);
                        post.setDesc(resultSet.getString("desc"));
                        post.setEmailAllowed(resultSet.getBoolean("email_allowed"));
                        post.setHomeType(resultSet.getString("home_type"));
                        post.setPrice(resultSet.getDouble("price"));
                        post.setRoomCount(resultSet.getInt("room_count"));
                        post.setPostType(resultSet.getString("home_type"));
                        post.setStatus(resultSet.getString("status"));
                        post.setShareDate(resultSet.getTimestamp("adding_time").toLocalDateTime());
                        User user = new User();
                        user.setIdUser(resultSet.getInt("id_user"));
                        user.setEmail(resultSet.getString("email"));
                        user.setFirstName(resultSet.getString("first_name"));
                        user.setLastName(resultSet.getString("last_name"));
                        post.setUser(user);

                    PostImage postImage = new PostImage();
                    postImage.setImagePath(resultSet.getString("image_path"));
                    post.addImage(postImage);

                return post;

            }
        });

        return myposts;
    }

    @Override
    public List<City> getAllCity() {
        return jdbcTemplate.query(GET_ALL_CITY, new BeanPropertyRowMapper<>(City.class));
    }

    @Override
    public void deletePost(int id) {
        jdbcTemplate.update(DELETE_POST_BY_ID, id);
    }

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
        jdbcTemplate.batchUpdate(ADD_IMAGE, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                String imagePath = post.getPostImages().get(i).getImagePath();
                preparedStatement.setInt(1, primarKey);
                preparedStatement.setString(2, imagePath);
            }

            @Override
            public int getBatchSize() {
                return post.getPostImages().size();
            }
        });
    }

    @Override
    public Post getPostById(int id) {
        return jdbcTemplate.query(SELECT_POST_BY_USER_ID, new Object[]{id}, new ResultSetExtractor<Post>() {
            @Override
            public Post extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                Post post = new Post();
                while (resultSet.next()) {
                    if (post.getIdPost() == 0) {
                        post.setIdPost(resultSet.getInt("id_post"));
                        post.setAddress(resultSet.getString("address"));
                        post.setArea(resultSet.getDouble("area"));
                        City city = new City();
                        city.setCityName(resultSet.getString("name"));
                        city.setIdCity(resultSet.getInt("id_city"));
                        post.setCity(city);
                        post.setDesc(resultSet.getString("desc"));
                        post.setEmailAllowed(resultSet.getBoolean("email_allowed"));
                        post.setHomeType(resultSet.getString("home_type"));
                        post.setPrice(resultSet.getDouble("price"));
                        post.setRoomCount(resultSet.getInt("room_count"));
                        post.setPostType(resultSet.getString("home_type"));
                        post.setStatus(resultSet.getString("status"));
                        post.setShareDate(resultSet.getTimestamp("adding_time").toLocalDateTime());
                        User user = new User();
                        user.setIdUser(resultSet.getInt("id_user"));
                        user.setEmail(resultSet.getString("email"));
                        user.setFirstName(resultSet.getString("first_name"));
                        user.setLastName(resultSet.getString("last_name"));
                        post.setUser(user);
                    }
                    PostImage postImage = new PostImage();
                    postImage.setImagePath(resultSet.getString("image_path"));
                    post.addImage(postImage);
                }
                return post;
            }
        });
    }

}
