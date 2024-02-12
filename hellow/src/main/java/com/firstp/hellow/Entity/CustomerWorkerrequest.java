package com.firstp.hellow.Entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name=" CustomerWorkerrequest")
public class CustomerWorkerrequest extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "customer_send")
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "workerreceive")
    private Worker worker;
    @Enumerated(value = EnumType.STRING)
    private Status status;
    @Column(name="messege", length=100)
    private String messege;
}
