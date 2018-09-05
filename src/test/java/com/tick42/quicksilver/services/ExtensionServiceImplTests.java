package com.tick42.quicksilver.services;

import com.tick42.quicksilver.exceptions.InvalidStateException;
import com.tick42.quicksilver.models.DTO.ExtensionDTO;
import com.tick42.quicksilver.models.Extension;
import com.tick42.quicksilver.repositories.base.ExtensionRepository;
import com.tick42.quicksilver.repositories.base.UserRepository;
import com.tick42.quicksilver.security.JwtValidator;
import com.tick42.quicksilver.services.base.ExtensionService;
import com.tick42.quicksilver.services.base.GitHubService;
import com.tick42.quicksilver.services.base.TagService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExtensionServiceImplTests {
    @Mock
    private ExtensionRepository extensionRepository;

    @InjectMocks
    private ExtensionServiceImpl extensionService;

    @Test
    public void setFeaturedState_whenSetToFeatured_returnFeaturedExtensionDTO() {
        // Arrange
        Extension extension = new Extension();
        extension.setIsFeatured(false);

        when(extensionRepository.findById(1)).thenReturn(extension);

        //Act
        ExtensionDTO extensionShouldBeFeatured = extensionService.setFeaturedState(1, "feature");

        //Assert
        Assert.assertTrue(extensionShouldBeFeatured.isFeatured());
    }

    @Test
    public void setFeaturedState_whenSetToUnfeatured_returnUnfeaturedExtensionDTO() {
        // Arrange
        Extension extension = new Extension();
        extension.setIsFeatured(true);

        when(extensionRepository.findById(1)).thenReturn(extension);

        //Act
        ExtensionDTO extensionShouldBeUnfeatured = extensionService.setFeaturedState(1, "unfeature");

        //Assert
        Assert.assertFalse(extensionShouldBeUnfeatured.isFeatured());
    }

    @Test(expected = InvalidStateException.class)
    public void setFeaturedState_whenGivenInvalidParameter_shouldThrow() {
        // Arrange
        Extension extension = new Extension();
        extension.setIsFeatured(true);

        when(extensionRepository.findById(1)).thenReturn(extension);

        //Act
        ExtensionDTO extensionShouldThrow = extensionService.setFeaturedState(1, "wrong-string");

        //Assert
    }

    @Test
    public void setPublishedState_whenSetToPublished_returnPublishedExtensionDTO() {
        // Arrange
        Extension extension = new Extension();
        extension.setIsPending(true);

        when(extensionRepository.findById(1)).thenReturn(extension);

        //Act
        ExtensionDTO extensionShouldBePending = extensionService.setPublishedState(1, "publish");

        //Assert
        Assert.assertFalse(extensionShouldBePending.isPending());
    }

    @Test
    public void setPublishedState_whenSetToUnpublished_returnUnpublishedExtensionDTO() {
        // Arrange
        Extension extension = new Extension();
        extension.setIsPending(false);

        when(extensionRepository.findById(1)).thenReturn(extension);

        //Act
        ExtensionDTO extensionShouldBeUnpublished = extensionService.setPublishedState(1, "unpublish");

        //Assert
        Assert.assertTrue(extensionShouldBeUnpublished.isPending());
    }

    @Test(expected = InvalidStateException.class)
    public void setPublishedState_whenGivenInvalidParameter_shouldThrow() {
        // Arrange
        Extension extension = new Extension();
        extension.setIsFeatured(true);

        when(extensionRepository.findById(1)).thenReturn(extension);

        //Act
        ExtensionDTO extensionShouldThrow = extensionService.setPublishedState(1, "wrong-string");

        //Assert
    }

    @Test
    public void findPending_shouldReturnListOfPendingExtensionDTOs() {
        //Arrange
        Extension extensionPending1 = new Extension();
        Extension extensionPending2 = new Extension();
        extensionPending1.setIsPending(true);
        extensionPending2.setIsPending(true);
        List<Extension> extensions = Arrays.asList(extensionPending1, extensionPending2);

        when(extensionRepository.findPending()).thenReturn(extensions);

        //Act
        List<ExtensionDTO> pendingExtensionDTOs = extensionService.findPending();

        //Assert
        Assert.assertEquals(2, pendingExtensionDTOs.size());
        Assert.assertTrue(pendingExtensionDTOs.get(0).isPending());
        Assert.assertTrue(pendingExtensionDTOs.get(1).isPending());
    }
}
