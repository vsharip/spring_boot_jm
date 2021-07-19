package ru.sharipov.spring.spring_boot_jm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sharipov.spring.spring_boot_jm.entity.Role;
import ru.sharipov.spring.spring_boot_jm.entity.User;
import ru.sharipov.spring.spring_boot_jm.repository.RoleRepository;
import ru.sharipov.spring.spring_boot_jm.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
    }

    public void addUser(User user) {
        Set<Role> roleSet = new HashSet<>();
        if (userRepository.findByEmail(user.getEmail()) == null) {
            user.getRoles().stream().forEach(role -> {
                        if (role.getName().equals("ADMIN")) {
                            roleSet.add(roleRepository.getById(1L));
                        } else if (role.getName().equals("USER")) {
                            roleSet.add(roleRepository.getById(2L));
                        }
                    }
            );
            String encryptPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encryptPassword);
            user.setRoles(roleSet);
            userRepository.save(user);
        }
    }





    public void updateUser(User user) {
        Set<Role> roleSet = new HashSet<>();
        user.getRoles().stream().forEach(role -> {
            if (role.getName().equals("ADMIN")) {
                roleSet.add(roleRepository.getById(1L));
            } else if (role.getName().equals("USER")) {
                roleSet.add(roleRepository.getById(2L));

            }
            String encryptPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encryptPassword);
            user.setRoles(roleSet);
            userRepository.save(user);
        });
    }


        public void deleteById (User user){
            userRepository.delete(user);
        }

        public User findById (Long id){
            return userRepository.findById(id).orElseThrow(() ->
                    new IllegalArgumentException("Invalid user Id:" + id));
        }

        public User findByEmail (String email){
            return userRepository.findByEmail(email);
        }

    }
