package com.tick42.quicksilver.services;
import com.tick42.quicksilver.exceptions.InvalidCredentialsException;
import com.tick42.quicksilver.exceptions.*;
import com.tick42.quicksilver.models.DTO.UserDTO;
import com.tick42.quicksilver.models.Spec.ChangeUserPasswordSpec;
import com.tick42.quicksilver.models.Spec.UserSpec;
import com.tick42.quicksilver.models.User;
import com.tick42.quicksilver.repositories.base.UserRepository;
import com.tick42.quicksilver.security.JwtGenerator;
import com.tick42.quicksilver.services.base.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public UserDTO setState(int id, String state) {
        User user = userRepository.findById(id);

        if (user == null) {
            throw new UserNotFoundException("User not found.");
        }

        switch (state) {
            case "enable":
                user.setIsActive(true);
                break;
            case "block":
                user.setIsActive(false);
                break;
            default:
                throw new InvalidStateException("\"" + state + "\" is not a valid user state. Use \"enable\" or \"block\".");
        }
        return new UserDTO(userRepository.update(user));
    }

    @Override
    public List<UserDTO> findAll(String state) {
        List<User> users = new ArrayList<>();

        if (state == null) {
            state = "";
        }

        switch (state) {
            case "active":
                users = userRepository.findUsersByState(true);
                break;
            case "blocked":
                users = userRepository.findUsersByState(false);
                break;
            case "all":
                users = userRepository.findAll();
                break;
            default:
                throw new InvalidStateException("\"" + state + "\" is not a valid user state. Use \"active\" , \"blocked\" or \"all\".");
        }

        List<UserDTO> usersDto = users
                .stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
        return usersDto;
    }

    @Override
    public UserDTO findById(int id, User loggedUser) {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new UserNotFoundException("User doesn't exist.");
        }

        if (!user.getIsActive() && !loggedUser.getRole().equals("ROLE_ADMIN")) {
            throw new UserProfileUnavailableException("User profile is disabled.");
        }

        return new UserDTO(user);
    }

    @Override
    public User login(User user) throws InvalidCredentialsException {
        String username = user.getUsername();
        String password = user.getPassword();

        User foundUser = userRepository.findByUsername(username);

        if (foundUser == null) {
            throw new InvalidCredentialsException("Invalid credentials.");
        }

        if (!password.equals(foundUser.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials.");
        }

        if (!foundUser.getIsActive()) {
            throw new DisabledUserException("User is disabled.");
        }

        return foundUser;
    }

    @Override
    public User register(UserSpec userSpec, String role) {
        User user = userRepository.findByUsername(userSpec.getUsername());

        if (user != null) {
            throw new UsernameExistsException("Username is already taken.");
        }

        if (!userSpec.getPassword().equals(userSpec.getRepeatPassword())) {
            throw new PasswordsMissMatchException("Passwords must match.");
        }

        user = new User(userSpec, role);
        return userRepository.create(user);
    }
    @Override
    public String generateToken(User user) {
        if (jwtGenerator.generate(user) == null) {
            throw new GenerateTokenException("Couldn't generate authentication token");
        }

        return jwtGenerator.generate(user);
    }

    @Override
    public UserDTO changePassword(int id, ChangeUserPasswordSpec changePasswordSpec){
        User user = userRepository.findById(id);
        if (!changePasswordSpec.getNewPassword().equals(changePasswordSpec.getRepeatNewPassword())){
            throw new PasswordsMissMatchException("passwords don't match");
        }

        if (!user.getPassword().equals(changePasswordSpec.getCurrentPassword())){
            throw new InvalidCredentialsException("Invalid current password.");
        }
        user.setPassword(changePasswordSpec.getNewPassword());
        userRepository.update(user);
        return new UserDTO(user);

    }
}
