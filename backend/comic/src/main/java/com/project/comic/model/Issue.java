package com.project.comic.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Issue implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer issueId;
    private Integer userId;
    private Integer publisherId;
    private Integer issueNumber;
    private Double coverPrice;
    private String characterTags; // possibly array of Strings
    private Boolean isActive;
    private String issueName;
    private String coverImage;
    @Transient
    private String publisherName;
    @Transient
    private String characterString;

    public Integer getIssueId() {
        return issueId;
    }

    public void setIssueId(Integer issueId) {
        this.issueId = issueId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Integer publisherId) {
        this.publisherId = publisherId;
    }

    public Integer getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(Integer issueNumber) {
        this.issueNumber = issueNumber;
    }

    public Double getCoverPrice() {
        return coverPrice;
    }

    public void setCoverPrice(Double coverPrice) {
        this.coverPrice = coverPrice;
    }

    public String getCharacterTags() {
        return characterTags;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public void setCharacterTags(String characterTags) {
        this.characterTags = characterTags;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getIssueName() {
        return issueName;
    }

    public void setIssueName(String issueName) {
        this.issueName = issueName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setCharacterString(String characterString) {
        this.characterString = characterString;
    }

    public String getCharacterString() {
        return characterString;
    }
}