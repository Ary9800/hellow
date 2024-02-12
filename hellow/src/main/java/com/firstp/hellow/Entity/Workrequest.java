package com.firstp.hellow.Entity;

import lombok.*;

import javax.persistence.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name="Workrequest")
public class Workrequest extends BaseEntity{
    @Column(name="messege",length = 100)
    private String messege;
    @ManyToOne
    @JoinColumn(name="worker_idsend")
    private Worker workersend;
    @ManyToOne
    @JoinColumn(name="customer_idrec")
    private Customer customerrec;
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne
    @JoinColumn(name = "work_id")
    private Work works;
}
