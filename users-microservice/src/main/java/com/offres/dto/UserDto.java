package com.offres.dto;


import lombok.Data;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.offres.enums.UserRole;

@Data
public class UserDto {

    private UUID id;

    private String email;

    private String name;

    private UserRole role;

    private MultipartFile img;

    private byte[] returnedImg;

}
