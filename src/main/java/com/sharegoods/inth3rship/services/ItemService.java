package com.sharegoods.inth3rship.services;

import com.sharegoods.inth3rship.exceptions.UserNotFoundException;
import com.sharegoods.inth3rship.models.Image;
import com.sharegoods.inth3rship.models.Item;
import com.sharegoods.inth3rship.models.User;
import com.sharegoods.inth3rship.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
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

    public List<Item> getItemsByUserId(Long userId, String searchQuery, String sortBy, String sortDirection) throws UserNotFoundException {

        // User
        User user;
        try {
            user = userService.getUserById(userId);
        } catch (NoSuchElementException e) {
            throw new UserNotFoundException("User with id " + userId + "does not exist");
        }

        // Sorting
        Sort sort = getSorting(sortBy, sortDirection);

        // With search query
        if (searchQuery != null && !searchQuery.isEmpty()) {
            return itemRepository.findAllByUserAndTitleContainingIgnoreCase(user, searchQuery, sort);
        }

        // Without search query
        List<Item> itemsList = itemRepository.findAllByUser(user, sort);
        return itemsList;

    }

    public List<Item> getItems(String searchQuery, String sortBy, String sortDirection) {

        // Sorting
        Sort sort = getSorting(sortBy, sortDirection);

        // With search query
        if (searchQuery != null && !searchQuery.isEmpty()) {

            List<Item> itemsList = itemRepository.findAllByTitleContainingIgnoreCase(searchQuery, sort);
            System.out.println(itemsList);
            return itemsList;
        }

        // Without search query
        List<Item> itemsList = itemRepository.findAll(sort);
        return itemsList;
    }

    public Sort getSorting(String value, String direction) {

        String sortValue;
        Sort.Direction sortDirection;

        if (value == null || value.isEmpty()) {
            sortValue = "Rating";
        } else {
            sortValue = value;
        }

        if (direction == null || direction.equals("Asc")) {
            sortDirection = Sort.Direction.ASC;
        } else {
            sortDirection = Sort.Direction.DESC;
        }

        return Sort.by(new Sort.Order(sortDirection, sortValue).ignoreCase());
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

        List<String> emails = userService.getAllEmails(user.getEmail());
        new Thread( () -> {
                mailService.sendEmail(emails, newItem);
            }
        ).start();

        return newItem;
    }

    public void deleteItem(String email, Long id) throws AccessDeniedException {
        User user = userService.findUserByEmail(email);
        Item item = getItemById(id);

        if(user.getRole().equals("ADMIN") | user.getId().equals(item.getUser().getId())) {
            itemRepository.deleteById(id);
        } else {
            throw new AccessDeniedException("User " + user.getId() + " with role " + user.getRole() + " has no access to delete item " + item.getId());
        }


    }

    public Item updateItem(Long itemId, String title, String description, List<MultipartFile> imageFiles) {
        Item item = getItemById(itemId);
        item.setTitle(title);
        item.setDescription(description);
        imageService.updateItemImages(item, imageFiles);
        return itemRepository.save(item);
    }

}