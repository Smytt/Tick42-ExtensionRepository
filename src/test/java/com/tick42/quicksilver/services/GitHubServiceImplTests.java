package com.tick42.quicksilver.services;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.tick42.quicksilver.exceptions.GitHubRepositoryException;
import com.tick42.quicksilver.exceptions.InvalidParameterException;
import com.tick42.quicksilver.models.GitHubModel;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kohsuke.github.GHFileNotFoundException;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GitHubServiceImplTests {

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
    public void setRemoteDetails_whenGitHubModelIsValid_shouldSetDetails() {
        //Arrange
        GitHubModel gitHubModel = new GitHubModel();
        gitHubModel.setUser("Smytt");
        gitHubModel.setRepo("Tick42-ExtensionRepository");

        //Act
        gitHubService.setRemoteDetails(gitHubModel);

        //Asser
        Assert.assertNotNull(gitHubModel.getLastSuccess());
        Assert.assertNotNull(gitHubModel.getLastCommit());
    }
}
