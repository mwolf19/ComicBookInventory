package com.project.comic.repository;

import com.project.comic.model.FriendActivity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendActivityRepository extends CrudRepository<FriendActivity, Integer> {
    List<FriendActivity> findByUserId(Integer userId);
    List<FriendActivity> findByUserIdAndShown(Integer userId, Boolean shown);
}