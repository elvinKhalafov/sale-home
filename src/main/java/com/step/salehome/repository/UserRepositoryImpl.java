package com.step.salehome.repository;

import com.step.salehome.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final String INSERT_NEW_USER_SQL = "insert into user(email, password, first_name, last_name, token, id_role, status) " +
            "values (?, ?, ?, ?, ?, ?, ?)";

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @java.lang.Override
    public void addUser(User user) {


    }
}
