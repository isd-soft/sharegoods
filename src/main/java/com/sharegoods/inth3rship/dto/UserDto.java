package com.sharegoods.inth3rship.dto;

import com.sharegoods.inth3rship.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;


public class UserDto {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String role;


    public UserDto(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.role = user.getRole();
    }


    public static List<UserDto> getUserDtoList(List<User> userList) {

        List<UserDto> userDtoList = new ArrayList<UserDto>();

        ListIterator<User> userListIterator = userList.listIterator();
        while (userListIterator.hasNext()) {
            UserDto userDto = new UserDto(userListIterator.next());
            userDtoList.add(userDto);
        }
        return userDtoList;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole()
    {
        return role;
    }

    public void setRole(String role)
    {
        this.role = role;
    }
}