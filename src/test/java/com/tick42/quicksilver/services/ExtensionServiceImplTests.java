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
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExtensionServiceImplTests {
    @Mock
    private ExtensionRepository extensionRepository;
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
//        extensionBeforeUpdate.setId(1);
        extensionBeforeUpdate.setIsFeatured(false);

        when(extensionRepository.findById(100)).thenReturn(extensionBeforeUpdate);

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
