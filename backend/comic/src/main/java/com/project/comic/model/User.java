package com.project.comic.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userId;
    private String username;
    private String password;
    private Boolean isActive;

    public User(String username, String password, Boolean isActive) {
        this.username = username;
        this.password = password;
        this.isActive = isActive;
    }

    public User() {
        // Empty constructor is only needed if you have a specific constructor
        // like the one above
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "{" +
                "\"username\":\"" + username +
                "\", \"isActive\":\"" + isActive +
                "\"}";
    }
}
