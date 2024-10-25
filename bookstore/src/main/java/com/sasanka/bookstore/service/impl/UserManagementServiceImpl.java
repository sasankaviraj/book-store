package com.sasanka.bookstore.service.impl;

import com.sasanka.bookstore.auth.JwtUtil;
import com.sasanka.bookstore.dto.LoginDto;
import com.sasanka.bookstore.dto.UserDto;
import com.sasanka.bookstore.dto.response.ErrorDto;
import com.sasanka.bookstore.dto.response.SuccessDto;
import com.sasanka.bookstore.entity.Role;
import com.sasanka.bookstore.entity.User;
import com.sasanka.bookstore.exception.NotFoundException;
import com.sasanka.bookstore.repository.RoleRepository;
import com.sasanka.bookstore.repository.UserRepository;
import com.sasanka.bookstore.service.UserManagementService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import static com.sasanka.bookstore.util.Constants.SUCCESS_MSG;

@Service
public class UserManagementServiceImpl implements UserManagementService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public ResponseEntity<?> register(UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Set<Role> roleSet = new HashSet<>();
        userDto.getRoles().forEach(id -> {
            Role role = roleRepository.findById(id).orElseThrow(() -> new NotFoundException("Role not found"));
            roleSet.add(role);
        });
        user.setRoles(roleSet);
        userRepository.save(user);
        return new ResponseEntity<>(new SuccessDto(SUCCESS_MSG), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> login(LoginDto loginDto) {
        Authentication authenticate = authenticationManager.authenticate
                (new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        if (authenticate.isAuthenticated()){
            String token = jwtUtil.generateToken(loginDto.getUsername());
            return new ResponseEntity<>(new SuccessDto(token), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ErrorDto(HttpStatus.UNAUTHORIZED.value(), "Unauthorized"), HttpStatus.OK);
    }
}
