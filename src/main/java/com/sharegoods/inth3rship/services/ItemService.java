package com.sharegoods.inth3rship.services;

import com.sharegoods.inth3rship.models.Image;
import com.sharegoods.inth3rship.models.Item;
import com.sharegoods.inth3rship.models.User;
import com.sharegoods.inth3rship.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.*;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    public List<Item> getItemsByUserId(Long id) {
        User user = userService.getUserById(id);
        return itemRepository.findByUser(user);
    }

    public List<Item> getItems() {
        return itemRepository.findAll();
    }

    public Item createNewItem(Long userId, String title, String description, List<MultipartFile> imageFiles) {
        User user = userService.getUserById(userId);
        java.util.Date dateNow = new java.util.Date();
        Timestamp date = new Timestamp(dateNow.getTime());
        Item newItem = new Item(user, date, title, description);
        itemRepository.save(newItem);
        imageService.createImages(newItem, imageFiles);
        return newItem;
    }

    public Item getItemById(Long id) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        return optionalItem.get();
    }

    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }

    public Item updateItem(Long itemId, String title, String description, List<MultipartFile> imageFiles) {
        Item item = getItemById(itemId);
        item.setTitle(title);
        item.setDescription(description);
        imageService.updateItemImages(item, imageFiles);
        return itemRepository.save(item);
    }

    public Map<Item, Image> getItemsWithThumbnails(List<Item> items) {
        Map<Item, Image> itemsWithThumbnails = new HashMap<>();
        ListIterator<Item> itemsIterator = items.listIterator();
        while (itemsIterator.hasNext()) {
            Item currentItem = itemsIterator.next();
            Image thumbnail = imageService.getThumbnail(currentItem);
            itemsWithThumbnails.put(currentItem, thumbnail);
        }
        return itemsWithThumbnails;
    }
}