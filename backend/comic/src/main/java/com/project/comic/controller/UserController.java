package com.project.comic.controller;

import com.project.comic.model.User;
import com.project.comic.repository.UserRepository;
import com.project.comic.service.ValidatorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(path="/user")
public class UserController {

    private UserRepository userRepository;;
    private ValidatorService vs;

    public UserController(UserRepository userRepository, ValidatorService vs) {
        this.userRepository = userRepository;
        this.vs = vs;
    }


    @PostMapping(path = "/createUser", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public Integer createUser(@RequestBody User user) {
        try {
            
            user.setIsActive(true);

            if(!vs.validateUserNameAvailable(user)) {
                return userRepository.save(user).getUserId();
            } else {
                return 0; //Not added
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @GetMapping(path = "/login/{username}/{password}", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public Integer login(@PathVariable("username") String username, @PathVariable("password") String password) {
        try {
            int newId = 0;

            User userCheck = userRepository.findByUsernameAndPassword(username, password);

            if(userCheck != null && userCheck.getUserId() > 0) {
                newId  = userCheck.getUserId();
            }

            return newId;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @GetMapping(path = "/updatePassword/{username}/{old_password}/{new_password}", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public Integer updatePassword(@PathVariable("username") String username,
                                  @PathVariable("old_password") String old_password,
                                  @PathVariable("new_password") String new_password) {
        try {
            int newId = 0;

            User userCheck = userRepository.findByUsernameAndPassword(username, old_password);

            if(userCheck != null
                    && userCheck.getUserId() > 0
                    && !new_password.isEmpty()) {
                newId  = userCheck.getUserId();
                userRepository.updatePasswordByUsername(userCheck.getUsername(), new_password);
            }

            return newId;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @GetMapping(path = "/getUserById/{id}", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public User login(@PathVariable("id") int id) {
        try {
            return userRepository.findByUserId(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping(path = "/userSearch/{username}", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<User> findByIsActiveAndUserNameStartingWith(@PathVariable("username") String username) {
        try {
            return userRepository.findByIsActiveAndUsernameStartingWith(true, username);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}