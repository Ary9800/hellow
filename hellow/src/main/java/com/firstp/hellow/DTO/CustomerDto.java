package com.firstp.hellow.DTO;

import lombok.*;

@ToString(exclude = "password")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {
private int id;
private String name;
private String mobno;
private int age;
private String Address;
private String email;
private byte[]photo;
private     long aadharnumber;
private String password;

}
