package com.tick42.quicksilver.services;

import com.tick42.quicksilver.exceptions.*;
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
import java.util.stream.Collectors;

import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.mockito.ArgumentMatchers.any;
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

    @Test
    public void create_whenUserExists_returnExtensionDTO() {
        //Arrange
        int userId = 1;
        Date uploadTime = new Date();
        Date commitTime = new Date();

        ExtensionSpec extensionSpec = new ExtensionSpec();
        extensionSpec.setName("name");
        extensionSpec.setVersion("1.0");
        extensionSpec.setDescription("description");
        extensionSpec.setGithub("gitHubLink");
        extensionSpec.setTags("tag1, tag2");

        User user = new User();
        user.setUsername("username");
        user.setId(userId);
        when(userRepository.findById(userId)).thenReturn(user);

        List<Tag> tags = Arrays.asList(new Tag("tag1"), new Tag("tag2"));
        when(tagService.generateTags(extensionSpec.getTags())).thenReturn(tags);

        GitHubModel github = new GitHubModel();
        github.setLastCommit(commitTime);
        github.setPullRequests(10);
        github.setOpenIssues(20);
        github.setLink("gitHubLink");
        when(gitHubService.generateGitHub(extensionSpec.getGithub())).thenReturn(github);

        Extension extension = new Extension();
        extension.setName("name");
        extension.setVersion("1.0");
        extension.setDescription("description");
        extension.setIsPending(true);
        extension.setOwner(user);
        extension.setUploadDate(uploadTime);
        extension.setTags(tags);
        extension.setGithub(github);

        ExtensionDTO expectedExtensionDTO = new ExtensionDTO();
        expectedExtensionDTO.setName("name");
        expectedExtensionDTO.setVersion("1.0");
        expectedExtensionDTO.setDescription("description");
        expectedExtensionDTO.setPending(true);
        expectedExtensionDTO.setUploadDate(uploadTime);
        expectedExtensionDTO.setOwnerName("username");
        expectedExtensionDTO.setOwnerId(1);
        expectedExtensionDTO.setUploadDate(uploadTime);
        expectedExtensionDTO.setTags(Arrays.asList("tag1", "tag2"));
        expectedExtensionDTO.setGitHubLink("gitHubLink");
        expectedExtensionDTO.setLastCommit(commitTime);
        expectedExtensionDTO.setPullRequests(10);
        expectedExtensionDTO.setOpenIssues(20);
        expectedExtensionDTO.setFeatured(false);
        expectedExtensionDTO.setTimesDownloaded(0);
        expectedExtensionDTO.setVersion("1.0");

        when(extensionRepository.create(any(Extension.class))).thenReturn(extension);

        //Act
        ExtensionDTO createdExtensionDTO = extensionService.create(extensionSpec, userId);

        //Assert
        Assert.assertThat(expectedExtensionDTO, samePropertyValuesAs(createdExtensionDTO));
    }

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

    @Test(expected = ExtensionNotFoundException.class)
    public void findById_whenExtensionDoesntExist_shouldThrow() {
        //Arrange
        User user = new User();
        when(extensionRepository.findById(1)).thenReturn(null);

        //Act
        extensionService.findById(1, user);
    }

    @Test(expected = ExtensionUnavailableException.class)
    public void findById_whenOwnerIsInactiveaAndUserIsNull_shouldThrow() {
        //Arrange
        User user = null;

        User owner = new User();
        owner.setIsActive(false);

        Extension extension = new Extension();
        extension.setOwner(owner);

        when(extensionRepository.findById(1)).thenReturn(extension);

        //Act
        extensionService.findById(1, user);
    }

    @Test(expected = ExtensionUnavailableException.class)
    public void findById_whenOwnerIsInactiveaAndUserIsNotAdmin_shouldThrow() {
        //Arrange
        User user = new User();
        user.setRole("ROLE_USER");

        User owner = new User();
        owner.setIsActive(false);

        Extension extension = new Extension();
        extension.setOwner(owner);

        when(extensionRepository.findById(1)).thenReturn(extension);

        //Act
        extensionService.findById(1, user);
    }

    @Test(expected = ExtensionUnavailableException.class)
    public void findById_whenExtensionIsPendingAndOwnerIsActiveAndUserIsNull_shouldThrow() {
        //Arrange
        User user = null;

        User owner = new User();
        owner.setIsActive(true);

        Extension extension = new Extension();
        extension.setIsPending(true);
        extension.setOwner(owner);

        when(extensionRepository.findById(1)).thenReturn(extension);

        //Act
        extensionService.findById(1, user);
    }

    @Test(expected = ExtensionUnavailableException.class)
    public void findById_whenExtensionIsPendingAndOwnerIsActiveAndUserIsNotOwnerAndNotAdmin_shouldThrow() {
        //Arrange
        User user = new User();
        user.setId(1);
        user.setRole("ROLE_USER");

        User owner = new User();
        owner.setIsActive(true);
        owner.setId(2);

        Extension extension = new Extension();
        extension.setIsPending(true);
        extension.setOwner(owner);

        when(extensionRepository.findById(1)).thenReturn(extension);

        //Act
        extensionService.findById(1, user);
    }

    @Test
    public void findById_whenExtensionIsPendingAndOwnerIsInactiveAndUserIsAdmin_shouldReturnExtensionDTO() {
        //Arrange
        User user = new User();
        user.setId(1);
        user.setRole("ROLE_ADMIN");

        User owner = new User();
        owner.setIsActive(false);

        Extension extension = new Extension();
        extension.setId(1);
        extension.setIsPending(true);
        extension.setOwner(owner);

        when(extensionRepository.findById(1)).thenReturn(extension);

        //Act
        ExtensionDTO expectedExtensionDTO = extensionService.findById(1, user);

        //Assert
        Assert.assertEquals(extension.getId(), expectedExtensionDTO.getId());
    }

    @Test
    public void findById_whenExtensionIsPendingAndOwnerIsActiveAndUserIsNotAdmin_shouldReturnExtensionDTO() {
        //Arrange
        User user = new User();
        user.setId(1);
        user.setRole("ROLE_USER");

        User owner = new User();
        owner.setIsActive(true);
        owner.setId(1);

        Extension extension = new Extension();
        extension.setId(1);
        extension.setIsPending(true);
        extension.setOwner(owner);

        when(extensionRepository.findById(1)).thenReturn(extension);

        //Act
        ExtensionDTO expectedExtensionDTO = extensionService.findById(1, user);

        //Assert
        Assert.assertEquals(extension.getId(), expectedExtensionDTO.getId());
    }

    @Test
    public void findById_whenExtensionIsNotPendingAndOwnerIsActiveAndUserIsNotOwnerAndNotAdmin_shouldReturnExtensionDTO() {
        //Arrange
        User user = new User();
        user.setId(1);
        user.setRole("ROLE_USER");

        User owner = new User();
        owner.setIsActive(true);
        owner.setId(2);

        Extension extension = new Extension();
        extension.setId(1);
        extension.setIsPending(false);
        extension.setOwner(owner);

        when(extensionRepository.findById(1)).thenReturn(extension);

        //Act
        ExtensionDTO expectedExtensionDTO = extensionService.findById(1, user);

        //Assert
        Assert.assertEquals(extension.getId(), expectedExtensionDTO.getId());
    }

    @Test(expected = ExtensionNotFoundException.class)
    public void update_whenExtensionDoesntExist_ShouldThrow() {
        //Assert
        when(extensionRepository.findById(1)).thenReturn(null);

        //Act
        extensionService.update(1, new ExtensionSpec(), 1);
    }

    @Test(expected = UserNotFoundException.class)
    public void update_whenUserDoesntExist_ShouldThrow() {
        //Assert
        Extension extension = new Extension();

        when(extensionRepository.findById(1)).thenReturn(extension);
        when(userRepository.findById(1)).thenReturn(null);

        //Act
        extensionService.update(1, new ExtensionSpec(), 1);
    }

    @Test(expected = UnauthorizedExtensionModificationException.class)
    public void update_whenUserIsNotOwnerAndNotAdmin_ShouldThrow() {
        //Assert
        User user = new User();
        user.setId(1);
        user.setRole("ROLE_USER");

        User owner = new User();
        owner.setId(2);

        Extension extension = new Extension();
        extension.setOwner(owner);

        when(extensionRepository.findById(1)).thenReturn(extension);
        when(userRepository.findById(1)).thenReturn(user);

        //Act
        extensionService.update(1, new ExtensionSpec(), 1);
    }

    @Test
    public void update_whenUserIsOwner_returnUpdatedExtensionDTO() {
        //Assert
        Date commitTime = new Date();

        User user = new User();
        user.setId(1);
        user.setRole("ROLE_USER");

        User owner = new User();
        owner.setId(1);

        ExtensionSpec extensionSpec = new ExtensionSpec();
        extensionSpec.setName("newName");
        extensionSpec.setVersion("1.0");
        extensionSpec.setDescription("description");
        extensionSpec.setGithub("gitHubLink");
        extensionSpec.setTags("tag1, tag2");

        List<Tag> tags = Arrays.asList(new Tag("tag1"), new Tag("tag2"));
        when(tagService.generateTags(extensionSpec.getTags())).thenReturn(tags);

        GitHubModel github = new GitHubModel();
        github.setLastCommit(commitTime);
        github.setPullRequests(10);
        github.setOpenIssues(20);
        github.setLink("gitHubLink");
        when(gitHubService.generateGitHub(extensionSpec.getGithub())).thenReturn(github);

        Extension extension = new Extension();
        extension.setOwner(owner);
        extension.setName("oldName");
        extension.setVersion("1.0");
        extension.setDescription("description");
        extension.setGithub(github);
        extension.setTags(tags);

        when(extensionRepository.findById(1)).thenReturn(extension);
        when(userRepository.findById(1)).thenReturn(user);

        //Act
        ExtensionDTO expectedExtensionDTO = extensionService.update(1, extensionSpec, 1);

        //Assert
        Assert.assertEquals(expectedExtensionDTO.getName(), "newName");
    }

    @Test
    public void update_whenUserIsAdmin_returnUpdatedExtensionDTO() {
        //Assert
        Date commitTime = new Date();

        User user = new User();
        user.setId(2);
        user.setRole("ROLE_ADMIN");

        User owner = new User();
        owner.setId(1);

        ExtensionSpec extensionSpec = new ExtensionSpec();
        extensionSpec.setName("newName");
        extensionSpec.setVersion("1.0");
        extensionSpec.setDescription("description");
        extensionSpec.setGithub("gitHubLink");
        extensionSpec.setTags("tag1, tag2");

        List<Tag> tags = Arrays.asList(new Tag("tag1"), new Tag("tag2"));
        when(tagService.generateTags(extensionSpec.getTags())).thenReturn(tags);

        GitHubModel github = new GitHubModel();
        github.setLastCommit(commitTime);
        github.setPullRequests(10);
        github.setOpenIssues(20);
        github.setLink("gitHubLink");
        when(gitHubService.generateGitHub(extensionSpec.getGithub())).thenReturn(github);

        Extension extension = new Extension();
        extension.setOwner(owner);
        extension.setName("oldName");
        extension.setVersion("1.0");
        extension.setDescription("description");
        extension.setGithub(github);
        extension.setTags(tags);

        when(extensionRepository.findById(1)).thenReturn(extension);
        when(userRepository.findById(1)).thenReturn(user);

        //Act
        ExtensionDTO expectedExtensionDTO = extensionService.update(1, extensionSpec, 1);

        //Assert
        Assert.assertEquals(expectedExtensionDTO.getName(), "newName");
    }

    @Test(expected = ExtensionNotFoundException.class)
    public void delete_whenExtensionDoesntExist_ShouldThrow() {
        //Assert
        when(extensionRepository.findById(1)).thenReturn(null);

        //Act
        extensionService.delete(1, 1);
    }

    @Test(expected = UserNotFoundException.class)
    public void delete_whenUserDoesntExist_ShouldThrow() {
        //Assert
        Extension extension = new Extension();

        when(extensionRepository.findById(1)).thenReturn(extension);
        when(userRepository.findById(1)).thenReturn(null);

        //Act
        extensionService.delete(1, 1);
    }

    @Test
    public void delete_whenUserIsOwner_ShouldNotThrow() {
        //Assert
        User user = new User();
        user.setId(1);
        user.setRole("ROLE_USER");

        User owner = new User();
        owner.setId(1);

        Extension extension = new Extension();
        extension.setOwner(owner);

        when(extensionRepository.findById(1)).thenReturn(extension);
        when(userRepository.findById(1)).thenReturn(user);

        //Act
        try {
            extensionService.delete(1, 1);
            Assert.assertTrue(Boolean.TRUE);
        } catch (Exception ex) {
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void delete_whenUserIsAdmin_ShouldNotThrow() {
        //Assert
        User user = new User();
        user.setId(1);
        user.setRole("ROLE_ADMIN");

        User owner = new User();
        owner.setId(2);

        Extension extension = new Extension();
        extension.setOwner(owner);

        when(extensionRepository.findById(1)).thenReturn(extension);
        when(userRepository.findById(1)).thenReturn(user);

        //Act
        try {
            extensionService.delete(1, 1);
            Assert.assertTrue(Boolean.TRUE);
        } catch (Exception ex) {
            Assert.fail(ex.getMessage());
        }
    }
}
