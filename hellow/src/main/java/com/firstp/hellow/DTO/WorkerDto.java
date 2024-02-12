package com.firstp.hellow.DTO;

import com.firstp.hellow.Entity.Availenum;
import lombok.*;

@ToString(exclude = "password")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkerDto {
    private String name;
    private String mobno;
    private int age;
    private String Address;
    private String email;
    private byte[]photo;
    private  long aadharno;
    private int rate;
    private String profession;
    private Availenum available;
    private String password;
private String messege;

}

