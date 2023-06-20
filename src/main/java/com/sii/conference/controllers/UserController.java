package com.sii.conference.controllers;

import com.sii.conference.data.User;
import com.sii.conference.exceptions.user.UserWithThisLoginExistsException;
import com.sii.conference.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<String> addNewUser(@RequestBody User newUser)
    {
        try {
            User createdUser = userService.createUser(newUser);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(createdUser.getId())
                    .toUri();

            return ResponseEntity.created(location).body("User created successfully.");
        } catch (UserWithThisLoginExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Integer id)
    {
        final Optional<User> foundUser = userService.findUserById(id);
        return ResponseEntity.of(foundUser);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> userList = userService.findAll();
        return ResponseEntity.ok().body(userList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Integer id, @RequestBody User newUser)
    {
        Optional<User> updatedUser = userService.updateUser(id, newUser);
        return ResponseEntity.of(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable("id") Integer id)
    {
        final Optional<User> userToDelete = userService.findUserById(id);

        if (userToDelete.isPresent()) {
            userService.deleteUser(userToDelete.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
