package com.tick42.quicksilver.repositories.base;

import com.tick42.quicksilver.models.Rating;

public interface RatingRepository {

    void rate(Rating rate);

    void updateRating(Rating rate);

    int findExtensionRatingByUser(int extensionId, int userId);

}
