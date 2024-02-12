package com.firstp.hellow.Service;

import com.firstp.hellow.CustomException.*;
import com.firstp.hellow.DTO.*;
import com.firstp.hellow.Entity.Admin;
import com.firstp.hellow.Entity.Customer;
import com.firstp.hellow.Entity.Feedback;
import com.firstp.hellow.Entity.Worker;
import com.firstp.hellow.Repository.AdminRepository;
import com.firstp.hellow.Repository.CustomerRepo;
import com.firstp.hellow.Repository.FeedbackRepo;
import com.firstp.hellow.Repository.WorkerRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
@Transactional
public class AdminServiceImpl implements AdminServie {
    @Autowired
    private AdminRepository adrepo;
    @Autowired
    private FeedbackRepo fdrepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private WorkerRepo wrepo;
    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private WorkerRepo workrepo;



    @Override
    public AdminDto loginadmin( @NotNull AdminDto  logindetails) {
        String adminemployeeid = logindetails.getEmployeeid();
        String adminemail = logindetails.getEmail();
        String password = logindetails.getPassword();
        String dbpassword="";
        Admin adminobj=null;
        System.out.println(adminemployeeid+" "+adminemail+password);
        if(adminemail!=null){
            dbpassword=adrepo.findPasswordByEmail(adminemail).orElseThrow(()->new AdminNotFoundException("no amdin"));
            adminobj=adrepo.findByEmail(adminemail);
            System.out.println(adminobj);
        }
        else if(adminemployeeid!=null){
            dbpassword=adrepo.findPasswordByEmployeeid(adminemployeeid).orElseThrow(()->new AdminNotFoundException("no amdin"));
            adminobj=adrepo.findByEmployeeid(adminemployeeid);
            System.out.println(adminobj);
        }
        System.out.println(dbpassword);
            if (dbpassword!=null ){
                if (dbpassword.equals(password)){

                    return modelMapper.map(adminobj,AdminDto.class);

            } else
                throw new PasswordNotMatch("password not correct");

    }
    else throw  new AdminNotFoundException("no admin");

    }



    @Override
    public List<feedbackDto> ccustgivenfeedback(int customerid) {
        List<Feedback> fb = fdrepo.findgivenBycustomerId(customerid);
        if (!fb.isEmpty()) {
            return fb.stream().map(feedbackdto->modelMapper.map(feedbackdto,feedbackDto.class)).collect(Collectors.toList());
        }
        else throw new FeedbackNotFoundException("no feedback given by customer ");
    }



    @Override
    public List<feedbackDto> cworkergivenfeedback(int workerid) {
        List<Feedback> fb = fdrepo.findgivenByworkerid(workerid);
        if (!fb.isEmpty()) {
            return fb.stream().map(feedbackdto -> modelMapper.map(feedbackdto, feedbackDto.class)).collect(Collectors.toList());
        }
        else throw new FeedbackNotFoundException("non feedback given by worker");
    }



    @Override
    public WorkerDto workerprofile(int workerid) {
        Worker worker = wrepo.findById(workerid).orElseThrow(()->new WorkerNotFoundException("no worker found with this id"));
        WorkerDto workdto = modelMapper.map(worker, WorkerDto.class);
            return workdto;
    }



    @Override
    public CustomerDto customerprofile(int customerid) {
        Customer customer = customerRepo.findById(customerid).orElseThrow(()->new CustomerNotFoundException("no customer found with this id"));
            CustomerDto custodto = modelMapper.map(customer, CustomerDto.class);
            return custodto;
    }



    @Override
    public Boolean deletecustomerprofile(int customerid) {
        if (customerRepo.existsById(customerid)) {
            customerRepo.deleteById(customerid);
            return true;
        } else
         throw new CustomerNotFoundException("no customer exists with this id");
    }



    @Override
    public Boolean deleteworerrofile(int workerid) {
        if (workrepo.existsById (workerid)) {
            workrepo.deleteById(workerid);
            return true;
        } else
            throw new WorkerNotFoundException("no worker exists with this id");
    }



