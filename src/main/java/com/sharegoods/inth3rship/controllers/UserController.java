package com.sharegoods.inth3rship.controllers;

import com.sharegoods.inth3rship.dto.UserDto;
import com.sharegoods.inth3rship.exceptions.DeleteAdminException;
import com.sharegoods.inth3rship.services.ChatService;
import com.sharegoods.inth3rship.services.UserService;
import com.sharegoods.inth3rship.models.User;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private ChatService chatService;

    @GetMapping("/users")
    public ResponseEntity getAllUsers() {
        List<User> userList = userService.getAllUsers();
        if (userList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body("No users no found");
        }
        List<UserDto> userDtoList = UserDto.getUserDtoList(userList);
        return ResponseEntity.status(HttpStatus.OK).body(userDtoList);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity getUserById(@PathVariable("id") Long id) {
        try {
            User user = userService.getUserById(id);
            UserDto userDto = new UserDto(user);
            return ResponseEntity.status(HttpStatus.OK).body(userDto);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No user with such id found");
        }
    }

    @PostMapping("/users/login")
    public ResponseEntity getUserByLoginData(@RequestParam("email") String email,
                                             @RequestParam("password") String password) {
        User user = userService.checkLoginData(email, password);
        if (user != null) {
            UserDto userDto = new UserDto(user);
            return ResponseEntity.status(HttpStatus.OK).body(userDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Login data incorrect");
        }
    }

    @PostMapping("/users")
    public ResponseEntity createUser(@RequestBody User userDto) {
        try {
            User user = userService.createNewUser(userDto);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        } catch (IllegalAccessException i) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid password!!!");

        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        try {
            User updatedUser = userService.updateUser(id, user);
            UserDto userDto = new UserDto(updatedUser);
            return ResponseEntity.status(HttpStatus.OK).body(userDto);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User with such email already exists");
        }
    }

    @PutMapping("/users/{id}/updatepassword")
    public ResponseEntity changePassword(@PathVariable("id") Long id,
                                         @RequestParam String oldPassword,
                                         @RequestParam String newPassword) {
        try {
            userService.changePassword(id, oldPassword, newPassword);
            return ResponseEntity.status(HttpStatus.OK).body("Password updated successfully");
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Incorrect old password");
        } catch (IllegalAccessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid password");
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity deleteItem(@PathVariable("id") Long idToDelete) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        try {
            userService.deleteUser(idToDelete, email);
            return ResponseEntity.status(HttpStatus.OK).body("User was successfully deleted");
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User to be deleted cannot be found");
        } catch (DeleteAdminException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping("/users/{userId}/otherUser/{otherUserId}")
    public ResponseEntity addChatRoom(@PathVariable("userId") Long userId,
                                      @PathVariable("otherUserId") Long otherUserId) {
        try {
            chatService.addChatRoom(userId, otherUserId);
            return ResponseEntity.status(HttpStatus.OK).body("Chat room was created");
        }
        catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or otherUser cannot be found");
        }
    }
}
