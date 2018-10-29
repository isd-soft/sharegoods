package com.sharegoods.inth3rship.services;

import com.sharegoods.inth3rship.models.Image;
import com.sharegoods.inth3rship.models.Item;
import com.sharegoods.inth3rship.models.User;
import com.sharegoods.inth3rship.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    private MailService mailService;

    public List<Item> getItemsByUserId(Long id) {
        User user = userService.getUserById(id);
        return itemRepository.findByUser(user);
    }

    public List<Item> getItems(String value, String direction) {
        Sort sortByValue = getSorting(value, direction);
        return itemRepository.findAll(sortByValue);
    }

    public List<Item> getItemsByTitle(String title, String value, String direction) {
        Sort sortByValue = getSorting(value, direction);
        return itemRepository.findAllByTitleContaining(title, sortByValue);
    }

    public Sort getSorting(String value, String direction) {
        Sort.Direction sortDirection = Sort.Direction.ASC;
        if (direction.equals("Desc")) {
            sortDirection = Sort.Direction.DESC;
        }
        if (value.equals("Title")) {
            return Sort.by(new Sort.Order(sortDirection, value).ignoreCase());
        } else {
            return Sort.by(new Sort.Order(sortDirection, value));
        }
    }

    public Item getItemById(Long id) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        return optionalItem.get();
    }

    public Map<Item, Image> getItemsWithThumbnails(List<Item> items) {
        Map<Item, Image> itemsWithThumbnails = new LinkedHashMap<>();
        ListIterator<Item> itemsIterator = items.listIterator();
        while (itemsIterator.hasNext()) {
            Item currentItem = itemsIterator.next();
            Image thumbnail = imageService.getThumbnail(currentItem);
            itemsWithThumbnails.put(currentItem, thumbnail);
        }
        return itemsWithThumbnails;
    }

    public Item createNewItem(Long userId, String title, String description, List<MultipartFile> imageFiles) {
        User user = userService.getUserById(userId);
        java.util.Date dateNow = new java.util.Date();
        Timestamp date = new Timestamp(dateNow.getTime());
        Item newItem = new Item(user, date, title, description);
        itemRepository.save(newItem);
        imageService.createImages(newItem, imageFiles);

        List<String> emails = userService.getAllEmails();
        emails.remove(user.getEmail());
        mailService.sendEmail(emails, newItem);
        return newItem;
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

}