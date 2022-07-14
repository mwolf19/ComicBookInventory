package com.project.comic.repository;

import com.project.comic.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface UserRepository extends CrudRepository<User,Integer> {
    List<User> findByIsActiveAndUsernameStartingWith(Boolean isActive, String username);
    List<User> findByIsActive(Boolean active);
    Boolean existsByUsername(String username);
    @Query("SELECT password FROM User WHERE username = ?1")
    String getPasswordByUsername(String username);
    User findByUsernameAndPassword(String username, String password);
    User findByUserId(int userId);
    List<String> findByUsername(String username);
    User getUserByUsername(String username);
    @Modifying
    @Query("UPDATE User SET password = ?2 where username = ?1")
    void updatePasswordByUsername(String Username, String password);

}
