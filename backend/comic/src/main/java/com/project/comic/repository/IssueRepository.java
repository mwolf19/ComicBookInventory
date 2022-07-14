package com.project.comic.repository;

import com.project.comic.model.Issue;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepository extends CrudRepository<Issue, Integer> {
    List<Issue> findByIsActive(Boolean isActive);
    Issue findByIssueIdAndIsActive(Integer id, Boolean isActive);
    Issue findByIssueNameAndIsActive(String name, Boolean isActive);
    Issue findByIssueId(Integer id);
    List<Issue> findByUserIdAndIsActiveAndPublisherId(Integer userId, Boolean isActive, Integer publisherId);
    @Query("SELECT i.issueId, i.userId, i.publisherId, i.issueNumber, i.coverPrice, i.characterTags, i.isActive, i.issueName, p.publisherName FROM Issue i, Publisher p WHERE  i.userId = ?1 AND p.publisherId = i.publisherId")
    List<Issue> findUserCollection(Integer userID, Boolean isActive);
    List<Issue> findByUserIdAndIsActiveAndIssueNameStartingWith(Integer userId, Boolean isActive, String issueName);
    List<Issue> findByUserIdAndIsActive(Integer userId, Boolean isActive);
    List<Issue> findByUserIdAndIsActiveAndCharacterTagsContaining(Integer userId, Boolean isActive, String characterId);
}