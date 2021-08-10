package ru.sharipov.spring.spring_boot_jm.EntityDTO;

import ru.sharipov.spring.spring_boot_jm.entity.Role;

public class RoleDTO {

    private String name;

    public RoleDTO(Role role) {
        this.name = role.getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
