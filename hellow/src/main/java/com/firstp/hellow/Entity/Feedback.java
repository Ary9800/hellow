package com.firstp.hellow.Entity;

import lombok.*;
import org.hibernate.annotations.Check;

import javax.persistence.*;
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Feedback")
public class Feedback extends BaseEntity{
   @Column(name="name",length=20)
    private String name;
    @Column(name="rating")
    @Check(constraints = "1<=rating<=5")
    private int Rating;
    @Column(name="description",length=100)

    private String description;
 @Column(name="feedbackrole", nullable = false)
 @Enumerated(EnumType.STRING)
    private Rolenum role;
 @ManyToOne
 @JoinColumn(name = "work_id")
 private Work work;
 @ManyToOne
 @JoinColumn(name = "giveenbycustomer")
 private Customer customergiven;
 @ManyToOne
 @JoinColumn(name="givenbyworker")
 private Worker workergiven;
 @ManyToOne
 @JoinColumn(name="giventocustomer")
 private Customer customerrec;
 @ManyToOne
 @JoinColumn(name="giventoworker")
 private Worker workerrec;
}
