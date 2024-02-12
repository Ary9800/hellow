package com.firstp.hellow.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Temporaryworkfeedbacktable")
public class Temporaryworkfeedbacktable extends BaseEntity{
    private int workerid;
    @OneToOne
    @JoinColumn(name="work_id")
    private Work work;
    @Column(name="feedback")
    @Embedded
    private FeedbackEmbed feedback;
}
