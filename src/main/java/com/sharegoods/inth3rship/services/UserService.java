package com.sharegoods.inth3rship.services;

import com.sharegoods.inth3rship.common.MyUserPrincipal;
import com.sharegoods.inth3rship.exceptions.DeleteAdminException;
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
import java.util.regex.Pattern;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatService chatService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

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

    public List<String> getAllEmails(String authorEmail) {
        return userRepository.getEmails(authorEmail);
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

    public User createNewUser(User newUser) throws IllegalAccessException {

        final Pattern pattern1 = Pattern.compile("[a-z]");
        final Pattern pattern2 = Pattern.compile("[A-Z]");
        final Pattern pattern3 = Pattern.compile("[0-9]");


        String password = newUser.getPassword();

        if (pattern1.matcher(password).find() && pattern2.matcher(password).find() && pattern3.matcher(password).find()) {
            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
            newUser.setRole("USER");
            return userRepository.save(newUser);
        } else {
            throw new IllegalAccessException("Incorect password!");
        }


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

    public void deleteUser(Long idToDelete, String email) throws DeleteAdminException {
        User user = findUserByEmail(email);
        if(idToDelete != user.getId()) {
            userRepository.deleteById(idToDelete);
        } else {
            throw new DeleteAdminException("are you mad bro?");
        }

    }
}
