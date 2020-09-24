package com.auth0.samples.authapi.springbootauthupdated.user;

import com.auth0.samples.authapi.springbootauthupdated.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;

    public UserController(BCryptPasswordEncoder bCryptPasswordEncoder, UserService userService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
    }

    /**
     * Finds all users
     * @return list with all users
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ApplicationUser> getAllUsers() {
        return userService.findAll();
    }

    /**
     * Method that creates a user
     * @param user user which should be crated
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(@RequestBody ApplicationUser user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        userService.createUser(user);
        if (userService.createUser(user).getId() == 1) {
            user.setRole(Role.ADMIN);
            editEntry(user);
        }
    }

    /**
     * Deletes user
     * @param id id of the user which should be deleted
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@Valid @PathVariable long id) {
        userService.deleteUser(id);
    }

    /**
     * Updates user
     * @param user user which should be updated
     */
    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApplicationUser editEntry(@Valid @RequestBody ApplicationUser user) {
        return userService.updateUser(user);
    }
}
