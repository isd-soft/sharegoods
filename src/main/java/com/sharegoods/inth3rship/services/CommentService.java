package com.sharegoods.inth3rship.services;

import com.sharegoods.inth3rship.models.Comment;
import com.sharegoods.inth3rship.models.Item;
import com.sharegoods.inth3rship.models.User;
import com.sharegoods.inth3rship.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ItemService itemService;

    public List<Comment> getComments(Item item) {
        return commentRepository.findAllByItem(item);
    }

    public Comment addComment(Long itemId, Long userId, String comment) {
        User user = userService.getUserById(userId);
        Item item = itemService.getItemById(itemId);
        java.util.Date dateNow = new java.util.Date();
        Timestamp date = new Timestamp(dateNow.getTime());
        Comment newComment = new Comment(item, user, date, comment);
        commentRepository.save(newComment);
        return newComment;
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

}