package com.tick42.quicksilver.repositories.base;

import com.tick42.quicksilver.models.Tag;
import com.tick42.quicksilver.models.User;

public interface UserRepository extends GenericRepository<User> {
    User authenticate(String username, String password);
}
