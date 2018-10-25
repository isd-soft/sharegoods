package com.sharegoods.inth3rship.services;

import com.sharegoods.inth3rship.models.Comment;
import com.sharegoods.inth3rship.models.Item;
import com.sharegoods.inth3rship.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public List<Comment> getComments(Item item) {
        return commentRepository.findByItem(item);
    }

    public Comment addComment(Comment comment) {
        commentRepository.save(comment);
        return comment;
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

}