package com.firstp.hellow.Entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor



@Entity
@Table(name = "Work")
public class Work extends BaseEntity {
    @Column(name="name",length=20, nullable = false)
    private String workname;
    @Column(name="description",length=100, nullable = false)
    private String description;
    @Column(name="Sdate",nullable = false)
private LocalDate startdate;
    @Column(name="edate",nullable = false)
    private LocalDate enddate;
    @Column(name = "field",length = 50,nullable = false)
    private String feild;
    @Column(name="duration",nullable = false)
    private int duration;
    @Column(name="rate",nullable = false)
    private int Rate;
    @Column(name="address",length=50, nullable = false)
    private String address;
    @Enumerated(EnumType.STRING)
    private StatusforWork Status;
    @OneToMany(mappedBy = "work",cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Feedback> feedback;
    @ManyToOne
    @JoinColumn(name="customerid", nullable = false )
    private Customer customer;
@ManyToMany(mappedBy = "works")
    private List<Worker>workers;
public void addfeedback(Feedback feedback1){
    if(feedback==null){
        feedback=new ArrayList<>();
    }
    feedback1.setWork(this);
    feedback.add(feedback1);
}
    public void removefeedback(Feedback feedbacks1) {
        if (feedback == null) {
            feedback = new ArrayList<>();
        }
        feedbacks1.setWork(null);
        feedback.remove(feedbacks1);
    }
public void addworker(Worker worker){
    if(workers==null){
        workers=new ArrayList<>();
    }
    worker.getWorks().add(this);
    workers.add(worker);
}
    public void removeworker(Worker worker){
        if(workers==null){
            workers=new ArrayList<>();
        }
        worker.getWorks().remove(this);
        workers.remove(worker);
    }
}
