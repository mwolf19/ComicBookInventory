package com.project.comic.controller;

import com.project.comic.model.User;
import com.project.comic.repository.UserRepository;
import com.project.comic.service.ValidatorService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.ArrayList;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(path="/homescreen")

public class HomescreenController {

    private UserRepository userRepository;
    private ValidatorService vs;

    public HomescreenController(UserRepository userRepository, ValidatorService vs) {
        this.userRepository = userRepository;
        this.vs = vs;
    }

    @GetMapping(path="/listActiveUsers", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<String> findActiveNames() {
        try {
            List<String> names = new ArrayList<>();
            for (User user : userRepository.findByIsActive(true)) {
                names.add(user.getUsername());
            }
            return names;
        }
        catch (Exception e) {
            e.printStackTrace();
            List<String> empty = new ArrayList<>();
            empty.add("error");
            return empty;
        }
    }
}
