package com.step.salehome.service;


import com.step.salehome.model.User;
import com.step.salehome.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public void addUser(User user) {
        userRepository.addUser(user);


    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.getUserByEmail(username);

        if(user==null){
            throw new UsernameNotFoundException(username);
        }
        return user;
    }
}
