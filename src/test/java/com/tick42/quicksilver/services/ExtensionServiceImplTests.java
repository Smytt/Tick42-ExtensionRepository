package com.tick42.quicksilver.services;

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
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.when;

@SpringBootTest
public class ExtensionServiceImplTests {
    @Mock
    ExtensionRepository extensionRepository;
    @Mock
    TagService tagService;
    @Mock
    GitHubService gitHubService;
    @Mock
    JwtValidator jwtValidator;
    @Mock
    UserRepository userRepository;

    private ExtensionService extensionService;

    @Before
    public void generateExtensionService() {
        extensionService = new ExtensionServiceImpl(extensionRepository, tagService, gitHubService, jwtValidator, userRepository);
    }


    @Test
    public void setFeaturedState_whenSetToFeatured_returnFeaturedExtensionDTO() {
        // Arrange
        Extension extensionBeforeUpdate = new Extension();
        extensionBeforeUpdate.setIsPending(false);

        when(extensionRepository.findById(1)).thenReturn(extensionBeforeUpdate);
        when(extensionRepository.update(extensionBeforeUpdate)).thenReturn(extensionBeforeUpdate);

        //Act
        ExtensionDTO extensionAfterUpdate = extensionService.setFeaturedState(1, "feature");

        //Assert
        Assert.assertTrue(extensionAfterUpdate.isFeatured());
    }

    @Test
    public void test() {
        Assert.assertTrue(true);
    }
}
