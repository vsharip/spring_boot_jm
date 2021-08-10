package ru.sharipov.spring.spring_boot_jm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sharipov.spring.spring_boot_jm.EntityDTO.UserDTO;
import ru.sharipov.spring.spring_boot_jm.EntityDTO.UserMapper;
import ru.sharipov.spring.spring_boot_jm.entity.Role;
import ru.sharipov.spring.spring_boot_jm.entity.User;
import ru.sharipov.spring.spring_boot_jm.exception_handling.NoSuchUserException;
import ru.sharipov.spring.spring_boot_jm.repository.RoleRepository;
import ru.sharipov.spring.spring_boot_jm.repository.UserRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    PasswordEncoder passwordEncoderNotPassword;

    @Autowired
    UserMapper userMapper;

    public List<UserDTO> getAllUsers() {
        List<User> userList = (List<User>) userRepository.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();
        userList.stream().map(user -> userMapper.toDto(user))
                .forEach(user -> userDTOList.add(user));
//        userDTOList.forEach(System.out::println);
        return userDTOList;
    }


    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchUserException("There is no user with ID = " + id + " in DataBase"));

    }

    public UserDTO addUser(User user) {
        Set<Role> roles = new HashSet<>();
        if (user.getRoles() != null) {
            user.getRoles().stream().forEach(role -> {
                        if (role.getName().equals("ADMIN")) {
                            roles.add(roleRepository.getById(1L));
                        } else if (role.getName().equals("USER")) {
                            roles.add(roleRepository.getById(2L));

                        }
                    }
            );
        }
        String encryptPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptPassword);
        user.setRoles(roles);
        User user1 = userRepository.save(user);
        return userMapper.toDto(user1);
    }


    public void deleteById(User user) {
        userRepository.delete(user);
    }


    public UserDTO findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return userMapper.toDto(user);
    }
}
