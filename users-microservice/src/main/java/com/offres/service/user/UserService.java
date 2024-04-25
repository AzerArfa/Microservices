package com.offres.service.user;


import org.springframework.http.ResponseEntity;

import com.offres.dto.ChangePasswordDto;
import com.offres.dto.SignupRequest;
import com.offres.dto.UserDto;
import com.offres.entity.User;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface UserService {

     UserDto createUser(SignupRequest signupRequest) throws Exception;

     Boolean hasUserWithEmail(String email);

     UserDto getUserById(UUID userId);
     public boolean checkIfPasswordNeedsUpdate(User user);
     UserDto updateUser(UserDto userDto) throws IOException;
     List<UserDto> getAllUsers(); 
    ResponseEntity<?> updatePasswordById(ChangePasswordDto changePasswordDto);
}
