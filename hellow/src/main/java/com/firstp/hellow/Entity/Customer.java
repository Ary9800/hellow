package com.firstp.hellow.Entity;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Customer")
public class Customer extends BaseEntity {


@Column(name="name",length=20,nullable = false )
private String name;
    @Column(name="age", nullable = false)
private int age;
    @Column(name="email",length=50, nullable = false, unique = true)
private String email;
    @Column(name="mobno",nullable = false)
private String mobno;
    @Column(name="Address",length=100, nullable = false)
private String Address;
    @Lob
    @Column(name="photo")
    private byte[] photo;
    @Column(name="password",length=20)
    @Check(constraints = "CHAR_LENGTH(password)>8")
    private String password;
    @Column(name="aadharnumber",length=20, nullable = false, unique = true)
    @Check(constraints = "CHAR_LENGTH(aadharnumber)=12")
    private long Adharnumber;
    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    private List<Work> customerwork;
    @OneToMany(mappedBy = "customergiven",cascade = CascadeType.ALL,fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Feedback>feedbacks;
    @OneToMany(mappedBy = "customerrec",orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<Feedback>receivedfeedbacks;
    @OneToMany(mappedBy = "customerrec",orphanRemoval = true,cascade = CascadeType.REMOVE)
    private List<Workrequest> receivedworkrequest;
    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<CustomerWorkerrequest>sendrequest;
    public void addwork(Work works){
        if(customerwork==null){
            customerwork=new ArrayList<>();
        }
        works.setCustomer(this);
        customerwork.add(works);
    }
    public void addfeedback(Feedback feedbacks1){
        if(feedbacks==null){
            feedbacks=new ArrayList<>();
        }
        feedbacks1.setCustomergiven(this);
        feedbacks.add(feedbacks1);
    }
    public void addrecievedfeedback(Feedback feedback){
        if(receivedfeedbacks==null){
            receivedfeedbacks=new ArrayList<>();
        }
        feedback.setCustomerrec(this);
        receivedfeedbacks.add(feedback)  ;
    }
    public void acceptrequest(Workrequest workrequest){
        if(receivedworkrequest==null){
            receivedworkrequest=new ArrayList<>();
        }
        workrequest.setCustomerrec(this);
        receivedworkrequest.add(workrequest);
    }
    public void sendrequest(CustomerWorkerrequest customerworkrequest){
        if(sendrequest==null){
            sendrequest=new ArrayList<>();
        }
        customerworkrequest.setCustomer(this);
        sendrequest.add(customerworkrequest);
    }
    public void removefeedback(Feedback feedbacks1){
        if(feedbacks==null){
            feedbacks=new ArrayList<>();
        }
        feedbacks1.setCustomergiven(null);
        feedbacks.remove(feedbacks1);
    }

}