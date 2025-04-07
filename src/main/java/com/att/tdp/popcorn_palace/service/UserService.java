package com.att.tdp.popcorn_palace.service;

import com.att.tdp.popcorn_palace.dto.request.UserRequest;
import com.att.tdp.popcorn_palace.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserRequest request);
    List<UserResponse> getAllUsers();
    UserResponse getUserById(Long id);
}
