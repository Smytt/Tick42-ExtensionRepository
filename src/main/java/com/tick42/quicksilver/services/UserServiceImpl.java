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
    public void update(User user){
        userRepository.update(user);
    }
    @Override
    public void changeState(User user,String state){
        if (state.equals("enable")){
            user.setActive(true);
        }else if (state.equals("disable")){
            user.setActive(false);
        }
        userRepository.update(user);
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
