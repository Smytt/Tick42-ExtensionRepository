package com.tick42.quicksilver.services;

import com.tick42.quicksilver.exceptions.ExtensionNotFoundException;
import com.tick42.quicksilver.exceptions.InvalidRatingException;
import com.tick42.quicksilver.models.Extension;
import com.tick42.quicksilver.models.Rating;
import com.tick42.quicksilver.models.User;
import com.tick42.quicksilver.repositories.base.ExtensionRepository;
import com.tick42.quicksilver.repositories.base.RatingRepository;
import com.tick42.quicksilver.repositories.base.UserRepository;
import com.tick42.quicksilver.services.base.RatingService;
import org.springframework.stereotype.Service;

@Service
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final ExtensionRepository extensionRepository;
    private final UserRepository userRepository;

    public RatingServiceImpl(RatingRepository ratingRepository, ExtensionRepository extensionRepository, UserRepository userRepository) {
        this.ratingRepository = ratingRepository;
        this.extensionRepository = extensionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public double rate(int extensionId, int rating, int userId) {
        if (rating > 5) {
            throw new InvalidRatingException("Rating must be between 1 and 5");
        }
        Rating newRating = new Rating(rating, extensionId, userId);
        double currentRating = ratingRepository.findExtensionRatingByUser(extensionId, userId);
        Extension extension = extensionRepository.findById(extensionId);
        double currentExtensionRating = extension.getRating();

        if (extension == null) {
            throw new ExtensionNotFoundException("Extension not found");
        }

        newExtensionRating(currentRating, currentExtensionRating, newRating, extension, rating);
        newUserRating(currentExtensionRating, extension, rating);

        return extension.getRating();
    }

    @Override
    public int userRatingForExtension(int extensionId, int userId) {
        return ratingRepository.findExtensionRatingByUser(extensionId, userId);
    }

    private void newUserRating(double currentExtensionRating, Extension extension, int rating) {
        User user = extension.getOwner();
        double userRating = user.getRating();
        int extensionsRated = user.getExtensionsRated();

        if (currentExtensionRating == 0) {
            user.setRating((userRating * extensionsRated + rating) / (extensionsRated + 1));
            user.setExtensionsRated(user.getExtensionsRated() + 1);
            userRepository.update(user);
        } else {
            user.setRating(((userRating * extensionsRated - currentExtensionRating) + extension.getRating()) / extensionsRated);
            userRepository.update(user);
        }
    }

    private void newExtensionRating(double currentRating, double currentExtensionRating, Rating newRating, Extension extension, int rating){
        if (currentRating == 0) {
            ratingRepository.rate(newRating);
            extension.setRating((currentExtensionRating * extension.getTimesRated() + rating) / (extension.getTimesRated() + 1));
            extension.setTimesRated(extension.getTimesRated() + 1);
            extensionRepository.update(extension);
        } else {
            extension.setRating(((currentExtensionRating * extension.getTimesRated() - currentRating) + rating) / (extension.getTimesRated()));
            extensionRepository.update(extension);
            ratingRepository.updateRating(newRating);
        }

    }
    @Override
    public void userRatingOnExtensionExtensionDelete(int userExtension){
        Extension extension = extensionRepository.findById(userExtension);
        double extensionRating = extension.getRating();
        User user = extension.getOwner();
        if (extensionRating > 0){
            double userRating = user.getRating();
            int userRatedExtensions = user.getExtensionsRated();
            user.setRating((userRating * userRatedExtensions - extensionRating)/(userRatedExtensions -1));
            user.setExtensionsRated(userRatedExtensions -1);
            userRepository.update(user);
        }

    }
}
