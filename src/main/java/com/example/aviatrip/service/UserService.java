package com.example.aviatrip.service;

import com.example.aviatrip.config.exception.ValueEqualsToPreviousValueException;
import com.example.aviatrip.config.exception.ValueNotUniqueException;
import com.example.aviatrip.model.Role;
import com.example.aviatrip.model.User;
import com.example.aviatrip.repository.RoleRepository;
import com.example.aviatrip.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public void createUser(User user) {
        checkEmailUniqueness(user.getEmail());

        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        userRepository.save(user);
    }

    public User retrieveUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(RuntimeException::new);
    }

    public void updatePassword(String rawPassword, long userId) {
        User user = userRepository.findById(userId).orElseThrow();

        if(passwordEncoder.matches(rawPassword, user.getPassword()))
            throw new ValueEqualsToPreviousValueException("password", true);

        user.setPassword(passwordEncoder.encode(rawPassword));
        userRepository.save(user);
    }

    public void updateName(String name, long userId) {
        User user = userRepository.findById(userId).orElseThrow();

        if(user.getName().equals(name))
            throw new ValueEqualsToPreviousValueException("name", true);

        user.setName(name);
        userRepository.save(user);
    }

    public void updateSurname(String surname, long userId) {
        User user = userRepository.findById(userId).orElseThrow();

        if(user.getSurname().equals(surname))
            throw new ValueEqualsToPreviousValueException("name", true);

        user.setSurname(surname);
        userRepository.save(user);
    }

    public void updateEmail(String email, long userId) {
        User user = userRepository.findById(userId).orElseThrow();

        if(user.getEmail().equals(email))
            throw new ValueEqualsToPreviousValueException("email", true);

        checkEmailUniqueness(email);

        user.setEmail(email);
        userRepository.save(user);
    }

    public void checkEmailUniqueness(String email) {
        if(userRepository.existsByEmail(email))
            throw new ValueNotUniqueException("email", true);
    }

    public List<? extends GrantedAuthority> retrieveAuthorities(User user) {
        Role role = user.getRole();

        return (role != null) ? List.of(new SimpleGrantedAuthority(role.getName().name())) : null;
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
