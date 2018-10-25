package com.sharegoods.inth3rship.dto;

import com.sharegoods.inth3rship.models.Comment;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class CommentDto {

    Long id;
    Long itemId;
    Timestamp dateTime;
    String comment;
    String userName;


    public CommentDto(Comment comment) {
        this.id = comment.getId();
        this.itemId = comment.getItem().getId();
        this.dateTime = comment.getDateTime();
        this.comment = comment.getComment();
        this.userName = comment.getUser().getFirstName() + ' ' + comment.getUser().getLastName();
    }

    public static List<CommentDto> getCommentDtoList(List<Comment> commentList) {
        List<CommentDto> commentDtoList = new ArrayList<>();
        ListIterator<Comment> commentListIterator = commentList.listIterator();
        while (commentListIterator.hasNext()) {
            CommentDto commentDto = new CommentDto(commentListIterator.next());
            commentDtoList.add(commentDto);
        }
        return commentDtoList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}