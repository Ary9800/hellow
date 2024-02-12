package com.firstp.hellow.Entity;

import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Embeddable
public class FeedbackEmbed {
    private String name;
    private int rating;
    private String description;
    @Enumerated(EnumType.STRING)
    private Rolenum role;
}
