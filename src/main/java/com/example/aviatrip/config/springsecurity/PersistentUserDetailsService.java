package com.example.aviatrip.config.springsecurity;

import com.example.aviatrip.model.entity.User;
import com.example.aviatrip.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersistentUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    PersistentUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new BadCredentialsException("incorrect email or password"));

        return new UserDetailsImpl(user.getId()+"", user.getPassword(), retrieveAuthorities(user));
    }

    public List<? extends GrantedAuthority> retrieveAuthorities(User user) {
        return (user.getRole() != null) ? List.of(new SimpleGrantedAuthority(user.getRole().getName().name())) : null;
    }
}
