package com.tick42.quicksilver.services;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.tick42.quicksilver.exceptions.GitHubRepositoryException;
import com.tick42.quicksilver.exceptions.InvalidParameterException;
import com.tick42.quicksilver.models.Extension;
import com.tick42.quicksilver.models.GitHubModel;
import com.tick42.quicksilver.repositories.base.GitHubRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kohsuke.github.GHFileNotFoundException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GitHubServiceImplTests {

    @Mock
    GitHubRepository gitHubRepository;

    @InjectMocks
    private GitHubServiceImpl gitHubService;

    @Test
    public void setRemoteDetails_whenGitHubModelIsValid_shouldNotThrow() {
        //Arrange
        GitHubModel gitHubModel = new GitHubModel();
        gitHubModel.setUser("Smytt");
        gitHubModel.setRepo("Tick42-ExtensionRepository");

        //Act
        try {
            gitHubService.setRemoteDetails(gitHubModel);
            //Assert
            Assert.assertTrue(Boolean.TRUE);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }

    }

    @Test
    public void setRemoteDetails_whenGitHubModelIsInvalid_shouldSetFailMsgAndDate() {
        //Arrange
        GitHubModel gitHubModel = new GitHubModel();
        gitHubModel.setUser("some");
        gitHubModel.setRepo("other");

        //Act
        gitHubService.setRemoteDetails(gitHubModel);

        //Asser
        Assert.assertNotNull(gitHubModel.getFailMessage());
        Assert.assertNotNull(gitHubModel.getLastFail());
    }

    @Test
    public void generateGitHub_whenLinkCorrect_returnGitHubModel() {
        //Arrange
        String link = "https://github.com/Smytt/Tick42-ExtensionRepository";

        //Act
        GitHubModel gitHubModel = gitHubService.generateGitHub(link);

        //Assert
        Assert.assertEquals(gitHubModel.getUser(), "Smytt");
        Assert.assertEquals(gitHubModel.getRepo(), "Tick42-ExtensionRepository");
    }

    @Test
    public void updateExtensionDetails_whenListProvided_shouldUpdateResults() {
        //Arrange
        GitHubModel gitHubModel1 = new GitHubModel();
        GitHubModel gitHubModel2 = new GitHubModel();
        gitHubModel1.setUser("Smytt");
        gitHubModel1.setRepo("Tick42-ExtensionRepository");
        gitHubModel2.setUser("wrong");
        gitHubModel2.setRepo("wrong");
        List<GitHubModel> gitHubModels = Arrays.asList(gitHubModel1, gitHubModel2);

        when(gitHubRepository.findAll()).thenReturn(gitHubModels);

        //Act
        gitHubService.updateExtensionDetails();

        //Assert
        verify(gitHubRepository, times(2)).update(isA(GitHubModel.class));
    }
}
