package com.tick42.quicksilver.services;

import com.tick42.quicksilver.models.User;
import com.tick42.quicksilver.repositories.base.UserRepository;
import com.tick42.quicksilver.security.JwtGenerator;
import com.tick42.quicksilver.services.base.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

//    private final GenericRepository<User> userRepository;
    private final UserRepository userRepository;
    private JwtGenerator jwtGenerator;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, JwtGenerator jwtGenerator) {
//        this.userRepository = genericRepository;
        this.userRepository = userRepository;
        this.jwtGenerator = jwtGenerator;
    }

    @Override
    public void create(User user) {
        userRepository.create(user);
    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public String createTokenData(User user){
        String username = user.getUsername();
        String password = user.getPassword();
        User user1 = userRepository.authenticate(username,password);
        return jwtGenerator.generate(user1);
    }
}
