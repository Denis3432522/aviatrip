package com.example.aviatrip.service;

import com.example.aviatrip.config.exception.ValueEqualsToPreviousValueException;
import com.example.aviatrip.config.exception.ValueNotUniqueException;
import com.example.aviatrip.enumeration.Roles;
import com.example.aviatrip.model.Role;
import com.example.aviatrip.model.User;
import com.example.aviatrip.repository.RoleRepository;
import com.example.aviatrip.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CustomerService customerService;
    private final RepresentativeService representativeService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, CustomerService customerService, RepresentativeService representativeService, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.customerService = customerService;
        this.representativeService = representativeService;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    private User saveUser(User user, Roles role) {
        assertEmailUniqueness(user.getEmail());

        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        user.setRole(roleRepository.findByName(role).orElseThrow());

        return userRepository.save(user);
    }

    @Transactional
    public User saveCustomer(User user) {
        User persistedUser = saveUser(user, Roles.ROLE_CUSTOMER);
        customerService.createCustomer(persistedUser);

        return persistedUser;
    }

    @Transactional
    public User saveRepresentative(User user, String companyName) {
        representativeService.assertCompanyNameUniqueness(companyName);
        User persistedUser = saveUser(user, Roles.ROLE_REPRESENTATIVE);
        representativeService.createRepresentativeAndCompany(persistedUser, companyName);

        return persistedUser;
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

        assertEmailUniqueness(email);

        user.setEmail(email);
        userRepository.save(user);
    }

    public void assertEmailUniqueness(String email) {
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
