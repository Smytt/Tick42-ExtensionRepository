package com.tick42.quicksilver.services;

import com.tick42.quicksilver.exceptions.InvalidStateException;
import com.tick42.quicksilver.exceptions.UserNotFoundException;
import com.tick42.quicksilver.models.DTO.ExtensionDTO;
import com.tick42.quicksilver.models.Extension;
import com.tick42.quicksilver.models.GitHubModel;
import com.tick42.quicksilver.models.Spec.ExtensionSpec;
import com.tick42.quicksilver.models.Tag;
import com.tick42.quicksilver.models.User;
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
import java.util.Date;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExtensionServiceImplTests {
    @Mock
    private ExtensionRepository extensionRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TagService tagService;

    @Mock
    private GitHubService gitHubService;

    @InjectMocks
    private ExtensionServiceImpl extensionService;

    @Test(expected = UserNotFoundException.class)
    public void create_whenUserIsNull_shouldThrow() {
        //Arrange
        User user = null;
        when(userRepository.findById(1)).thenReturn(user);

        //Act
        extensionService.create(new ExtensionSpec(), 1);
    }

//    @Test
//    public void create_whenUserExists_returnExtensionDTO() {
//        //Arrange
//        int userId = 1;
//        Date date = new Date();
//
//        User user = new User();
//        user.setId(userId);
//
//        ExtensionSpec extensionSpec = new ExtensionSpec();
//        extensionSpec.setName("name");
//        extensionSpec.setVersion("1.0");
//        extensionSpec.setDescription("description");
//        extensionSpec.setGithub("gitHubLink");
//        extensionSpec.setTags("tag1, tag2");
//
//        List<Tag> tags = Arrays.asList(new Tag("tag1"), new Tag("tag2"));
//
//        GitHubModel github = new GitHubModel();
//        github.setOpenIssues(10);
//        github.setPullRequests(20);
//        github.setLastCommit(date);
//        github.setLink(extensionSpec.getGithub());
//
//        Extension extension = new Extension();
//        extension.setId(1);
//        extension.setOwner(user);
//        extension.setGithub(github);
//        extension.setTags(tags);
//        extension.setName(extensionSpec.getName());
//        extension.setVersion(extensionSpec.getVersion());
//        extension.setDescription(extensionSpec.getDescription());
//
//        when(userRepository.findById(userId)).thenReturn(user);
//        when(tagService.generateTags(extensionSpec.getTags())).thenReturn(tags);
//        when(gitHubService.generateGitHub(extensionSpec.getGithub())).thenReturn(github);
//        when(extensionRepository.create(extension)).thenReturn(extension);
//
//        //Act
//        ExtensionDTO createdExtensionDTO = extensionService.create(extensionSpec, userId);
//
//        //Assert
//        Assert.assertTrue(true);
//    }

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
        ExtensionDTO extensionShouldThrow = extensionService.setFeaturedState(1, "wrongString");

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
        ExtensionDTO extensionShouldThrow = extensionService.setPublishedState(1, "wrongString");

        //Assert
    }

    @Test
    public void findPending_shouldReturnListOfPendingExtensionDTOs() {
        //Arrange
        Extension extension1 = new Extension();
        Extension extension2 = new Extension();
        extension1.setIsPending(true);
        extension2.setIsPending(true);
        List<Extension> extensions = Arrays.asList(extension1, extension2);

        when(extensionRepository.findPending()).thenReturn(extensions);

        //Act
        List<ExtensionDTO> pendingExtensionDTOs = extensionService.findPending();

        //Assert
        Assert.assertEquals(2, pendingExtensionDTOs.size());
        Assert.assertTrue(pendingExtensionDTOs.get(0).isPending());
        Assert.assertTrue(pendingExtensionDTOs.get(1).isPending());
    }

    @Test
    public void findFeatured_shouldReturnListOfFeaturedExtensionDTOs() {
        //Arrange
        Extension extension1 = new Extension();
        Extension extension2 = new Extension();
        extension1.setIsFeatured(true);
        extension2.setIsFeatured(true);
        List<Extension> extensions = Arrays.asList(extension1, extension2);

        when(extensionRepository.findFeatured()).thenReturn(extensions);

        //Act
        List<ExtensionDTO> featuredExtensionDTOs = extensionService.findFeatured();

        //Assert
        Assert.assertEquals(2, featuredExtensionDTOs.size());
        Assert.assertTrue(featuredExtensionDTOs.get(0).isFeatured());
        Assert.assertTrue(featuredExtensionDTOs.get(1).isFeatured());
    }


}
