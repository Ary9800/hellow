package com.firstp.hellow.DTO;

import lombok.*;
import org.hibernate.annotations.Check;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.validation.constraints.*;
@ToString(exclude = "password")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AdminDto {

    private  String name;
    private int age;
    private String employeeid;
    private String email;
    private String mobno;
    private String Address;
    private byte[] photo;
    private String password;

}





    // Constructor for email and password


