package com.offres.entity;


import java.util.Date;
import java.util.UUID;

import com.offres.dto.UserDto;
import com.offres.enums.UserRole;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {

	 @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    @Column(columnDefinition = "BINARY(16)")
	    private UUID id;

    private String email;

    private String password;

    private String name;
    private String societe;  

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate = new Date();
    private UserRole role;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] img;


    public UserDto getUserDto() {
        UserDto userDto = new UserDto();
        userDto.setId(id);
        userDto.setName(name);
        userDto.setEmail(email);
        userDto.setReturnedImg(img);
        userDto.setRole(role);
        return userDto;
    }
}
