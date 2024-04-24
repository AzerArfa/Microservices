package com.offres.dto;


import java.util.Date;

import lombok.Data;

@Data
public class SignupRequest {

    private String email;

    private String password;

    private String name;
    
    private String societe;
    
    private Date creationDate;

}
