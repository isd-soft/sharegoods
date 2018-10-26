package com.sharegoods.inth3rship.repositories;

import com.sharegoods.inth3rship.models.Comment;
import com.sharegoods.inth3rship.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByItem(Item item);
}
