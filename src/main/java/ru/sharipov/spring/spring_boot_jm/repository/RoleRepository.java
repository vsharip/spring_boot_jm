package ru.sharipov.spring.spring_boot_jm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sharipov.spring.spring_boot_jm.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
