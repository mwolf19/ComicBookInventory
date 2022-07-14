package com.project.comic.service;

import com.project.comic.model.User;
import com.project.comic.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Component
public class ValidatorService {

    private UserRepository ur;
    private HashingService hs;

    public ValidatorService(UserRepository ur, HashingService hs) {
        this.ur = ur;
        this.hs = hs;
    }

    public boolean validateUserNameAvailable(User u) {
        return ur.existsByUsername(u.getUsername());
    }

    public boolean validateLogin(User u) throws InvalidKeySpecException, NoSuchAlgorithmException {
        String hash = ur.getPasswordByUsername(u.getUsername());
        String givenPwd = hs.hash(u.getPassword());
        return hash.equals(givenPwd);
    }
}
