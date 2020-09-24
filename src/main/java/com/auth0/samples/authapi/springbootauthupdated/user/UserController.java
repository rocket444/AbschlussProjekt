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

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ApplicationUser> getAllUsers() {
        return userService.findAll();
    }

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

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@Valid @PathVariable long id) {
        userService.deleteUser(id);
    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApplicationUser editEntry(@Valid @RequestBody ApplicationUser user) {
        return userService.updateUser(user);
    }
}
