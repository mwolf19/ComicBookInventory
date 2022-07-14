package com.project.comic.controller;

import com.project.comic.model.Friend;
import com.project.comic.model.User;
import com.project.comic.repository.UserRepository;
import com.project.comic.repository.FriendRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/friend")
public class FriendController {

    private FriendRepository friendRepository;
    private UserRepository userRepository;

    public FriendController(FriendRepository friendRepository, UserRepository userRepository){
        this.friendRepository = friendRepository;
        this.userRepository = userRepository;
    }

    @GetMapping(path = "/listByFriendId/{id}", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public Friend findByFriendId(@PathVariable("id") int id){
        return friendRepository.findByFriendId(id);
    }

    @GetMapping(path = "/listByUserId/{userId}", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public Friend findByUserId(@PathVariable("userId") int userId){
        return friendRepository.findByUserId(userId);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(path = "/saveFriend", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public int saveFriend(@RequestBody Friend friendModel){
        try {
            Friend existingCheck = friendRepository.findByFriendId(friendModel.
                    getFriendId());
            int newId = 0;
            if (existingCheck == null ) {
                newId = friendRepository.save(friendModel).getFriendId();
            }
            return newId;
        } catch (Exception e){
            return -1;
        }
    }

    @GetMapping(path="/findFriend/{username}", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<User> findByUsername(@PathVariable("username") String usernameModel) {
        try {
            usernameModel = usernameModel.toLowerCase();
            List<User> userMatches = new ArrayList<>();

            for (User user : userRepository.findByIsActive(true)) {
                String checkUsername = user.getUsername().toLowerCase();
                if (checkUsername.contains(usernameModel)) {
                    userMatches.add(user);
                }
            }
            return userMatches;
        }
        catch (Exception e) {
            e.printStackTrace();
            List<User> empty = new ArrayList<>();
            return empty;
        }
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping(path="/updateFriends/{userId}/{friends}", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public int updateFriendsByUserId(@PathVariable("userId") int userId,
                                        @PathVariable("friends") String friends) {
        try {
            Friend friend = friendRepository.findByUserId(userId);

            friend.setFriends(friends);

            friendRepository.save(friend);

            return 1;
        } catch (Exception e) {
            return -1;
        }
    }

}
