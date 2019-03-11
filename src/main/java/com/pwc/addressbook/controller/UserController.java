package com.pwc.addressbook.controller;

import com.pwc.addressbook.model.Friend;
import com.pwc.addressbook.model.User;
import com.pwc.addressbook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("user")
    public ResponseEntity<User> addNewUser(@RequestBody @Valid User user) {
        return ResponseEntity.ok(userService.save(user));
    }

    @PostMapping("addressbook/unique")
    public ResponseEntity<Set<String>> getUniqueFriends(HttpServletRequest request, @RequestBody Set<Friend> setOfNames) {
        String userId = request.getHeader("Authorization");
        return ResponseEntity.ok(userService.getFriendsThatAreNotInListProvided(userId, setOfNames));
    }


    @GetMapping("addressbook")
    public ResponseEntity<Set<Friend>> getAddressBook(HttpServletRequest request) {
        String userId = request.getHeader("Authorization");
        return ResponseEntity.ok(userService.getAddressBook(userId));
    }

    @PostMapping("addressbook")
    public ResponseEntity<User> addFriend(HttpServletRequest request, @Valid @RequestBody Friend friend) {
        String userId = request.getHeader("Authorization");
        return ResponseEntity.ok(userService.addFriend(userId, friend));
    }

    @GetMapping("manage/user")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

}
