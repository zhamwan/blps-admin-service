package com.example.blpsadminservice.controllers;

import com.example.blpsadminservice.exceptions.NoSuchTestException;
import com.example.blpsadminservice.exceptions.NoSuchUserException;
import com.example.blpsadminservice.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @DeleteMapping("/quiz/{testId}")
    public ResponseEntity<?> deleteTest(@PathVariable Long testId) throws NoSuchTestException {
        Map<Object, Object> model = new HashMap<>();
        adminService.deleteTest(testId);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<?> deletePerson(@PathVariable Long userId) throws NoSuchUserException {
        Map<Object, Object> model = new HashMap<>();
        adminService.deletePerson(userId);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }
}
