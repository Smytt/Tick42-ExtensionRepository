package com.tick42.quicksilver.services.base;

import com.tick42.quicksilver.models.Extension;
import com.tick42.quicksilver.models.Rating;
import com.tick42.quicksilver.models.User;

public interface RatingService {

    double rate(int extensionId, int rating, int userId);

    int userRatingForExtension(int extensionId, int userId);

    User newUserRating(double currentExtensionRating, Extension extension, int rating);

    Extension newExtensionRating(double userRatingForExtension, Rating newRating, Extension extension);

    User userRatingOnExtensionDelete(int userExtension);
}
