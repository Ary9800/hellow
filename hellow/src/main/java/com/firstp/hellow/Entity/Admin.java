package com.firstp.hellow.Entity;

import lombok.*;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Admin")
public class Admin extends BaseEntity{

    @Column(name="name",length = 20)
    private  String name;
    @Column(name="age", nullable = false)
    private int age;
    @Column(name = "employeeid",unique = true)
    private String employeeid;
    @Column(name="email",length=50, nullable = false, unique = true)
    private String email;
    @Column(name="mobno",nullable = false)
    private String mobno;
    @Column(name="Address",length=10, nullable = false)
    private String Address;
    @Lob
    @Column(name="photo")
    private byte[] photo;
    @Column(name="password",length=20)
    @Check(constraints = "CHAR_LENGTH(password)>=8")
    private String password;
}
