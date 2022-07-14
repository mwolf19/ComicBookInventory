package com.project.comic.controller;

import com.project.comic.model.FriendActivity;
import com.project.comic.model.Hero;
import com.project.comic.model.Issue;
import com.project.comic.model.Publisher;
import com.project.comic.repository.FriendActivityRepository;
import com.project.comic.repository.HeroRepository;
import com.project.comic.repository.IssueRepository;
import com.project.comic.repository.PublisherRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping(path = "/issue")
public class IssueController {

    private IssueRepository issueRepository;
    private PublisherRepository publisherRepository;
    private HeroRepository heroRepository;
    private FriendActivityRepository friendActivityRepository;

    public IssueController(IssueRepository issueRepository, PublisherRepository publisherRepository,
                           HeroRepository heroRepository, FriendActivityRepository friendActivityRepository) {
        this.issueRepository = issueRepository;
        this.publisherRepository = publisherRepository;
        this.heroRepository = heroRepository;
        this.friendActivityRepository = friendActivityRepository;
    }

    private String getPublisherName(int publisherId) {
        String name = "N/A";
        Publisher publisher = publisherRepository.findByPublisherIdAndIsActive(publisherId, true);

        if (publisher != null) {
            name = publisher.getPublisherName();
        }

        return name;
    }

    private String getCharacterNames(String characterTags) {
        List<String> characterIds = List.of(characterTags.split(","));

        Iterator<String> iterator = characterIds.iterator();

        String characterNames = "";

        String idString = "";

        Integer characterId = 0;

        while (iterator.hasNext()) {
            idString = iterator.next();

            if (!idString.equals("")) {
                characterId = Integer.parseInt(idString);

                Hero hero = heroRepository.findByCharacterIdAndIsActive(characterId, true);

                if (hero != null) {
                    characterNames += hero.getCharacterName();

                    if (iterator.hasNext()) {
                        characterNames += ",";
                    }
                }
            }
        }

        return characterNames;
    }

    @GetMapping(path = "/listAllActive", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public Iterable<Issue> findByIsActive() {
        return issueRepository.findByIsActive(true);
    }

    @GetMapping(path = "/listByIssueId/{id}", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public Issue findByIssueId(@PathVariable("id") int id) {
        Issue issue = issueRepository.findByIssueId(id);

        issue.setPublisherName(getPublisherName(issue.getPublisherId()));

        issue.setCharacterString(getCharacterNames(issue.getCharacterTags()));

        return issue;
    }

    @GetMapping(path = "/getUserCollection/{userId}", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<Issue> getUserCollection(@PathVariable("userId") int userID) {
        List<Issue> list = issueRepository.findByUserIdAndIsActive(userID, true);

        for (Issue item : list) {
            item.setPublisherName(getPublisherName(item.getPublisherId()));

            item.setCharacterString(getCharacterNames(item.getCharacterTags()));
        }

        return list;
    }

    @GetMapping(path = "/listByUserIdAndPublisherId/{userId}/{publisherId}", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<Issue> findByUserIdAndIsActiveAndPublisherId(@PathVariable("userId") int userId, @PathVariable("publisherId") int publisherId) {
        List<Issue> issues = issueRepository.findByUserIdAndIsActiveAndPublisherId(userId, true, publisherId);

        for (Issue issue : issues) {
            issue.setPublisherName(getPublisherName(issue.getPublisherId()));

            issue.setCharacterString(getCharacterNames(issue.getCharacterTags()));
        }

        return issues;
    }

    @GetMapping(path = "/listByUserIdAndCharacterId/{userId}/{characterId}", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<Issue> findByUserIdAndIsActiveAndCharacterId(@PathVariable("userId") int userId, @PathVariable("characterId") String characterId) {
        List<Issue> issues = issueRepository.findByUserIdAndIsActiveAndCharacterTagsContaining(userId, true, "," + characterId + ",");

        for (Issue issue : issues) {
            issue.setPublisherName(getPublisherName(issue.getPublisherId()));

            issue.setCharacterString(getCharacterNames(issue.getCharacterTags()));
        }

        return issues;
    }

    @GetMapping(path = "/filterBySearchName/{userId}/{name}", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<Issue> filterBySearchName(@PathVariable("userId") int userId, @PathVariable("name") String issueName) {
        List<Issue> issues = issueRepository.findByUserIdAndIsActiveAndIssueNameStartingWith(userId, true, issueName);

        for (Issue issue : issues) {
            issue.setPublisherName(getPublisherName(issue.getPublisherId()));

            issue.setCharacterString(getCharacterNames(issue.getCharacterTags()));
        }

        return issues;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(path = "/saveIssue", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public int saveIssue(@RequestBody Issue issueModel) {
        try {
            int newId = 0;

            issueModel.setIsActive(true);
            issueModel.setCharacterTags(issueModel.getCharacterTags().trim());
            newId = issueRepository.save(issueModel).getIssueId();

            if (issueModel.getIssueId() == 0) {
                FriendActivity activity = new FriendActivity();
                activity.setIssueId(newId);
                activity.setUserId(issueModel.getUserId());
                activity.setActivity("added");
                activity.setShown(false);

                friendActivityRepository.save(activity);
            }

            return newId;
        } catch (Exception e) {
            return -1;
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping(path = "/removeIssue", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public int removeIssue(@RequestBody Issue issueModel) {
        try {
            issueModel.setIsActive(false);
            issueRepository.save(issueModel).getIssueId();

            FriendActivity activity = new FriendActivity();
            activity.setIssueId(issueModel.getIssueId());
            activity.setUserId(issueModel.getUserId());
            activity.setActivity("deleted");
            activity.setShown(false);

            friendActivityRepository.save(activity);

            return 1;
        } catch (Exception e) {
            return -1;
        }
    }
}