package com.programming.authenticationservice.service.impl;

import com.programming.authenticationservice.domain.Role;
import com.programming.authenticationservice.domain.User;
import com.programming.authenticationservice.dto.ResponseMessageDto;
import com.programming.authenticationservice.dto.request.RegisterRequestDto;
import com.programming.authenticationservice.repo.RoleRepository;
import com.programming.authenticationservice.repo.UserRepository;
import com.programming.authenticationservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserServiceImpl(RoleRepository roleRepository, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<ResponseMessageDto> register(RegisterRequestDto requestDto){
        Optional<Role> optionalRole = roleRepository.findByName("user");
        if (optionalRole.isEmpty())
            return (ResponseEntity<ResponseMessageDto>) ResponseEntity.notFound();

        Optional<User> userOptional = userRepository.findByUsername(requestDto.userName());
        if (userOptional.isPresent())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseMessageDto.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("duplicate username")
                    .build());

        User user = User.builder()
                .username(requestDto.userName())
                .password(passwordEncoder.encode(requestDto.password()))
                .enabled(true)
                .roles(Set.of(optionalRole.get()))
                .build();
        userRepository.save(user);
        return ResponseEntity.ok(ResponseMessageDto.builder().build());
    }

}
