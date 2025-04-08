package com.att.tdp.popcorn_palace.service.impl;

import com.att.tdp.popcorn_palace.dto.request.UserRequest;
import com.att.tdp.popcorn_palace.dto.response.UserResponse;
import com.att.tdp.popcorn_palace.entity.User;
import com.att.tdp.popcorn_palace.repository.UserRepository;
import com.att.tdp.popcorn_palace.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    public void setup() {
        userRepository = mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    public void testCreateUser() {
        UserRequest request = new UserRequest("Alice", "alice@example.com");
        User savedUser = User.builder()
                .id(UUID.randomUUID())
                .name(request.getName())
                .email(request.getEmail())
                .build();

        when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(savedUser);

        UserResponse response = userService.createUser(request);

        assertEquals("Alice", response.getName());
        assertEquals("alice@example.com", response.getEmail());
        verify(userRepository).save(any(User.class));
    }

    @Test
    public void testGetAllUsers() {
        User user = User.builder()
                .id(UUID.randomUUID())
                .name("Bob")
                .email("bob@example.com")
                .build();

        when(userRepository.findAll()).thenReturn(List.of(user));

        List<UserResponse> users = userService.getAllUsers();

        assertEquals(1, users.size());
        assertEquals("Bob", users.get(0).getName());
    }

    @Test
    public void testGetUserById() {
        UUID userId = UUID.randomUUID();
        User user = User.builder()
                .id(userId)
                .name("Charlie")
                .email("charlie@example.com")
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        UserResponse response = userService.getUserById(userId);

        assertEquals(userId, response.getId());
        assertEquals("Charlie", response.getName());
    }

    @Test
    public void testGetUserById_NotFound() {
        UUID userId = UUID.randomUUID();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.getUserById(userId);
        });

        assertTrue(exception.getMessage().contains("User not found with id"));
    }
}
