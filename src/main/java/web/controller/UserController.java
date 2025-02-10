package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.exception.UserNotFoundException;
import web.model.User;
import web.service.UserService;
import web.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }


    @GetMapping("/users")
    public String showAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    @GetMapping("/users/show")
    public String showUserById(@RequestParam("id") long id, Model model) {
        try {
            User user = userService.getUserById(id);
            model.addAttribute("user", user);
            return "user-details";
        } catch (UserNotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/users/add")
    public String addUserForm(Model model) {
        model.addAttribute("user", new User());
        return "add-user";
    }

    @PostMapping("/users/add")
    public String addUser(@ModelAttribute User user) {
        userService.addUser(user);
        return "redirect:/users";
    }

    @GetMapping("/users/update")
    public String updateUserForm(@RequestParam("id") long id, Model model) {
        try {
            User user = userService.getUserById(id);
            model.addAttribute("user", user);
            return "update-user";
        } catch (UserNotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }

    @PostMapping("/users/update")
    public String updateUser(@ModelAttribute User user) {
        userService.updateUser(user);
        return "redirect:/users";
    }

    @GetMapping("/users/delete")
    public String deleteUser(@RequestParam("id") long id, Model model) {
        try{
            userService.deleteUser(id);
            return "redirect:/users";
        } catch (UserNotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }
}
