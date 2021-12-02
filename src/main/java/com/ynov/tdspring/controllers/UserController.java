package com.ynov.tdspring.controllers;

import com.ynov.tdspring.entities.User;
import com.ynov.tdspring.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    // --------------------- >

    @RequestMapping(path = "/add-test-user", method = RequestMethod.GET)
    public void addTestUser() {
        User user = new User();
        user.setUsername("neerfix");
        user.setFirstname("Jean Pierre");
        user.setLastname("Test");
        user.setPhone("0610203040");
        user.setRole("ADMIN");
        user.setPassword("qwertyuiop");
        userService.createOrUpdate(user);
    }

    @RequestMapping(path = "/user", method = RequestMethod.GET)
    public User getUser(@RequestParam(value = "id") String username) {
        return userService.getUserByUsername(username);
    }

    @Operation(summary = "Création ou mise à jour d'un utilisateur")
    @RequestMapping(path = "/user", method = RequestMethod.PUT)
    public User addOrUpdateUser(@Valid @RequestBody User user) {
        return userService.createOrUpdate(user);
    }

    @Operation(summary = "Récupération de tous les utilisateurs")
    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<User> getUsers() {
        return (List<User>) userService.getAllUsers();
    }

    @Operation(summary = "Suppression d'un utilisateur à partir de son username")
    @RequestMapping(path = "/user", method = RequestMethod.DELETE)
    public void deleteUser(@RequestParam(value = "username") String username) {
        userService.delete(username);
    }

    @Operation(summary = "Mise à jour du mot de passe d'un utilisateur")
    @RequestMapping(path = "/user/updatePassword", method = RequestMethod.GET)
    public void setPassword(@RequestParam(value = "username") String username,
                            @RequestParam(value = "old") String oldPassword,
                            @RequestParam(value = "new") String newPassword) throws IllegalAccessException {
        userService.setPassword(username, oldPassword, newPassword);
    }
}
