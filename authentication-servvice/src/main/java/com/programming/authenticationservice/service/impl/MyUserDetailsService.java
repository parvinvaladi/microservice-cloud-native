package com.programming.authenticationservice.service.impl;


import com.programming.authenticationservice.domain.Role;
import com.programming.authenticationservice.domain.User;
import com.programming.authenticationservice.dto.ResponseMessageDto;
import com.programming.authenticationservice.dto.request.RegisterRequestDto;
import com.programming.authenticationservice.repo.RoleRepository;
import com.programming.authenticationservice.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MyUserDetailsService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException("User not found");
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        return new org.springframework.security.core.userdetails.User(username,user.getPassword(),user.isEnabled(),accountNonExpired,credentialsNonExpired,accountNonLocked,getAuthorities(user.getRoles()));
    }

    private static List<GrantedAuthority> getAuthorities (Set<Role> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

    public ResponseEntity<ResponseMessageDto> register(RegisterRequestDto requestDto){
        Optional<Role> optionalRole = roleRepository.findByName("user");
        if (optionalRole.isEmpty())
            return (ResponseEntity<ResponseMessageDto>) ResponseEntity.notFound();
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
