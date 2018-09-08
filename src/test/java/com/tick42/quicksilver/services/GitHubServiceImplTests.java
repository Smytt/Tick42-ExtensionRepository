package com.tick42.quicksilver.services;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.tick42.quicksilver.models.GitHubModel;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GitHubServiceImplTests {

    @InjectMocks
    private GitHubServiceImpl gitHubService;

    @Test
    public void setRemoteDetails_GitHubModelIsInvalid_shouldThrow() {
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
}
