package com.project.comic.repository;

import com.project.comic.model.Friend;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRepository extends CrudRepository<Friend, Integer> {
    Friend findByFriendId(int id);
    Friend findByUserId(int userId);
}

