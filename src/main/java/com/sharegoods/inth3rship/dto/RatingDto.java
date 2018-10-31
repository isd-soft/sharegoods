package com.sharegoods.inth3rship.dto;

import com.sharegoods.inth3rship.models.Rating;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;



public class RatingDto {
    Long id;
    Long userId;
    Long itemId;
    Double rating;

    public RatingDto (Rating rating) {
        this.id = rating.getId();
        this.userId = rating.getUser().getId();
        this.itemId = rating.getItem().getId();
        this.rating = rating.getRating();

    }


    public static List<RatingDto> getRatingDtoList(List<Rating> ratingList) {
        List<RatingDto> ratingDtoList = new ArrayList<>();
        ListIterator<Rating> ratingListIterator = ratingList.listIterator();
        while(ratingListIterator.hasNext()) {
            RatingDto ratingDto = new RatingDto(ratingListIterator.next());
            ratingDtoList.add(ratingDto);
        }
        return ratingDtoList;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

}
