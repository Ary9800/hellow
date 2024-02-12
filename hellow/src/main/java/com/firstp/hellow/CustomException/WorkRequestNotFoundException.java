package com.firstp.hellow.CustomException;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class WorkRequestNotFoundException extends RuntimeException{

    public WorkRequestNotFoundException(String error){
        super(error);
    }
}
