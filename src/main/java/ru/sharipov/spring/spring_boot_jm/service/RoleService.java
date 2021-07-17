package ru.sharipov.spring.spring_boot_jm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sharipov.spring.spring_boot_jm.entity.Role;
import ru.sharipov.spring.spring_boot_jm.repository.RoleRepository;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
    public Role getRoleByName(String roleName) {
        return roleRepository.findByName(roleName);
    }
}
