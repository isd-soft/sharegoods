package com.sharegoods.inth3rship.dto;

import com.sharegoods.inth3rship.models.Item;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class ItemDto {

    Long id;
    Long userId;
    String title;
    String description;
    Timestamp dateTime;
    String author;

    public ItemDto(Item item) {
        this.id = item.getId();
        this.title = item.getTitle();
        this.description = item.getDescription();
        this.userId = item.getUser().getId();
        this.dateTime = item.getDateTime();
        this.author = item.getUser().getFirstName() + ' ' +  item.getUser().getLastName();
    }

        public static List<ItemDto> getItemDtoList(List<Item> itemList) {
        List<ItemDto> itemDtoList = new ArrayList<>();
        ListIterator<Item> itemsIterator = itemList.listIterator();
        while (itemsIterator.hasNext()) {
            ItemDto itemDto = new ItemDto(itemsIterator.next());
            itemDtoList.add(itemDto);
        }
        return itemDtoList;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

}