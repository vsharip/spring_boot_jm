package ru.sharipov.spring.spring_boot_jm.EntityDTO;

import org.springframework.stereotype.Component;
import ru.sharipov.spring.spring_boot_jm.entity.Role;


@Component
public class RoleMapper {

    public RoleDTO toRoleDto(Role role) {
        RoleDTO roleDTO = new RoleDTO(role);
        roleDTO.setName(role.getName());
        return roleDTO;
    }

    public Role toRole(RoleDTO roleDTO) {
        Role role = new Role();
        role.setName(roleDTO.getName());
        return role;

    }

}
