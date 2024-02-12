package com.firstp.hellow.Controller;

import com.firstp.hellow.CustomException.AdminNotFoundException;
import com.firstp.hellow.CustomException.EntitynotfoundException;
import com.firstp.hellow.CustomException.PasswordNotMatch;
import com.firstp.hellow.DTO.AdminDto;
import com.firstp.hellow.DTO.CustomerDto;
import com.firstp.hellow.DTO.ErrorDto;
import com.firstp.hellow.DTO.WorkerDto;
import com.firstp.hellow.DTO.feedbackDto;
import com.firstp.hellow.Service.AdminServie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminServie adminservice;



    @PostMapping ("/login")
    public ResponseEntity<?> login(@RequestBody AdminDto logindetails) {
        System.out.println(logindetails.toString());
        try{
       AdminDto result = adminservice.loginadmin(logindetails);
       return ResponseEntity.ok().body(result);
    }catch (PasswordNotMatch  |AdminNotFoundException ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }



    @GetMapping("/customergivenfeedback/{customerid}")
    public ResponseEntity<?> ccustgivenfeedback(@PathVariable int customerid) {
        try{
       List<feedbackDto> result = adminservice.ccustgivenfeedback(customerid);
        if (!result.isEmpty()) {
            return ResponseEntity.ok().body(result);
        } else
           throw new EntitynotfoundException("no feedback found");
    }catch (EntitynotfoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto().setMessege(ex.getMessage()));
        }}



    @GetMapping("/customerreceivedfeedback/{customerid}")
    public ResponseEntity<?> ccustreceivedfeedback(@PathVariable int customerid) {
        try{
        List<feedbackDto> result = adminservice.ccustreceivedfeedback(customerid);
        if (!result.isEmpty()) {
            return ResponseEntity.ok().body(result);
        } else
            throw new EntitynotfoundException("no feedbacks found");
    }catch (EntitynotfoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }}



    @GetMapping("/workergivenfeedback/{workerid}")
    public ResponseEntity<?> cworkergivenfeedback(@PathVariable int workerid) {
        try{
       List<feedbackDto> result = adminservice.cworkergivenfeedback(workerid);
        if (!result.isEmpty()) {
            return ResponseEntity.ok().body(result);
        } else
            throw new EntitynotfoundException("no feedbacks found");
    }catch(EntitynotfoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }}



    @GetMapping("/workerreceivedfeedback/{workerid}")
    public ResponseEntity<?> cworkerreceivedfeedback(@PathVariable int workerid) {
        try{
        List<feedbackDto> result = adminservice.cworkerreceivedfeedback(workerid);
        if (!result.isEmpty()) {
            return ResponseEntity.ok().body(result);
        }
        else
            throw new EntitynotfoundException("no feedbacks found");
        }catch(EntitynotfoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }}



    @GetMapping("/cutomergivenfeedbackbyid/{feedbackid}/{customerid}")
    public ResponseEntity<?> customergivenfeedbackbyid(@PathVariable int customerid,@PathVariable int feedbackid) {
        try {
            feedbackDto result = adminservice.customergivenfeedbackbyid(customerid, feedbackid);
            return ResponseEntity.ok().body(result);
        } catch (EntitynotfoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto().setMessege(ex.getMessage()));
        }}



    @GetMapping("/cutomerreceivedfeedbackbyid/{feedbackid}/{customerid}")
    public ResponseEntity<?> customerrecievedfeedbackbyid(@PathVariable int customerid,@PathVariable int feedbackid) {
        try {
            feedbackDto result = adminservice.customerreceivedfeedbackbyid(customerid, feedbackid);

            return ResponseEntity.ok().body(result);
        } catch (EntitynotfoundException ex) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }}



    @GetMapping("/workerreceivedfeedbackbyid/{workerid}/{feedbackid}")
    public ResponseEntity<?> workerfeedbackreceivedbyid(@PathVariable int workerid,@PathVariable int feedbackid) {
        try {
            feedbackDto result = adminservice.workerreceivedfeedbackbyid(workerid, feedbackid);

            return ResponseEntity.ok().body(result);
        } catch (EntitynotfoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }}



    @GetMapping("/workergivenfeedbackbyid/{workerid}/{feedbackid}")
    public ResponseEntity<?> workerfeedbackgivenbyid(@PathVariable int workerid,@PathVariable int feedbackid) {
        try{
       feedbackDto result = adminservice.workergivenfeedbackbyid(workerid,feedbackid);

            return ResponseEntity.ok().body(result);
        } catch (EntitynotfoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }}




    @GetMapping("/workerprofile/{workerid}")
    public ResponseEntity<?> workerprofile(@PathVariable int workerid) {
        try{
        WorkerDto result = adminservice.workerprofile(workerid);
        return ResponseEntity.ok().body(result);
        } catch(EntitynotfoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto().setMessege(ex.getMessage()));
    }}



    @GetMapping("/allworkerprofile")
    public ResponseEntity<?> allworkerprofile() {
        try{
        List<WorkerDto> result = adminservice.allworkerprofile();
        if (!result.isEmpty()) {
            return ResponseEntity.ok().body(result);
        } else {
            throw new EntitynotfoundException("no feedbacks found");
        }
        }catch(EntitynotfoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto().setMessege(ex.getMessage()));
        }}



    @GetMapping("/customerprofile/{customerid}")
    public ResponseEntity<?> customerprofile(@PathVariable int customerid) {
        try{
       CustomerDto result = adminservice.customerprofile(customerid);
            return ResponseEntity.ok().body(result);
        } catch (EntitynotfoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }}



    @GetMapping("/allcustomerprofile")
    public ResponseEntity<?> allcustomerprofile() {
        try{
        List<CustomerDto> result = adminservice.allcustomerprofile();
        if (!result.isEmpty()) {
            return ResponseEntity.ok().body(result);
        } else
           throw new EntitynotfoundException("");
    }catch (EntitynotfoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }}


    @DeleteMapping("/customerprofile1/{customerid}")
    public ResponseEntity<?> deletecustomerprofile(@PathVariable int customerid) {
            try{
        Boolean result = adminservice.deletecustomerprofile(customerid);
            if (result) {
                return ResponseEntity.ok().body("CUSTOMER DELETED SUCCESSFULLY");
            } else
              throw new EntitynotfoundException("");
        } catch(EntitynotfoundException ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }}



    @DeleteMapping("/workerprofile1/{workerid}")
    public ResponseEntity<?> deleteworkerprofile(@PathVariable int workerid) {
        try{
        Boolean result = adminservice.deleteworerrofile(workerid);
            if (result) {
                return ResponseEntity.ok().body("worker DELETED SUCCESSFULLY");
            } else
                throw new EntitynotfoundException("");
        } catch(EntitynotfoundException ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }}
}




