package com.tick42.quicksilver.services;

import com.tick42.quicksilver.exceptions.ExtensionNotFoundException;
import com.tick42.quicksilver.exceptions.InvalidRatingException;
import com.tick42.quicksilver.exceptions.InvalidStateException;
import com.tick42.quicksilver.exceptions.UserNotFoundException;
import com.tick42.quicksilver.models.Extension;
import com.tick42.quicksilver.models.Rating;
import com.tick42.quicksilver.models.User;
import com.tick42.quicksilver.repositories.base.ExtensionRepository;
import com.tick42.quicksilver.repositories.base.RatingRepository;
import com.tick42.quicksilver.repositories.base.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RatingServiceImplTests {

    @Mock
    private ExtensionRepository extensionRepository;

    @Mock
    private RatingRepository ratingRepository;

    @InjectMocks
    private RatingServiceImpl ratingService;

    @Test(expected = InvalidRatingException.class)
    public void rateExtension_withInvalidInput_ShouldThrow() {
        //Arrange
        int extensionId = 1;
        int rating = 40;
        int userId = 5;

        //Act
        ratingService.rate(extensionId, rating, userId);
    }

    public void rateExtension__ShouldThrow() {
        //Arrange
        int extensionId = 1;
        int rating = 4;
        int userId = 5;

        when(extensionRepository.findById(1)).thenReturn(null);
        //Act
        ratingService.rate(extensionId, rating, userId);
    }

    @Test()
    public void rateExtension_WhenUserHasCurrentRattingForExtension_ShouldReturnChanged() {
        //Arrange
        Extension extension = new Extension();
        extension.setRating(2);
        extension.setTimesRated(2);
        int currentUserRatingForExtension = 2;
        Rating newRating = new Rating(3, 1, 1);

        //Act
        extension =ratingService.newExtensionRating( currentUserRatingForExtension, newRating, extension);
        //Assert
        Assert.assertEquals(2.50, extension.getRating(), 0);
    }

    @Test
    public void rateExtension_WhenUserDoesntHaveCurrentRattingForExtension_ShouldReturnChanged() {
        //Arrange
        Extension extension = new Extension();
        extension.setRating(2);
        extension.setTimesRated(2);
        int currentUserRatingForExtension = 0;
        Rating newRating = new Rating(5, 1, 1);

        //Act
        extension =ratingService.newExtensionRating( currentUserRatingForExtension, newRating, extension);

        //Assert
        Assert.assertEquals(3, extension.getRating(), 0);
    }
}
