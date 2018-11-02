package com.sharegoods.inth3rship.services;

import com.sharegoods.inth3rship.exceptions.*;
import com.sharegoods.inth3rship.models.Item;
import com.sharegoods.inth3rship.models.Rating;
import com.sharegoods.inth3rship.models.User;
import com.sharegoods.inth3rship.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    public List<Rating> getRatingByItemAndUser(Long itemId, Long userId) throws ItemNotFoundException, UserNotFoundException {
        try {
            Item item = itemService.getItemById(itemId);
        } catch (NoSuchElementException e) {
            throw new ItemNotFoundException("This itemId is not found in DB " + itemId);
        }
        try {
            User user = userService.getUserById(userId);
        } catch (NoSuchElementException e) {
            throw new UserNotFoundException("This userId is not found in DB " + userId);
        }
        List<Rating> ratingList = ratingRepository.findByUserIdAndItemId(userId, itemId);

        return ratingList;
    }

    public Rating createRating(Long userId, Long itemId, Double rating) throws UserNotFoundException, ItemNotFoundException, RatingValidationException, VoteTwiceException {

        if (!getRatingByItemAndUser(itemId, userId).isEmpty()) {
            throw new VoteTwiceException("You try to vote second time, Snicky bastard");
        }

        if (rating <= 5 && rating > 0) {
            User user = userService.getUserById(userId);
            Item item = itemService.getItemById(itemId);
            Rating rate = new Rating(rating, user, item);
            return ratingRepository.save(rate);
        } else {
            throw new RatingValidationException("Rating is higher than u think");
        }
    }
}



