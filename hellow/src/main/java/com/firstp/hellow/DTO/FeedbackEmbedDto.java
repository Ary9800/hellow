package com.firstp.hellow.DTO;

import com.firstp.hellow.Entity.Rolenum;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FeedbackEmbedDto {
    private String name;
    private int rating;
    private String description;
    private Rolenum role;
}
