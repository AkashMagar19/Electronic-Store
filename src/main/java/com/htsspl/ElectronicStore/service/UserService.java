package com.htsspl.ElectronicStore.service;

import com.htsspl.ElectronicStore.dtos.PageableResponse;
import com.htsspl.ElectronicStore.dtos.UserDto;

import java.util.List;

public interface UserService {

    // Create
    UserDto createUser (UserDto userDto);
    // Update
    UserDto updateUser (UserDto userDto, Long userId);

    // Delete
    void deleteUser(Long userId);

    // Get All User
    PageableResponse<UserDto> getAllUser(Integer pageNumber , Integer pageSize , String sortBy , String sortDir);
    // Get User by id
    UserDto getUserById(Long userId);
    // Get User By email
    UserDto getUserByEmail(String email);

    //Search User
    List<UserDto> searching(String keyword);

}
