package com.sharegoods.inth3rship.dto;

import com.sharegoods.inth3rship.models.Image;
import com.sharegoods.inth3rship.models.Item;

import java.util.*;

public class ItemThumbnailsDto {

    private Long itemId;
    private String title;
    private ImageDto thumbnailDto;
    private Double rating;
    private Long itemUserId;
    private String itemUserName;

    public ItemThumbnailsDto(Long itemId, String title, Long itemUserId, String itemUserFirstName, String itemUserLastName, ImageDto thumbnailDto, Double rating) {
        this.itemId = itemId;
        this.title = title;
        this.thumbnailDto = thumbnailDto;
        this.rating = rating;
        this.itemUserId = itemUserId;
        this.itemUserName = itemUserFirstName + " " + itemUserLastName;
    }

    public static List<ItemThumbnailsDto> getItemThumbnailsDtoList (Map<Item, Image> itemHashMap) {
        List<ItemThumbnailsDto> itemThumbnailsDtoList = new ArrayList<>();
        for (Map.Entry<Item, Image> entry : itemHashMap.entrySet()) {
            Item item = entry.getKey();
            Image thumbnail = entry.getValue();
            ImageDto thumbnailDto = new ImageDto(thumbnail);
            Double itemRating = item.getRating();
            if (itemRating == null) {
                itemRating = 0.0;
            }
            ItemThumbnailsDto itemThumbnailsDto = new ItemThumbnailsDto(item.getId(), item.getTitle(), item.getUser().getId(), item.getUser().getFirstName(), item.getUser().getLastName(), thumbnailDto, itemRating);
            itemThumbnailsDtoList.add(itemThumbnailsDto);
        }

        return itemThumbnailsDtoList;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ImageDto getThumbnailDto() {
        return thumbnailDto;
    }

    public void setThumbnailDto(ImageDto thumbnailDto) {
        this.thumbnailDto = thumbnailDto;
    }

    public Double getRating() { return rating; }

    public void setRating(Double rating) { this.rating = rating; }

    public Long getItemUserId() {
        return itemUserId;
    }

    public void setItemUserId(Long itemUserId) {
        this.itemUserId = itemUserId;
    }

    public String getItemUserName() {
        return itemUserName;
    }

    public void setItemUserName(String itemUserName) {
        this.itemUserName = itemUserName;
    }
}
