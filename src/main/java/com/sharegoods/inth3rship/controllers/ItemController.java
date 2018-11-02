package com.sharegoods.inth3rship.controllers;

import com.sharegoods.inth3rship.dto.*;
import com.sharegoods.inth3rship.exceptions.*;
import com.sharegoods.inth3rship.models.*;
import com.sharegoods.inth3rship.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private ChatService chatService;

    @Autowired
    private UserService userService;

    @GetMapping("/users/{userId}/items")
    public ResponseEntity getItemsByUserId(@PathVariable("userId") Long userId,
                                           @RequestParam(value = "search", required = false) String searchQuery,
                                           @RequestParam(value = "value", required = false) String sortBy,
                                           @RequestParam(value = "direction", required = false) String sortDirection) {
        try {
            List<Item> itemList = itemService.getItemsByUserId(userId, searchQuery, sortBy, sortDirection);
            if (itemList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body("User has no items");
            }

            Map<Item, Image> itemHashMap = itemService.getItemsWithThumbnails(itemList);
            List<ItemThumbnailsDto> itemThumbnailsDtoList = ItemThumbnailsDto.getItemThumbnailsDtoList(itemHashMap);
            return ResponseEntity.status(HttpStatus.OK).body(itemThumbnailsDtoList);

        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @GetMapping("/items")
    public ResponseEntity getItems(@RequestParam(value = "search", required = false) String searchQuery,
                                   @RequestParam(value = "value", required = false) String sortBy,
                                   @RequestParam(value = "direction", required = false) String sortDirection) {

        List<Item> itemList = itemService.getItems(searchQuery, sortBy, sortDirection);
        if (itemList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body("No items found");
        }

        Map<Item, Image> itemHashMap = itemService.getItemsWithThumbnails(itemList);
        List<ItemThumbnailsDto> itemThumbnailsDtoList = ItemThumbnailsDto.getItemThumbnailsDtoList(itemHashMap);
        return ResponseEntity.status(HttpStatus.OK).body(itemThumbnailsDtoList);
    }

    @GetMapping("/items/{id}")
    public ResponseEntity getItemById(@PathVariable("id") Long id) {
        try {
            Item item = itemService.getItemById(id);
            List<Image> itemImages = imageService.getImagesByItemId(id);
            ItemDto itemDto = new ItemDto(item);
            List<ImageDto> imageDtoList = ImageDto.getImageDtoList(itemImages);
            boolean isAuthorOnline = chatService.isAuthorOnline(item.getUser().getId());
            ItemDetailsDto itemDetailsDto = new ItemDetailsDto(itemDto, imageDtoList, isAuthorOnline);
            return ResponseEntity.status(HttpStatus.OK).body(itemDetailsDto);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item not found");
        }
    }

    @PostMapping("/users/{id}/items")
    public ResponseEntity createItem(@PathVariable("id") Long userId,
                                     @RequestParam("title") String title,
                                     @RequestParam("description") String description,
                                     @RequestParam("file") List<MultipartFile> imageFiles) {
        Item item = itemService.createNewItem(userId, title, description, imageFiles);
        ItemDto itemDto = new ItemDto(item);
        return ResponseEntity.status(HttpStatus.OK).body(itemDto);
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity deleteItem(@PathVariable("id") Long itemId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        try {
            itemService.deleteItem(email, itemId);
            return ResponseEntity.status(HttpStatus.OK).body("Deleted successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or item not found");
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PutMapping("/items/{itemId}")
    public ResponseEntity updateItem(@PathVariable("itemId") Long itemId,
                                     @RequestParam("title") String title,
                                     @RequestParam("description") String description,
                                     @RequestParam("file") List<MultipartFile> imageFiles,
                                     @RequestParam(value = "uploadedImagesIds", required = false) List<String> imageIds) {
        try {
            Item item = itemService.updateItem(itemId, title, description, imageFiles, imageIds);
            ItemDto itemDto = new ItemDto(item);
            return ResponseEntity.status(HttpStatus.OK).body(itemDto);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item not found");
        }
    }

    /***** image *****/

    @GetMapping("/items/getImage/{id}")
    public byte[] getImage(@PathVariable("id") Long id) {
        Image image = imageService.getImageById(id);
        return image.getImageData();
    }

    /***** comments *****/

    @GetMapping("/items/{id}/comments")
    public ResponseEntity getComments(@PathVariable("id") Long id) {
        try {
            Item item = itemService.getItemById(id);
            List<Comment> comments = commentService.getComments(item);
            List<CommentDto> commentDtoList = CommentDto.getCommentDtoList(comments);
            return ResponseEntity.status(HttpStatus.OK).body(commentDtoList);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item not found");
        }
    }

    @PostMapping("/items/{itemId}/addComment")
    public ResponseEntity addComment(@PathVariable("itemId") Long itemId,
                                     @RequestParam("userId") Long userId,
                                     @RequestParam("comment") String comment) {
        Comment commentToSave = commentService.addComment(itemId, userId, comment);
        CommentDto commentDto = new CommentDto(commentToSave);
        return ResponseEntity.status(HttpStatus.OK).body(commentDto);
    }

    @DeleteMapping("items/{itemId}/comments/{id}")
    public ResponseEntity deleteComment(@PathVariable("id") Long id) {
        try {
            commentService.deleteComment(id);
            return ResponseEntity.status(HttpStatus.OK).body("Comment deleted successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment not found");
        }
    }

    @PutMapping("items/{itemId}/comments/{id}")
    public ResponseEntity updateComment(@PathVariable("id") Long id,  @RequestParam("comment") String comment) {
        try {
            Comment updatedComment = commentService.updateComment(id, comment);
            CommentDto commentDto = new CommentDto(updatedComment);
            return ResponseEntity.status(HttpStatus.OK).body(commentDto);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment not found");
        }
    }

    /***** rating *****/

    @PostMapping("/items/{itemId}/addRating")
    public ResponseEntity createRating(@PathVariable("itemId") Long itemId,
                                       @RequestParam("userId") Long userId,
                                       @RequestParam("rating") Double rating) {
        try {
            ratingService.createRating(userId, itemId, rating);
            Item item = itemService.getItemById(itemId);
            ItemDto itemDto = new ItemDto(item);
            return ResponseEntity.status(HttpStatus.OK).body(itemDto);

        } catch (UserNotFoundException e) {
            return ResponseEntity.status((HttpStatus.CONFLICT)).body(e.getMessage());
        } catch (ItemNotFoundException e) {
            return ResponseEntity.status((HttpStatus.CONFLICT)).body(e.getMessage());
        } catch (RatingValidationException e) {
            return ResponseEntity.status((HttpStatus.CONFLICT)).body(e.getMessage());
        } catch (VoteTwiceException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/items/{itemId}/getAvgRating")
    public ResponseEntity getAvgRating(@PathVariable("itemId") Long id) {
        try{
            Item item = itemService.getItemById(id);
            ItemDto itemDto = new ItemDto(item);
            return ResponseEntity.status(HttpStatus.OK).body(itemDto);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rating not found");
        }
    }

    @GetMapping("/users/{userId}/items/{itemId}/getRating")
    public ResponseEntity getUserRating(@PathVariable("userId") Long userId,
                                        @PathVariable("itemId") Long itemId) {
        try{
            List<Rating> ratingList = ratingService.getRatingByItemAndUser(itemId, userId);
            if(ratingList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rating was not found for user " + userId  + " and for item " + itemId);
            }
            RatingDto ratingDto = new RatingDto(ratingList.get(0));
            return ResponseEntity.status(HttpStatus.OK).body(ratingDto);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (ItemNotFoundException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}