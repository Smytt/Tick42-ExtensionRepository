package com.tick42.quicksilver.services.base;

public interface RatingService {

    int rate(int extensionId, int rating, int userId);

    int userRatingForExtension(int extensionId, int userId);

}
