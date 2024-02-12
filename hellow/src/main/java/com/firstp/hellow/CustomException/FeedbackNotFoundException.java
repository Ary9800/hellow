package com.firstp.hellow.CustomException;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class FeedbackNotFoundException extends RuntimeException{

    public FeedbackNotFoundException(String error){
        super(error);;
    }
}
