package com.project.comic.model;

import javax.persistence.*;

@Entity
public class FriendActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer friendActivityId;
    private Integer issueId;
    private Integer userId;
    private String activity;
    private Boolean shown;
    @Transient
    private String username;
    @Transient
    private String issueTitle;
    @Transient
    private Integer issueNumber;

    public Integer getFriendActivityId() {
        return friendActivityId;
    }

    public Integer getIssueId() {
        return issueId;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getActivity() {
        return activity;
    }

    public Boolean getShown() {
        return shown;
    }

    public String getUsername() {
        return username;
    }

    public String getIssueTitle() {
        return issueTitle;
    }

    public Integer getIssueNumber() {
        return issueNumber;
    }

    public void setFriendActivityId(Integer friendActivityId) {
        this.friendActivityId = friendActivityId;
    }

    public void setIssueId(Integer issueId) {
        this.issueId = issueId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public void setShown(Boolean shown) {
        this.shown = shown;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setIssueTitle(String issueTitle) {
        this.issueTitle = issueTitle;
    }

    public void setIssueNumber(Integer issueNumber) {
        this.issueNumber = issueNumber;
    }
}