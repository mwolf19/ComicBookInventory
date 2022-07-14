package com.project.comic.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Friend implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer friendId;
    private Integer userId;
    private String friends;

    public Integer getFriendId() {
        return friendId;
    }

    public void setFriendId(Integer friendID) {
        this.friendId = friendID;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFriends() { return friends; }

    public void setFriends(String friends) { this.friends = friends; }
}
