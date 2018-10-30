package com.sharegoods.inth3rship.repositories;

import com.sharegoods.inth3rship.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    @Query(value="SELECT email FROM Users u WHERE u.email <> ?1", nativeQuery = true)
    List<String> getEmails(String authorEmail);
}

