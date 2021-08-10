package ru.sharipov.spring.spring_boot_jm.EntityDTO;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.sharipov.spring.spring_boot_jm.entity.User;

@Component
public class UserMapper {

    public UserDTO toDto(User user) {
        UserDTO userDTO = new UserDTO(user);
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setAge(user.getAge());
        userDTO.setEmail(user.getEmail());
        userDTO.setRoles(user.getRoles());
        return userDTO;
    }

    public User toUser(UserDTO userDTO) {
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setAge(userDTO.getAge());
        user.setEmail(userDTO.getEmail());
        user.setRoles(userDTO.getRoles());
        return user;
    }
}
