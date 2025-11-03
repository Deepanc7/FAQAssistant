package com.faq.Controller;

import com.faq.Entity.UserEntity;
import com.faq.Service.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    // ✅ Create a new user
    @PostMapping
    public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    // ✅ Get all users
    @GetMapping
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // ✅ Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable UUID id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
