package com.sharegoods.inth3rship.restcontrollers;

import com.sharegoods.inth3rship.dto.UserDto;
import com.sharegoods.inth3rship.services.UserService;
import com.sharegoods.inth3rship.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

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
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity updateUser(@RequestBody User user, @PathVariable("id") Long id) {
        try {
            User updatedUser = userService.updateUser(id, user);
            UserDto userDto = new UserDto(updatedUser);
            return ResponseEntity.status(HttpStatus.OK).body(userDto);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User with such email already exists");
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity deleteItem(@PathVariable("id") Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.status(HttpStatus.OK).body("User was deleted successfully !");
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
}
