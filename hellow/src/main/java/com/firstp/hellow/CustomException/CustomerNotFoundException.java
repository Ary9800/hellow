package com.firstp.hellow.CustomException;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CustomerNotFoundException extends RuntimeException{
private String error;
public CustomerNotFoundException(String error){
    super(error);;
}
}
