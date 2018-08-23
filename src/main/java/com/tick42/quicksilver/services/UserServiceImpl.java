package com.tick42.quicksilver.services;

import com.tick42.quicksilver.models.User;
import com.tick42.quicksilver.repositories.UserRepositoryImpl;
import com.tick42.quicksilver.repositories.base.GenericRepository;
import com.tick42.quicksilver.services.base.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final GenericRepository<User> userRepository;

    @Autowired
    public UserServiceImpl(GenericRepository<User> genericRepository) {
        this.userRepository = genericRepository;
    }

    @Override
    public void create(User user) {
        userRepository.create(user);
    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id);
    }

}
