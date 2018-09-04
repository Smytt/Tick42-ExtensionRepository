package com.tick42.quicksilver.services.base;

import com.tick42.quicksilver.models.DTO.UserDTO;
import com.tick42.quicksilver.models.Spec.UserSpec;
import com.tick42.quicksilver.models.User;
import org.apache.http.auth.InvalidCredentialsException;


import java.util.List;

public interface UserService {

    UserDTO setState(int id, String state);

    List<UserDTO> findAll(String state);

    UserDTO findById(int id, User loggedUser);

    User login(User user) throws InvalidCredentialsException;

    User register(UserSpec userSpec);

    User registerAdmin(UserSpec userSpec);

    String generateToken(User user);
}
