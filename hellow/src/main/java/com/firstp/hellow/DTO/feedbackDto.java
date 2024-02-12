package com.firstp.hellow.DTO;

import com.firstp.hellow.Entity.Rolenum;
import lombok.*;
import lombok.experimental.Accessors;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class feedbackDto {

    private String name;
    private int rating;
    private String description;
    private Rolenum role;
   




}
