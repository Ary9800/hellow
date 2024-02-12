package com.firstp.hellow.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Worker")
public class Worker extends BaseEntity{



    @Column(name="name",length=20,nullable = false)
private String name;
    @Column(name="email",length=50,nullable = false,unique = true)
private String email;
    @Column(name="age",nullable = false)
    @Check(constraints = "age>=18 and age<=60")
private int age;
    @Column(name="mobno",length=10, nullable = false,unique = true)
private String mobno;
    @Column(name="profession",length=50,nullable = false)
private String profession;
    @Column(name="rate",nullable = false)
private int rate;
    @Column(name="aadharno",precision = 12,scale = 0,nullable = false,unique = true)
    @Check(constraints = "aadharno >= 100000000000 AND aadharno <= 999999999999")
private long aadharno;
    @Column(name="address",length=100, nullable = false)
private String address;
    @Column(name="available",nullable = false)
    @Enumerated(EnumType.STRING)
private Availenum available;
    @Column(name="photo")
    @Lob
private byte[] photo;
    @Column(name="password",length=20)
    @Check(constraints = "CHAR_LENGTH(password)>8")
    private String password;
    @OneToMany(mappedBy = "workergiven",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Feedback>feedbackgiven;
    @OneToMany(mappedBy = "workerrec", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Feedback>feedbackrecieved;
    @OneToMany(mappedBy = "workersend",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Workrequest>sendworkrequests;
    @OneToMany(mappedBy = "worker",cascade = CascadeType.REMOVE)
    private List<CustomerWorkerrequest>receivedrequest;
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Work>works;
    public void addfeedback(Feedback feedbacks){
        if(feedbackgiven==null){
            feedbackgiven=new ArrayList<>();
        }
        feedbacks.setWorkergiven(this);
        feedbackgiven.add(feedbacks);
    }
    public void addreceivedfeedback(Feedback feedbaack){
        if(feedbackrecieved==null){
            feedbackrecieved=new ArrayList<>();
        }
        feedbaack.setWorkerrec(this);
        feedbackrecieved.add(feedbaack);
    }
    public void removereceivedfeedback(Feedback feedbacks1){
        if(feedbackrecieved==null){
            feedbackrecieved=new ArrayList<>();
        }
        feedbacks1.setWorkerrec(null);
        feedbackrecieved.remove(feedbacks1);
    }

    public void removefeedback(Feedback feedbacks1){
        if(feedbackgiven==null){
            feedbackgiven=new ArrayList<>();
        }
        feedbacks1.setWorkergiven(null);
      feedbackgiven.remove(feedbacks1);
    }

    public void sendrequest(Workrequest workrequest){
        if(sendworkrequests==null){
           sendworkrequests=new ArrayList<>();
        }
        workrequest.setWorkersend(this);
        sendworkrequests.add(workrequest);
    }
    public void acceptrequest(CustomerWorkerrequest customerworkrequest){
        if(receivedrequest==null){
            receivedrequest=new ArrayList<>();
        }
        customerworkrequest.setWorker(this);
       receivedrequest.add(customerworkrequest);
    }

    public void addwork(Work work){
        if(works==null){
            works=new ArrayList<>();
        }
        work.getWorkers().add(this);
        works.add(work);
    }

    public void removework(Work work){
        if(works==null){
            works=new ArrayList<>();
        }
        work.getWorkers().remove(this);
        works.remove(work);
    }

}
