package com.tick42.quicksilver.services.base;

import com.tick42.quicksilver.models.User;

public interface UserService {
    void create(User user);

    User findById(int id);

}
