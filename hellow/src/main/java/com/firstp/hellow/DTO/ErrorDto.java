package com.firstp.hellow.DTO;

import lombok.*;

import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ErrorDto {
     private String messege;
     public ErrorDto setMessege(String messege){
          this.messege=messege;
                  return this;
     }
}
