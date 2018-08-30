package com.tick42.quicksilver.services;

import com.tick42.quicksilver.exceptions.UsernameExistsException;
import com.tick42.quicksilver.models.User;
import com.tick42.quicksilver.repositories.base.UserRepository;
import com.tick42.quicksilver.security.JwtGenerator;
import com.tick42.quicksilver.services.base.UserService;
import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private JwtGenerator jwtGenerator;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, JwtGenerator jwtGenerator, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtGenerator = jwtGenerator;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User create(User user) {
        return userRepository.create(user);
    }

    @Override
    public void update(User user) {
        userRepository.update(user);
    }

    @Override
    public User setState(int id, String state) {
        User user = userRepository.findById(id);
        switch (state) {
            case "enable":
                user.setIsActive(true);
                break;
            case "block":
                user.setIsActive(false);
                break;
            default://TODO:exception
        }
       return userRepository.update(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public User login(User user) throws InvalidCredentialsException {
        String username = user.getUsername();
        String password = user.getPassword();
        User foundUser = userRepository.findByUsername(username);
        if (foundUser != null && password.equals(foundUser.getPassword())) {
            return foundUser;
        }
        throw new InvalidCredentialsException("Invalid credentials.");
    }

    @Override
    public User register(User user) {
        String username = user.getUsername();
        User registeredUser = userRepository.findByUsername(username);

        if (registeredUser == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.create(user);
        }
        throw new UsernameExistsException("Username is already taken.");
    }

    @Override
    public String generateToken(User user) {
        return jwtGenerator.generate(user);
    }
}
