package ru.sharipov.spring.spring_boot_jm.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.sharipov.spring.spring_boot_jm.entity.User;
import ru.sharipov.spring.spring_boot_jm.service.RoleService;
import ru.sharipov.spring.spring_boot_jm.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AdminPageController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }


    @GetMapping("/admin/all-users")
    public List<User> allUsersPage() {
        return userService.getAllUsers();
    }

    @GetMapping("/admin/user/{id}")
    public User userPage(@PathVariable("id") Long id) {
        return userService.getUser(id);
    }

//    @GetMapping(value = "/login")
//    public String loginPage() {
//        return "login";
//    }

    @GetMapping(value = "/admin")
    public String adminPAge(Model model) {
        List<User> allUsers = userService.getAllUsers();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("userAuthorize", userService.findByEmail(auth.getName()));
        model.addAttribute("newUser", new User());
        model.addAttribute("roleList", roleService.getAllRoles());
        model.addAttribute("allUsers", allUsers);
        return "bootstrap-admin-page-ViewAllUsers";
    }

    @PostMapping("/")
    public String addUser(@ModelAttribute("newUser") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/admin";
        }
        userService.addUser(user);
        return "redirect:/admin";
    }

    @PutMapping("/update")
    public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        User user = userService.getUser(id);
        userService.deleteById(user);
        return "redirect:/admin";
    }


    @GetMapping("/user")
    public String showInfoUser(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("userAuthorize", userService.findByEmail(auth.getName()));
        return "bootstrap-user-info";
    }



//    @GetMapping("/admin/create")
//    public String addUser(Model model) {
//        model.addAttribute("user", new User());
//        model.addAttribute("roleList", roleService.getAllRoles());
//        return "createNewUser";
//    }

//    @GetMapping("/admin/{id}/update")
//    public String updateUser(Model model, @PathVariable("id") Long id) {
//        User user = userService.findById(id);
//        model.addAttribute("user", user);
//        model.addAttribute("roleList", roleService.getAllRoles());
//        return "update-user";
//    }

//
//    @GetMapping("/admin/{id}/delete")
//    public String deleteUser(Model model, @PathVariable("id") Long id) {
//        User user = userService.getUser(id);
//        model.addAttribute("user", user);
//        return "delete-user";
//    }

}
