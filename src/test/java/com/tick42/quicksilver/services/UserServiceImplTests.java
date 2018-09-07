package com.tick42.quicksilver.services;


import com.tick42.quicksilver.exceptions.*;
import com.tick42.quicksilver.models.DTO.UserDTO;
import com.tick42.quicksilver.models.Extension;
import com.tick42.quicksilver.models.Spec.UserSpec;
import com.tick42.quicksilver.models.User;
import com.tick42.quicksilver.repositories.base.ExtensionRepository;
import com.tick42.quicksilver.repositories.base.UserRepository;
import com.tick42.quicksilver.services.base.GitHubService;
import com.tick42.quicksilver.services.base.TagService;
import com.tick42.quicksilver.services.base.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.floatThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test(expected = UserNotFoundException.class)
    public void findById_whenUserDoesNotExist_shouldThrow() {
        //Arrange
        User user = new User();
        when(userRepository.findById(1)).thenReturn(null);

        //Act
        userService.findById(1, user);
    }

    @Test(expected = UserProfileUnavailableException.class)
    public void findById_whenUserProfileIsNotActive_andCurrentUserIsNotAdmin_shouldThrow(){
        //Arrange
        User loggedUser = new User();
        loggedUser.setUsername("test");
        loggedUser.setRole("ROLE_USER");
        loggedUser.setIsActive(true);
        User blockedUser = new User();
        blockedUser.setIsActive(false);
        blockedUser.setUsername("test");
        blockedUser.setRole("ROLE_USER");
        when(userRepository.findById(1)).thenReturn(blockedUser);

        //Act
        userService.findById(1,loggedUser);
    }
    @Test()
    public void findById_whenUserProfileIsNotActive_andCurrentUserIsAdmin(){
        //Arrange
        User loggedUser = new User();
        loggedUser.setUsername("test");
        loggedUser.setRole("ROLE_ADMIN");
        loggedUser.setIsActive(true);
        User blockedUser = new User();
        blockedUser.setIsActive(false);
        blockedUser.setUsername("test");
        blockedUser.setRole("ROLE_USER");
        when(userRepository.findById(1)).thenReturn(blockedUser);

        //Act
        userService.findById(1,loggedUser);
    }

    @Test
    public void findBlockedUsers_ShouldReturnBlockedUsers() {
        //Arrange
        User user = new User();
        User user1 = new User();
        user.setIsActive(false);
        user1.setIsActive(false);
        List<User> users = Arrays.asList(user, user1);

        when(userRepository.findUsersByState(false)).thenReturn(users);

        //Act
        List<UserDTO> usersDTO = userService.findAll("blocked");

        //Assert
        Assert.assertEquals(2, usersDTO.size());
        Assert.assertFalse(usersDTO.get(0).getIsActive());
        Assert.assertFalse(usersDTO.get(1).getIsActive());
    }

    @Test
    public void findActiveUsers_ShouldReturnBlockedUsers() {
        //Arrange
        User user = new User();
        User user1 = new User();
        user.setIsActive(true);
        user1.setIsActive(true);
        List<User> users = Arrays.asList(user, user1);

        when(userRepository.findUsersByState(true)).thenReturn(users);

        //Act
        List<UserDTO> usersDTO = userService.findAll("active");

        //Assert
        Assert.assertEquals(2, usersDTO.size());
        Assert.assertTrue(usersDTO.get(0).getIsActive());
        Assert.assertTrue(usersDTO.get(1).getIsActive());
    }

    @Test
    public void findAllUsers_ShouldReturnAllUsers() {
        //Arrange
        User user = new User();
        User user1 = new User();
        user.setIsActive(true);
        user1.setIsActive(true);
        List<User> users = Arrays.asList(user, user1);

        when(userRepository.findUsersByState(true)).thenReturn(users);

        //Act
        List<UserDTO> usersDTO = userService.findAll("active");

        //Assert
        Assert.assertEquals(2, usersDTO.size());
        Assert.assertTrue(usersDTO.get(0).getIsActive());
        Assert.assertTrue(usersDTO.get(1).getIsActive());
    }

    @Test(expected = InvalidCredentialsException.class)
    public void LoginUserWithWrongPassword_InvalidCredentialsException_shouldThrow() {

        //Arrange
        User user = new User();
        user.setUsername("test");
        user.setPassword("k");

        User userInvalid = new User();
        userInvalid.setUsername("test");
        userInvalid.setPassword("wrong password");

        when(userRepository.findByUsername("test")).thenReturn(user);

        //Act
        userService.login(userInvalid);
    }

    @Test(expected = UsernameExistsException.class)
    public void RegisterUser_WithAlreadyTakenUsername_shouldThrow() {

        //Assert
        User registeredUser = new User();
        registeredUser.setUsername("Test");

        UserSpec newRegistration = new UserSpec();
        newRegistration.setUsername("Test");
        when(userRepository.findByUsername("Test")).thenReturn(registeredUser);

        //Act
        userService.register(newRegistration, "ROLE_USER");
    }

    @Test()
    public void SuccessfulRegistration_withAvailable() {

        //Assert

        UserSpec newRegistration = new UserSpec();
        newRegistration.setUsername("Test");
        when(userRepository.findByUsername("Test")).thenReturn(null);

        //Act
        userService.register(newRegistration, "ROLE_USER");
    }

    @Test()
    public void SuccessfulRegistration_Role_Admin() {

        //Assert

        UserSpec newRegistration = new UserSpec();
        newRegistration.setUsername("Test");
        when(userRepository.findByUsername("Test")).thenReturn(null);

        //Act
        userService.register(newRegistration, "ROLE_USER");
    }
}
