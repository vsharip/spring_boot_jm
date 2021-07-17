package ru.sharipov.spring.spring_boot_jm.repository;


import org.springframework.data.repository.CrudRepository;
import ru.sharipov.spring.spring_boot_jm.entity.User;


public interface UserRepository extends CrudRepository<User, Long> {

    User findByEmail(String email);
}

