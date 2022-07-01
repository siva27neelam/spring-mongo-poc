package org.pnc.user.controller;

import lombok.RequiredArgsConstructor;
import org.pnc.user.model.UserData;
import org.pnc.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<UserData>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/user")
    public ResponseEntity<Void> createUser(@RequestBody @Validated UserData userData) {
        userService.createUser(userData);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PatchMapping("/user")
    public ResponseEntity<Void> updateUser(@RequestBody UserData userData) {
        userService.createUser(userData);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserData> getuser(@PathVariable String id) {
        return ResponseEntity.ok(userService.getuser(id));
    }

    @GetMapping("/user/search")
    public ResponseEntity<List<UserData>> searchUsers(@RequestParam Map<String, String> queryParams) {
        return ResponseEntity.ok(userService.searchUsers(queryParams));
    }
}
