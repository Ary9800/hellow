package com.firstp.hellow.CustomException;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class WorkerNotFoundException extends RuntimeException{

    public WorkerNotFoundException(String error){
        super(error);
    }
}