    @Override
    public List<WorkerDto> allworkerprofile() {
        List<Worker> worker = wrepo.findAll();
        if (!worker.isEmpty()) {
           return worker.stream().
                    map(worker1->modelMapper.map(worker1,WorkerDto.class)).
                    collect(Collectors.toList());
        } else
            throw new WorkerNotFoundException("no worker found");
    }



    @Override
    public List<CustomerDto> allcustomerprofile() {
        List<Customer> customer = customerRepo.findAll();
        if (!customer.isEmpty()) {
            return customer.stream().
                    map(customer1->modelMapper.map(customer1,CustomerDto.class)).
                    collect(Collectors.toList());
        } else
            throw new CustomerNotFoundException("no customer found");
    }



    @Override
    public feedbackDto customergivenfeedbackbyid(int customerid, int feedbackid) {
        List<Feedback> customerfeedbcak = fdrepo.findgivenBycustomerId(customerid);
        if (!customerfeedbcak.isEmpty()) {
            feedbackDto feedbackdto = customerfeedbcak.stream().filter(customerfeedbackid->customerfeedbackid.getId()==feedbackid).findAny().map(customerfeedback1->modelMapper.map(customerfeedback1, feedbackDto.class)).orElseThrow(()->new FeedbackNotFoundException("no feedback found with this id"));
            return feedbackdto;
        } else

            throw new FeedbackNotFoundException("no feedback given by customer");

    }



    @Override
    public feedbackDto workerreceivedfeedbackbyid(int workerid, int feedbackid) {
         List<Feedback> workerfeedbcak = fdrepo.findreceivedByworkerid(workerid);
        if (!workerfeedbcak.isEmpty()) {
            feedbackDto feedbackdto = workerfeedbcak.stream().filter(workerfeedbackid->workerfeedbackid.getId()==feedbackid).findAny().map(workerfeedback1->modelMapper.map(workerfeedback1, feedbackDto.class)).orElseThrow(()->new FeedbackNotFoundException("no feedback found with this id"));
            return feedbackdto;
        } else
            throw new FeedbackNotFoundException("no feedback given by worker");
    }



    @Override
    public feedbackDto workergivenfeedbackbyid(int workerid, int feedbackid) {

        List<Feedback> workerfeedbcak = fdrepo.findgivenByworkerid(workerid);
        if (!workerfeedbcak.isEmpty()) {
            feedbackDto feedbackdto = workerfeedbcak.stream().filter(workerfeedbackid->workerfeedbackid.getId()==feedbackid).findAny().map(workerfeedback1->modelMapper.map(workerfeedback1, feedbackDto.class)).orElseThrow(()->new FeedbackNotFoundException("no feedback found with this id"));
            return feedbackdto;
        } else

            throw new FeedbackNotFoundException("no feedbacks found");
    }



    @Override
    public List<feedbackDto> ccustreceivedfeedback(int customerid) {

        List<Feedback> fb = fdrepo.findreceivedBycustomerId(customerid);
        if (!fb.isEmpty()) {
            return fb.stream().map(feedbackdto->modelMapper.map(feedbackdto,feedbackDto.class)).collect(Collectors.toList());
        }else
        throw new FeedbackNotFoundException("no feedback found");
    }



    @Override
    public List<feedbackDto> cworkerreceivedfeedback(int workerid) {
        List<Feedback> fb = fdrepo.findreceivedByworkerid(workerid);
        if (!fb.isEmpty()) {
            return fb.stream().map(feedbackdto -> modelMapper.map(feedbackdto, feedbackDto.class)).collect(Collectors.toList());
        }
        throw new FeedbackNotFoundException("no feedback found");
    }



    @Override
    public feedbackDto customerreceivedfeedbackbyid(int customerid, int feedbackid) {
        List<Feedback> customerfeedbcak = fdrepo.findreceivedBycustomerId(customerid);
        if (!customerfeedbcak.isEmpty()) {
            feedbackDto feedbackdto = customerfeedbcak.stream().filter(customerfeedbackid->customerfeedbackid.getId()==feedbackid).findAny().map(customerfeedback1->modelMapper.map(customerfeedback1, feedbackDto.class)).orElseThrow(()->new FeedbackNotFoundException("no feedback found with this id"));
            return feedbackdto;
        } else

            throw new FeedbackNotFoundException("no feedbacks found");
    }}



