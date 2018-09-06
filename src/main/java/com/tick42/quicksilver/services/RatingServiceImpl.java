package com.tick42.quicksilver.services;

import com.tick42.quicksilver.exceptions.ExtensionNotFoundException;
import com.tick42.quicksilver.exceptions.InvalidRatingException;
import com.tick42.quicksilver.models.Extension;
import com.tick42.quicksilver.models.Rating;
import com.tick42.quicksilver.repositories.base.ExtensionRepository;
import com.tick42.quicksilver.repositories.base.RatingRepository;
import com.tick42.quicksilver.services.base.RatingService;
import org.springframework.stereotype.Service;

@Service
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final ExtensionRepository extensionRepository;

    public RatingServiceImpl(RatingRepository ratingRepository, ExtensionRepository extensionRepository) {
        this.ratingRepository = ratingRepository;
        this.extensionRepository = extensionRepository;
    }

    @Override
    public int rate(int extensionId, int rating, int userId) {
        if (rating > 5) {
            throw new InvalidRatingException("Rating must be between 1 and 5");
        }
        Rating newRating = new Rating(rating, extensionId, userId);
        int currentRating = ratingRepository.findExtensionRatingByUser(extensionId, userId);
        Extension extension = extensionRepository.findById(extensionId);
        if (extension == null) {
            throw new ExtensionNotFoundException("Extension not found");
        }
        if (currentRating == 0) {
            ratingRepository.rate(newRating);
            extension.setRating((extension.getRating() * extension.getTimesRated() + rating) / (extension.getTimesRated() + 1));
            extension.setTimesRated(extension.getTimesRated() + 1);
            extensionRepository.update(extension);
        } else {
            extension.setRating(((extension.getRating() * extension.getTimesRated() - currentRating) + rating) / (extension.getTimesRated()));
            extensionRepository.update(extension);
            ratingRepository.updateRating(newRating);
            currentRating = rating;
        }
        return currentRating;
    }

    @Override
    public int userRatingForExtension(int extensionId, int userId) {

        return ratingRepository.findExtensionRatingByUser(extensionId, userId);
    }
}
