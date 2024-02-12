package com.firstp.hellow.CustomException;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AdminNotFoundException extends RuntimeException{

    public AdminNotFoundException(String error){
        super(error);
    }
}
