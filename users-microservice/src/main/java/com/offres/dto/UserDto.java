package com.offres.dto;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import com.offres.enums.UserRole;

@Data
public class UserDto {

    private Long id;

    private String email;

    private String name;

    private UserRole role;

    private MultipartFile img;

    private byte[] returnedImg;

}
