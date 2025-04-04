package com.example.splitwise.services;

import com.example.splitwise.models.User;
import com.example.splitwise.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService{

    @Autowired
    private UserRepository userRepository;




    public User registerUser(String name, String email, String password) throws Exception {

        Optional<User> userOp = userRepository.findByEmail(email);


        if(!userOp.isEmpty()){
          throw new Exception("User already exists");
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(password);

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(encodedPassword);
        userRepository.save(user);
        return user;
    }

    public User loginUser(String email, String password) throws Exception {
        Optional<User> userOp = userRepository.findByEmail(email.trim());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (userOp.isEmpty()) {
            throw new Exception("User not found");
        }

        User user = userOp.get();
        if (!encoder.matches(password, user.getPassword())) {
            throw new Exception("Wrong password");
        }

        return user;
    }
}
