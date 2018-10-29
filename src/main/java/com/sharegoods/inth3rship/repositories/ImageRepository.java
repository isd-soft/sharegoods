package com.sharegoods.inth3rship.repositories;

import com.sharegoods.inth3rship.models.Image;
import com.sharegoods.inth3rship.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByItemAndThumbnail(Item item, boolean thumbnail);
    void deleteByItem(Item item);
}
