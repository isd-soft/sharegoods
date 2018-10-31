package com.sharegoods.inth3rship.services;

import com.sharegoods.inth3rship.exceptions.ItemNotFoundException;
import com.sharegoods.inth3rship.exceptions.RatingException;
import com.sharegoods.inth3rship.exceptions.UserNotFoundException;
import com.sharegoods.inth3rship.exceptions.VoteTwiceException;
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

    public List<Rating> getRating(Item item) {
        return ratingRepository.findByItem(item);
    }

    public Boolean validationRating(Long userId, Long itemId) {
        User user = userService.getUserById(userId);
        Item item = itemService.getItemById(itemId);
        List<Rating> arrayList = ratingRepository.findByUserIdAndItemId(user.getId(), item.getId());
        return arrayList.isEmpty();
    }

    public Rating createRating(Long userId, Long itemId, Double rating) throws UserNotFoundException, ItemNotFoundException, RatingException, VoteTwiceException {

        if (!validationRating(userId, itemId)) {
            throw new VoteTwiceException("You try to vote second time, Snicky bastard");
        }

        if (rating <= 5 && rating > 0) {
            User user;
            Item item;

            try {
                user = userService.getUserById(userId);
            } catch (NoSuchElementException e) {
                throw new UserNotFoundException("No such user");
            }
            try {
                item = itemService.getItemById(itemId);
            } catch (NoSuchElementException e) {
                throw new ItemNotFoundException("No such item");
            }
            Rating rate = new Rating(rating, user, item);
            return ratingRepository.save(rate);

        } else {
            throw new RatingException("Rating is higher than u think");
        }
    }
}



