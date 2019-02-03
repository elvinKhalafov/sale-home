package com.step.salehome.repository;

import com.step.salehome.model.Role;
import com.step.salehome.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final String INSERT_NEW_USER_SQL = "insert into user(email, password, first_name, last_name, token, id_role, status) " +
            "values (?, ?, ?, ?, ?, ?, ?)";

    private final String GET_USER_BY_EMAIL = "select * from user u inner join role r on u.id_role = r.id_role where u.email = ?";




    @Autowired
    private JdbcTemplate jdbcTemplate;


  @Override
    public void addUser(User user) {
        jdbcTemplate.update(INSERT_NEW_USER_SQL, user.getEmail(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getToken(),user.getRole(), user.getStatus());


    }

    @Override
    public User getUserByEmail(String email) {

    User user = jdbcTemplate.queryForObject(GET_USER_BY_EMAIL, new Object[]{email}, new RowMapper<User>() {
        @Nullable
        @Override
        public User mapRow(ResultSet rs, int i) throws SQLException {
            User user = new User();
            user.setIdUser(rs.getInt("id_user"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setFirstName(rs.getString(rs.getString("first_name")));
            user.setLastName(rs.getString("last_name"));
            user.setToken(rs.getString("token"));
            user.setStatus(rs.getInt("status"));
            Role role = new Role();
            role.setIdRole(rs.getInt("role_id"));
            role.setRoleType(rs.getString("role_type"));
            user.setRole(role);
            return user;
        }
    });

    return user;

    }
}
