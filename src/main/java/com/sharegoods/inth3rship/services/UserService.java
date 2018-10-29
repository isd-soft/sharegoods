package com.sharegoods.inth3rship.services;

import com.sharegoods.inth3rship.common.MyUserPrincipal;
import com.sharegoods.inth3rship.models.User;
import com.sharegoods.inth3rship.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatService chatService;

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /* The loadUserByUsername method is required by the UserDetailsService interface, which is used for Spring Security
       However we identify user by email, hence the implementation is for email */
    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = findUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        return new MyUserPrincipal(user);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<String> getAllEmails() {
        return userRepository.getEmails();
    }

    public User getUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.get();
    }

    public User checkLoginData(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }

    public User createNewUser(User newUser) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        newUser.setRole("USER");
        return userRepository.save(newUser);
    }

    public User updateUser(Long id, User user) {
        User userToUpdate = getUserById(id);
        userToUpdate.setFirstName(user.getFirstName());
        userToUpdate.setLastName(user.getLastName());
        userToUpdate.setEmail((user.getEmail()));
        if (!user.getPassword().isEmpty()) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            userToUpdate.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return userRepository.save(userToUpdate);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
