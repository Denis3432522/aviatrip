package com.example.aviatrip.config;

import com.example.aviatrip.enumeration.Roles;
import com.example.aviatrip.model.Role;
import com.example.aviatrip.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class PreCommandsConfig {

    @Bean
    CommandLineRunner persistRoles(final RoleRepository roleRepository) {
        return args -> {
            for(Roles role : Roles.values()) {
                roleRepository.save(new Role(role));
            }
        };
    }
}
