package com.tick42.quicksilver.services;

import com.tick42.quicksilver.exceptions.ExtensionNotFoundException;
import com.tick42.quicksilver.exceptions.InvalidParameterException;
import com.tick42.quicksilver.exceptions.UnauthorizedExtensionModificationException;
import com.tick42.quicksilver.exceptions.UserNotFoundException;
import com.tick42.quicksilver.models.Extension;
import com.tick42.quicksilver.models.GitHubModel;
import com.tick42.quicksilver.models.User;
import com.tick42.quicksilver.repositories.base.ExtensionRepository;
import com.tick42.quicksilver.repositories.base.GitHubRepository;
import com.tick42.quicksilver.repositories.base.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FileServiceImplTests {

    @Mock
    MultipartFile multipartFile;

    @Mock
    ExtensionRepository extensionRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    private FileServiceImpl fileService;

    @Test(expected = ExtensionNotFoundException.class)
    public void storeFile_whenExtensionNotExisitng_ShouldThrow() {
        //Arrange
        when(extensionRepository.findById(1)).thenReturn(null);

        //Act
        fileService.storeFile(multipartFile, 1, 1);
    }

    @Test(expected = ExtensionNotFoundException.class)
    public void storeImage_whenExtensionNotExisitng_ShouldThrow() {
        //Arrange
        when(extensionRepository.findById(1)).thenReturn(null);

        //Act
        fileService.storeImage(multipartFile, 1, 1);
    }

    @Test(expected = UserNotFoundException.class)
    public void storeFile_whenUserNotExisitng_ShouldThrow() {
        //Arrange
        Extension extension = new Extension();
        when(userRepository.findById(1)).thenReturn(null);
        when(extensionRepository.findById(1)).thenReturn(extension);

        //Act
        fileService.storeFile(multipartFile, 1, 1);
    }

    @Test(expected = UserNotFoundException.class)
    public void storeImage_whenUserNotExisitng_ShouldThrow() {
        //Arrange
        Extension extension = new Extension();
        when(userRepository.findById(1)).thenReturn(null);
        when(extensionRepository.findById(1)).thenReturn(extension);

        //Act
        fileService.storeImage(multipartFile, 1, 1);
    }

    @Test(expected = UnauthorizedExtensionModificationException.class)
    public void storeFile_whenUserisNotOwnerAndNotAdmin_ShouldThrow() {
        //Arrange
        User user = new User();
        user.setId(1);
        user.setRole("USER");
        User owner = new User();
        owner.setId(2);
        Extension extension = new Extension();
        extension.setOwner(owner);

        when(userRepository.findById(1)).thenReturn(user);
        when(extensionRepository.findById(1)).thenReturn(extension);

        //Act
        fileService.storeFile(multipartFile, 1, 1);
    }

    @Test(expected = UnauthorizedExtensionModificationException.class)
    public void storeImage_whenUserisNotOwnerAndNotAdmin_ShouldThrow() {
        //Arrange
        User user = new User();
        user.setId(1);
        user.setRole("USER");
        User owner = new User();
        owner.setId(2);
        Extension extension = new Extension();
        extension.setOwner(owner);

        when(userRepository.findById(1)).thenReturn(user);
        when(extensionRepository.findById(1)).thenReturn(extension);

        //Act
        fileService.storeImage(multipartFile, 1, 1);
    }

}
