package com.tick42.quicksilver.repositories.base;

import com.tick42.quicksilver.models.Tag;
import com.tick42.quicksilver.models.User;

import java.util.List;

public interface UserRepository {
    User create(User user);

    User update(User user);

    User findById(int id);

    User findByUsername(String username);

    List<User> findAll();

    List<User> findUsersByState(boolean state);
}
