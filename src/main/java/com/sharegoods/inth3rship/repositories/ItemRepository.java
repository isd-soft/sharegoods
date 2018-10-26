package com.sharegoods.inth3rship.repositories;

import com.sharegoods.inth3rship.models.Item;
import com.sharegoods.inth3rship.models.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByUser(User user);
    List<Item> findAll(Sort order);
}

