package com.tick42.quicksilver.repositories.base;

import com.tick42.quicksilver.models.Tag;
import com.tick42.quicksilver.models.User;

import java.util.List;

public interface UserRepository extends GenericRepository<User> {
    User findByUsername(String username);
    List<User> findUsersByActiveState(boolean state);
}
