package ru.sharipov.spring.spring_boot_jm.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

@Controller
public class AdminPageController {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @GetMapping(value = "/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping(value = "/testAdminPage")
    public String adminPAge(Model model) {
            List<User> allUsers = userService.getAllUsers();
            model.addAttribute("allUsers", allUsers);
        return "bootstrap-admin-page-ViewAllUsers";
    }

    @GetMapping("/admin")
    public String showAllUsers(Model model) {
        List<User> allUsers = userService.getAllUsers();
        model.addAttribute("allUsers", allUsers);
        return "admin-page-ViewAllUsers";
    }



    @GetMapping("/admin/create")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roleList", roleService.getAllRoles());
        return "createNewUser";
    }

    @PatchMapping("/")
    public String addUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "createNewUser";
        }
        userService.addUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/{id}/update")
    public String updateUser(Model model, @PathVariable("id") Long id) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("roleList", roleService.getAllRoles());
        return "update-user";
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable("id") Long id, @ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            user.setId(id);
            return "update-user";
        }
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/{id}/delete")
    public String deleteUser(Model model, @PathVariable("id") Long id) {
        User user = userService.getUser(id);
        model.addAttribute("user", user);
//        userService.deleteById(user);
        return "delete-user";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        User user = userService.getUser(id);
        userService.deleteById(user);
        return "redirect:/admin";
    }

//    @GetMapping("/user")
//    public String showInfoUser(Model model) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        model.addAttribute("user", userService.findByEmail(auth.getName()));
//        return "user-info";
//    }

    @GetMapping("/user")
    public String showInfoUser(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("user", userService.findByEmail(auth.getName()));
        return "bootstrap-user-info";
    }

}
