package com.project.comic.controller;

import com.project.comic.model.FriendActivity;
import com.project.comic.model.Issue;
import com.project.comic.repository.FriendActivityRepository;
import com.project.comic.repository.IssueRepository;
import com.project.comic.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/friendActivity")
public class FriendActivityController {
    private FriendActivityRepository friendActivityRepository;
    private UserRepository userRepository;
    private IssueRepository issueRepository;

    public FriendActivityController(FriendActivityRepository friendActivityRepository, UserRepository userRepository,
                                    IssueRepository issueRepository) {
        this.friendActivityRepository = friendActivityRepository;
        this.userRepository = userRepository;
        this.issueRepository = issueRepository;
    }

    @GetMapping(path = "/findByUserId/{userId}", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<FriendActivity> findByUserId(@PathVariable("userId") int userId) {
        List<FriendActivity> list = friendActivityRepository.findByUserId(userId);

        return list;
    }

    @GetMapping(path = "/findByUserIdAndShown/{userId}", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<FriendActivity> findByUserIdAndShown(@PathVariable("userId") int userId) {
        List<FriendActivity> list =  friendActivityRepository.findByUserIdAndShown(userId, false);

        String username = userRepository.findByUserId(userId).getUsername();

        Issue issue = new Issue();

        for (FriendActivity activity : list) {
            activity.setUsername(username);

            issue = issueRepository.findByIssueId(activity.getIssueId());

            activity.setIssueTitle(issue.getIssueName());
            activity.setIssueNumber(issue.getIssueNumber());
        }

        return list;
    }
}