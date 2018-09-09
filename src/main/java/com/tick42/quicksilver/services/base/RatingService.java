package com.tick42.quicksilver.services.base;

import com.tick42.quicksilver.models.Extension;
import com.tick42.quicksilver.models.Rating;

public interface RatingService {

    double rate(int extensionId, int rating, int userId);

    int userRatingForExtension(int extensionId, int userId);

    Extension newExtensionRating(double userRatingForExtension, Rating newRating, Extension extension);

    void userRatingOnExtensionDelete(int userExtension);
}
