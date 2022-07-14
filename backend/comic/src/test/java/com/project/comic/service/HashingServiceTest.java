package com.project.comic.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.junit.jupiter.api.Assertions.*;

class HashingServiceTest {
    @Test
    public void hashTest() throws InvalidKeySpecException, NoSuchAlgorithmException {
        HashingService hs = new HashingService();
        String p1 = hs.hash("test");
        String p2 = hs.hash("test");
        System.out.printf("P1: %s, P2: %s\n", p1, p2);
        assertEquals(p1,p2);
    }
}