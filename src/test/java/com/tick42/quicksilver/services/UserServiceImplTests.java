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

import static org.mockito.ArgumentMatchers.any;
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

        when(userRepository.findAll()).thenReturn(users);

        //Act
        List<UserDTO> usersDTO = userService.findAll("all");

        //Assert
        Assert.assertEquals(2, usersDTO.size());
        Assert.assertTrue(usersDTO.get(0).getIsActive());
        Assert.assertTrue(usersDTO.get(1).getIsActive());
    }

    @Test(expected = InvalidStateException.class)
    public void findAllUsers_WithWrongState_ShouldThrow(){
        //Act
        List<UserDTO> usersDTO = userService.findAll("ActiveUsersInvalidInput");
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

        //Arrange
        User registeredUser = new User();
        registeredUser.setUsername("Test");

        UserSpec newRegistration = new UserSpec();
        newRegistration.setUsername("Test");
        newRegistration.setPassword("TestPassword");
        newRegistration.setRepeatPassword("TestPassword");

        when(userRepository.findByUsername("Test")).thenReturn(registeredUser);

        //Act
        userService.register(newRegistration, "ROLE_USER");
    }

    @Test(expected = PasswordsMissMatchException.class)
    public void RegisterUser_WithNotMatchingPasswords_shouldThrow() {

        //Arrange
        UserSpec newRegistration = new UserSpec();
        newRegistration.setUsername("Test");
        newRegistration.setPassword("TestPassword");
        newRegistration.setRepeatPassword("TestPasswordMissMatch");

        when(userRepository.findByUsername("Test")).thenReturn(null);

        //Act
        userService.register(newRegistration, "ROLE_USER");
    }

    @Test()
    public void SuccessfulRegistration_withAvailable() {

        //Arrange

        UserSpec newRegistration = new UserSpec();
        newRegistration.setUsername("Test");
        newRegistration.setPassword("testPassword");
        newRegistration.setRepeatPassword("testPassword");
        when(userRepository.findByUsername("Test")).thenReturn(null);

        //Act
        userService.register(newRegistration, "ROLE_USER");
    }

    @Test()
    public void SuccessfulRegistration_Role_User() {
        //Arrange
        UserSpec newRegistration = new UserSpec();
        newRegistration.setUsername("Test");
        newRegistration.setPassword("testPassword");
        newRegistration.setRepeatPassword("testPassword");

        User newUser = new User();
        newUser.setUsername("Test");
        newUser.setPassword("testPassword");
        newUser.setRole("USER_ROLE");

        when(userRepository.findByUsername("Test")).thenReturn(null);
        when(userRepository.create(any(User.class))).thenReturn(newUser);
        //Act
        User user = userService.register(newRegistration, "ROLE_USER");

        //Assert
        Assert.assertEquals(user.getRole(),"USER_ROLE");
    }

    @Test()
    public void SuccessfulRegistration_Role_Admin() {
        //Arrange
        UserSpec newRegistration = new UserSpec();
        newRegistration.setUsername("Test");
        newRegistration.setPassword("testPassword");
        newRegistration.setRepeatPassword("testPassword");

        User newUser = new User();
        newUser.setUsername("Test");
        newUser.setPassword("testPassword");
        newUser.setRole("ADMIN_ROLE");

        when(userRepository.findByUsername("Test")).thenReturn(null);
        when(userRepository.create(any(User.class))).thenReturn(newUser);
        //Act
        User user = userService.register(newRegistration, "ADMIN_ROLE");

        //Assert
        Assert.assertEquals(user.getRole(),"ADMIN_ROLE");
    }
}
