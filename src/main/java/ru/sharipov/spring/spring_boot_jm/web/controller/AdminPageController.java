package ru.sharipov.spring.spring_boot_jm.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.sharipov.spring.spring_boot_jm.EntityDTO.UserDTO;
import ru.sharipov.spring.spring_boot_jm.EntityDTO.UserMapper;
import ru.sharipov.spring.spring_boot_jm.entity.Role;
import ru.sharipov.spring.spring_boot_jm.entity.User;
import ru.sharipov.spring.spring_boot_jm.service.RoleService;
import ru.sharipov.spring.spring_boot_jm.service.UserService;

import java.util.List;

@Controller
public class AdminPageController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    UserMapper userMapper;


    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/user")
    public String getAuthUser() {
        return "rest-bootstrap-user-info";
    }

    @GetMapping("/admin")
    public String getAdminUser() {
        return "rest-bootstrap-admin-page-ViewAllUsers";
    }

    @ResponseBody
    @GetMapping("/user/user-auth")
    public ResponseEntity<UserDTO> getUserSession() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDTO userDTO = userService.findByEmail(auth.getName());
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }


    @ResponseBody
    @GetMapping("/admin/all-users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    @ResponseBody
    @GetMapping("/admin/all-roles")
    public ResponseEntity<List<Role>> roleSet() {
        List<Role> roleSet = roleService.getAllRoles();
        return new ResponseEntity<>(roleSet, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/admin/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("id") Long id) {
        User user = userService.getUser(id);
        UserDTO userDTO = userMapper.toDto(user);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping("/admin/test")
    public ResponseEntity<UserDTO> addUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.addUser(user), HttpStatus.OK);
    }

    @ResponseBody
    @PutMapping("/admin/test")
    public ResponseEntity<UserDTO> updateUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.addUser(user), HttpStatus.OK);
    }

    @ResponseBody
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> deleteUserRest(@PathVariable Long id) {
        User user = userService.getUser(id);
        userService.deleteById(user);
        return ResponseEntity.ok().build();
    }
}
