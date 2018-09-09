package com.tick42.quicksilver.services;

import com.tick42.quicksilver.exceptions.*;
import com.tick42.quicksilver.models.DTO.ExtensionDTO;
import com.tick42.quicksilver.models.DTO.PageDTO;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

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

        Set<Tag> tags = new HashSet<>(Arrays.asList(new Tag("tag1"), new Tag("tag2")));
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
        createdExtensionDTO.getTags().sort(String::compareTo);

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

        Set<Tag> tags = new HashSet<>(Arrays.asList(new Tag("tag1"), new Tag("tag2")));
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
        ExtensionDTO actualExtensionDTO = extensionService.update(1, extensionSpec, 1);

        //Assert
        Assert.assertEquals(actualExtensionDTO.getName(), "newName");
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

        Set<Tag> tags = new HashSet<>(Arrays.asList(new Tag("tag1"), new Tag("tag2")));
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
        ExtensionDTO actualExtensionDTO = extensionService.update(1, extensionSpec, 1);

        //Assert
        Assert.assertEquals(actualExtensionDTO.getName(), "newName");
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
        } catch (Exception e) {
            Assert.fail(e.getMessage());
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

    @Test(expected = InvalidParameterException.class)
    public void findAll_whenPageLessThan1_shouldThrow() {
        //Arrange
        int page = -1;

        //Act
        extensionService.findAll("name", "date", page, 5);
    }

    @Test(expected = InvalidParameterException.class)
    public void findAll_whenPerPageLessThan1_shouldThrow() {
        //Arrange
        int perPage = -1;

        //Act
        extensionService.findAll("name", "date", 1, perPage);
    }

    @Test(expected = InvalidParameterException.class)
    public void findAll_whenPageMoreThanTotalPages_shouldThrow() {
        //Arrange
        String name = "name";
        int page = 5;
        int perPage = 10;
        Long totalResults = 21L;

        when(extensionRepository.getTotalResults(name)).thenReturn(totalResults);

        //Act
        extensionService.findAll(name, "date", page, perPage);
    }

    @Test
    public void findAll_whenPageMoreThanTotalPagesAndTotalResultsAreZero_shouldNotThrow() {
        //Arrange
        String name = "name";
        int page = 5;
        int perPage = 10;
        Long totalResults = 0L;

        when(extensionRepository.getTotalResults(name)).thenReturn(totalResults);

        //Act
        try {
            extensionService.findAll(name, "date", page, perPage);
            Assert.assertTrue(Boolean.TRUE);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test(expected = InvalidParameterException.class)
    public void findAll_whenInvalidParameter_shouldThrow() {
        //Arrange
        String name = "name";
        String orderBy = "orderType";
        int page = 5;
        int perPage = 10;
        Long totalResults = 500L;

        when(extensionRepository.getTotalResults(name)).thenReturn(totalResults);

        //Act
        extensionService.findAll(name, orderBy, page, perPage);
    }

    @Test
    public void findAll_whenNameIsNotNull_returnAll() {
        //Arrange
        String name = "SearchedName";

        Extension extension1 = new Extension();
        Extension extension2 = new Extension();
        extension1.setName("Contains SearchedName");
        extension2.setName("Contains SearchedName As Well");
        List<Extension> extensionResults = Arrays.asList(extension1, extension2);

        when(extensionRepository.findAllByDate(name, 1, 1)).thenReturn(extensionResults);

        //Act
        PageDTO<ExtensionDTO> actualPageDTO = extensionService.findAll(name, null, 1, 1);

        //Assert
        actualPageDTO.getExtensions().forEach(extension -> {
            Assert.assertTrue(extension.getName().contains(name));
        });
    }

    @Test
    public void findAll_whenOrderedByName_returnAllOrderedByName() {
        //Arrange
        String orderBy = "name";

        Extension extension1 = new Extension();
        Extension extension2 = new Extension();
        Extension extension3 = new Extension();
        extension1.setName("aa");
        extension2.setName("bb");
        extension3.setName("cc");
        List<Extension> extensionResults = Arrays.asList(extension1, extension2, extension3);
        when(extensionRepository.findAllByName("", 1, 1)).thenReturn(extensionResults);

        ExtensionDTO extensionDTO1 = new ExtensionDTO();
        ExtensionDTO extensionDTO2 = new ExtensionDTO();
        ExtensionDTO extensionDTO3 = new ExtensionDTO();
        extensionDTO1.setName("cc");
        extensionDTO2.setName("aa");
        extensionDTO3.setName("bb");
        List<ExtensionDTO> expectedDTOs = Arrays.asList(extensionDTO1, extensionDTO2, extensionDTO3);
        expectedDTOs.sort(Comparator.comparing(ExtensionDTO::getName));

        PageDTO<ExtensionDTO> expectededPageDTO = new PageDTO<>();
        expectededPageDTO.setExtensions(expectedDTOs);

        //Act
        PageDTO<ExtensionDTO> actualPageDTO = extensionService.findAll("", orderBy, 1, 1);

        //Assert
        Assert.assertEquals(expectededPageDTO.getExtensions().size(), actualPageDTO.getExtensions().size());
        for (int i = 0; i < expectededPageDTO.getExtensions().size(); i++) {
            Assert.assertEquals(expectededPageDTO.getExtensions().get(i).getName(),
                    actualPageDTO.getExtensions().get(i).getName());
        }
    }

    @Test
    public void findAll_whenOrderedByDate_returnAllOrderedByName() throws ParseException {
        //Arrange
        String orderBy = "date";

        Extension extension1 = new Extension();
        Extension extension2 = new Extension();
        Extension extension3 = new Extension();
        extension1.setUploadDate(new SimpleDateFormat("yyyy-MM-dd").parse("2015-05-10"));
        extension2.setUploadDate(new SimpleDateFormat("yyyy-MM-dd").parse("2013-03-10"));
        extension3.setUploadDate(new SimpleDateFormat("yyyy-MM-dd").parse("2011-01-10"));
        List<Extension> extensionResults = Arrays.asList(extension1, extension2, extension3);
        when(extensionRepository.findAllByDate("", 1, 1)).thenReturn(extensionResults);

        ExtensionDTO extensionDTO1 = new ExtensionDTO();
        ExtensionDTO extensionDTO2 = new ExtensionDTO();
        ExtensionDTO extensionDTO3 = new ExtensionDTO();
        extensionDTO1.setUploadDate(new SimpleDateFormat("yyyy-MM-dd").parse("2011-01-10"));
        extensionDTO2.setUploadDate(new SimpleDateFormat("yyyy-MM-dd").parse("2015-05-10"));
        extensionDTO3.setUploadDate(new SimpleDateFormat("yyyy-MM-dd").parse("2013-03-10"));
        List<ExtensionDTO> expectedDTOs = Arrays.asList(extensionDTO1, extensionDTO2, extensionDTO3);
        expectedDTOs.sort((a, b) -> b.getUploadDate().compareTo(a.getUploadDate()));

        PageDTO<ExtensionDTO> expectededPageDTO = new PageDTO<>();
        expectededPageDTO.setExtensions(expectedDTOs);

        //Act
        PageDTO<ExtensionDTO> actualPageDTO = extensionService.findAll("", orderBy, 1, 1);

        //Assert
        Assert.assertEquals(expectededPageDTO.getExtensions().size(), actualPageDTO.getExtensions().size());
        for (int i = 0; i < expectededPageDTO.getExtensions().size(); i++) {
            Assert.assertEquals(expectededPageDTO.getExtensions().get(i).getUploadDate(),
                    actualPageDTO.getExtensions().get(i).getUploadDate());
        }
    }

    @Test
    public void findAll_whenOrderedByDate_returnAllOrderedByLastCommit() throws ParseException {
        //Arrange
        String orderBy = "commits";

        Extension extension1 = new Extension();
        Extension extension2 = new Extension();
        Extension extension3 = new Extension();
        extension1.setGithub(new GitHubModel());
        extension2.setGithub(new GitHubModel());
        extension3.setGithub(new GitHubModel());
        extension1.getGithub().setLastCommit(new SimpleDateFormat("yyyy-MM-dd").parse("2015-05-10"));
        extension2.getGithub().setLastCommit(new SimpleDateFormat("yyyy-MM-dd").parse("2013-03-10"));
        extension3.getGithub().setLastCommit(new SimpleDateFormat("yyyy-MM-dd").parse("2011-01-10"));
        List<Extension> extensionResults = Arrays.asList(extension1, extension2, extension3);
        when(extensionRepository.findAllByCommit("", 1, 1)).thenReturn(extensionResults);

        ExtensionDTO extensionDTO1 = new ExtensionDTO();
        ExtensionDTO extensionDTO2 = new ExtensionDTO();
        ExtensionDTO extensionDTO3 = new ExtensionDTO();
        extensionDTO1.setLastCommit(new SimpleDateFormat("yyyy-MM-dd").parse("2011-01-10"));
        extensionDTO2.setLastCommit(new SimpleDateFormat("yyyy-MM-dd").parse("2015-05-10"));
        extensionDTO3.setLastCommit(new SimpleDateFormat("yyyy-MM-dd").parse("2013-03-10"));
        List<ExtensionDTO> expectedDTOs = Arrays.asList(extensionDTO1, extensionDTO2, extensionDTO3);
        expectedDTOs.sort((a, b) -> b.getLastCommit().compareTo(a.getLastCommit()));

        PageDTO<ExtensionDTO> expectededPageDTO = new PageDTO<>();
        expectededPageDTO.setExtensions(expectedDTOs);

        //Act
        PageDTO<ExtensionDTO> actualPageDTO = extensionService.findAll("", orderBy, 1, 1);

        //Assert
        Assert.assertEquals(expectededPageDTO.getExtensions().size(), actualPageDTO.getExtensions().size());
        for (int i = 0; i < expectededPageDTO.getExtensions().size(); i++) {
            Assert.assertEquals(expectededPageDTO.getExtensions().get(i).getLastCommit(),
                    actualPageDTO.getExtensions().get(i).getLastCommit());
        }
    }

    @Test
    public void findAll_whenOrderedByDate_returnAllOrderedByDownloads() {
        //Arrange
        String orderBy = "downloads";

        Extension extension1 = new Extension();
        Extension extension2 = new Extension();
        Extension extension3 = new Extension();
        extension1.setTimesDownloaded(5);
        extension2.setTimesDownloaded(3);
        extension3.setTimesDownloaded(1);
        List<Extension> extensionResults = Arrays.asList(extension1, extension2, extension3);
        when(extensionRepository.findAllByDownloads("", 1, 1)).thenReturn(extensionResults);

        ExtensionDTO extensionDTO1 = new ExtensionDTO();
        ExtensionDTO extensionDTO2 = new ExtensionDTO();
        ExtensionDTO extensionDTO3 = new ExtensionDTO();
        extensionDTO1.setTimesDownloaded(5);
        extensionDTO2.setTimesDownloaded(3);
        extensionDTO3.setTimesDownloaded(1);
        List<ExtensionDTO> expectedDTOs = Arrays.asList(extensionDTO1, extensionDTO2, extensionDTO3);
        expectedDTOs.sort((a, b) -> b.getTimesDownloaded() - a.getTimesDownloaded());

        PageDTO<ExtensionDTO> expectededPageDTO = new PageDTO<>();
        expectededPageDTO.setExtensions(expectedDTOs);

        //Act
        PageDTO<ExtensionDTO> actualPageDTO = extensionService.findAll("", orderBy, 1, 1);

        //Assert
        Assert.assertEquals(expectededPageDTO.getExtensions().size(), actualPageDTO.getExtensions().size());
        for (int i = 0; i < expectededPageDTO.getExtensions().size(); i++) {
            Assert.assertEquals(expectededPageDTO.getExtensions().get(i).getTimesDownloaded(),
                    actualPageDTO.getExtensions().get(i).getTimesDownloaded());
        }
    }

    @Test
    public void delete_whenExtensionsFound_shouldInvokeDeleteInRepository() {
        //Arrange
        User user = new User();
        Extension extension = new Extension();
        extension.setOwner(user);

        when(extensionRepository.findById(1)).thenReturn(extension);
        when(userRepository.findById(1)).thenReturn(user);
        doNothing().when(extensionRepository).delete(isA(Extension.class));

        extensionService.delete(1, 1);
        verify(extensionRepository, times(1)).delete(extension);
    }

    @Test(expected = ExtensionUnavailableException.class)
    public void increaseDownloadCount_whenExtensionDoesntExist_shouldThrow() {
        when(extensionRepository.findById(1)).thenReturn(null);

        //Act
        extensionService.increaseDownloadCount(1);
    }

    @Test(expected = ExtensionUnavailableException.class)
    public void increaseDownloadCount_whenExtensionIsPending_shouldThrow() {
        Extension extension = new Extension();
        extension.setIsPending(true);

        when(extensionRepository.findById(1)).thenReturn(extension);

        //Act
        extensionService.increaseDownloadCount(1);
    }

    @Test(expected = ExtensionUnavailableException.class)
    public void increaseDownloadCount_whenOwnerIsDeactivated_shouldThrow() {
        User owner = new User();
        owner.setIsActive(false);
        Extension extension = new Extension();
        extension.setOwner(owner);
        extension.setIsPending(false);

        when(extensionRepository.findById(1)).thenReturn(extension);

        //Act
        extensionService.increaseDownloadCount(1);
    }

    @Test
    public void increaseDownloadCount_whenExtensionAvailable_shouldIncreasseTimesDownlaoded() {
        int times = 1;
        User owner = new User();
        owner.setIsActive(true);
        Extension extension = new Extension();
        extension.setOwner(owner);
        extension.setIsPending(false);
        extension.setTimesDownloaded(times);

        when(extensionRepository.findById(1)).thenReturn(extension);

        when(extensionRepository.update(extension)).thenReturn(extension);

        //Act
        ExtensionDTO result = extensionService.increaseDownloadCount(1);

        //Assert
        Assert.assertEquals(result.getTimesDownloaded(), times + 1);
    }

    @Test(expected = ExtensionNotFoundException.class)
    public void fetchGitHub_whenExtensionDoesntExist_ShouldThrow() {
        //Arrange
        when(extensionRepository.findById(1)).thenReturn(null);

        //Act
        extensionService.fetchGitHub(1, 1);

    }

    @Test(expected = UserNotFoundException.class)
    public void fetchGitHub_whenUserDoesntExist_ShouldThrow() {
        //Arrange
        Extension extension = new Extension();
        when(extensionRepository.findById(1)).thenReturn(extension);
        when(userRepository.findById(1)).thenReturn(null);

        //Act
        extensionService.fetchGitHub(1, 1);

    }

    @Test(expected = UnauthorizedExtensionModificationException.class)
    public void fetchGitHub_whenUserIsNotAdmin_ShouldThrow() {
        //Arrange
        Extension extension = new Extension();
        User user = new User();
        user.setRole("USER");

        when(extensionRepository.findById(1)).thenReturn(extension);
        when(userRepository.findById(1)).thenReturn(user);

        //Act
        extensionService.fetchGitHub(1, 1);

    }

    @Test
    public void fetchGitHub_whenEtensionIsAvailableAndUserIsAdmin_ShouldNotThrow() {
        //Arrange
        Extension extension = new Extension();
        User user = new User();
        user.setRole("ROLE_ADMIN");

        when(extensionRepository.findById(1)).thenReturn(extension);
        when(userRepository.findById(1)).thenReturn(user);

        try {
            extensionService.fetchGitHub(1, 1);
            Assert.assertTrue(Boolean.TRUE);
        } catch (Error e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void generateExtensionDTOList_whenGivenListOfExtension_returnListOfExtensionDTO() {
        //Arrange
        Extension extension1 = new Extension();
        Extension extension2 = new Extension();
        List<Extension> extensions = Arrays.asList(extension1, extension2);

        //Act
        List<ExtensionDTO> extensionDTOs = extensionService.generateExtensionDTOList(extensions);

        //Assert
        Assert.assertEquals(extensions.size(), extensionDTOs.size());
        Assert.assertTrue(extensionDTOs.get(1) instanceof ExtensionDTO);
    }
}
