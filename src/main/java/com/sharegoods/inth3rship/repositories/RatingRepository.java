package com.sharegoods.inth3rship.repositories;

import com.sharegoods.inth3rship.models.Item;
import com.sharegoods.inth3rship.models.Rating;
import com.sharegoods.inth3rship.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByItem(Item item);

    List<Rating> findByUserIdAndItemId(Long user, Long item);
}
