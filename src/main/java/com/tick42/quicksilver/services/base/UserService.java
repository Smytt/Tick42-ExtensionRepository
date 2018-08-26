package com.tick42.quicksilver.services.base;

import com.tick42.quicksilver.models.User;


import javax.servlet.http.HttpServletResponse;

public interface UserService {
    void create(User user);

    User findById(int id);

    String createTokenData(User user, HttpServletResponse response);

    boolean registered(User user);
}
