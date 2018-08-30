package com.tick42.quicksilver.services.base;

import com.tick42.quicksilver.models.User;
import org.apache.http.auth.InvalidCredentialsException;


import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface UserService {

    User create(User user);

    void update(User user);

    void changeActiveState(User user, String state);

    List<User> findAll();

    User findByUsername(String username);

    User findById(int id);

    User login(User user) throws InvalidCredentialsException;

    User register(User user);

    String generateToken(User user);
}
