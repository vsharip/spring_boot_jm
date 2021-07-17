package ru.sharipov.spring.spring_boot_jm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sharipov.spring.spring_boot_jm.entity.Role;
import ru.sharipov.spring.spring_boot_jm.entity.User;
import ru.sharipov.spring.spring_boot_jm.repository.UserRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        System.out.println("Инициализация UserDetailsServiceImpl ");
        this.userRepository = userRepository;
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
        System.out.println("Инициализация setGrantedAuthorities ");
        return roles.stream()
                .map(s -> new SimpleGrantedAuthority(s.getName())).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        System.out.println("loadUserByUsername запустился");

        User user = userRepository.findByEmail(userName);
        if (user != null) {
            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(), user.getPassword(), getAuthorities(user.getRoles()));
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
