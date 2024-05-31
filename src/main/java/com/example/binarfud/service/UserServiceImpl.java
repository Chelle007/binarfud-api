package com.example.binarfud.service;

import com.example.binarfud.model.dto.user.UserCreateRequestDto;
import com.example.binarfud.model.dto.user.UserDto;
import com.example.binarfud.model.dto.user.UserUpdateRequestDto;
import com.example.binarfud.exception.*;
import com.example.binarfud.model.entity.account.Role;
import com.example.binarfud.model.entity.account.ERole;
import com.example.binarfud.model.entity.account.User;
import com.example.binarfud.repository.RoleRepository;
import com.example.binarfud.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired UserRepository userRepository;
    @Autowired RoleRepository roleRepository;
    @Autowired ModelMapper modelMapper;

    @Override
    public UserDto create(UserCreateRequestDto userCreateRequestDto) {
        User user = modelMapper.map(userCreateRequestDto, User.class);

        if (userRepository.existsByUsername(userCreateRequestDto.getUsername())) {
            log.error("Username existed: {}", user.getUsername());
            throw new UsernameExistedException("Username existed: " + user.getUsername());
        } else if (userRepository.existsByEmail(userCreateRequestDto.getEmail())) {
            log.error("Account with same email existed: {}", user.getEmail());
            throw new EmailExistedException("Account with same email existed: " + user.getEmail());
        }

        user = userRepository.save(user);
        log.info("User {} successfully added", user.getId());

        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public void createUserPostLogin(String name, String email) {
        Role role = roleRepository.findByName(ERole.ROLE_CUSTOMER);
        Set<Role> roles = new HashSet<>(Collections.singletonList(role));

        User user = getByUsername(email);
        if(user == null){
            user = User.builder()
                    .username(email)
                    .email(email)
                    .roles(roles)
                    .build();
            userRepository.save(user);
        }
    }

    @Override
    public User getById(UUID id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }

        log.error("User not found: {}", (id));
        throw new UserNotFoundException("User not found: " + id);
    }

    @Override
    public UserDto getDtoById(UUID id) {
        return modelMapper.map(getById(id), UserDto.class);
    }

    @Override
    public User getByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        return userOptional.orElse(null);
    }

    @Override
    public UserDto getByUsernameAndPassword(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, password);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> getList() {
        return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .toList();
    }

    @Override
    public UserDto update(UUID id, UserUpdateRequestDto userUpdateRequestDto) {
        User existingUser = getById(id);

        existingUser.setUsername(userUpdateRequestDto.getUsername());
        existingUser.setEmail(userUpdateRequestDto.getEmail());
        existingUser.setPassword(userUpdateRequestDto.getPassword());

        User updatedUser = userRepository.save(existingUser);
        return modelMapper.map(updatedUser, UserDto.class);
    }

    @Override
    public void safeDeleteById(UUID id) {
        User user = getById(id);
        user.setDeleted(true);
        userRepository.save(user);
        log.info("User {} has been deleted", id);
    }
}
