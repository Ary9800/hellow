package com.firstp.hellow.Controller;

import com.firstp.hellow.CustomException.EntitynotfoundException;
import com.firstp.hellow.CustomException.FeedbackNotFoundException;
import com.firstp.hellow.DTO.*;
import com.firstp.hellow.Entity.Work;
import com.firstp.hellow.Entity.Worker;
import com.firstp.hellow.Service.Customerservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private Customerservice customerserv;


    @PostMapping("/register")
    public ResponseEntity<?> registercustomer(@RequestBody CustomerDto customer) {
        try {
            System.out.println(customer.toString());
            CustomerDto result = customerserv.registercustomer(customer);
            return ResponseEntity.ok().body("registration successfull" + result);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body("registrstion unsuccessfull" + ex.getMessage());
        }}



    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody CustomerDto logindetails) {
        Optional<String> result = customerserv.logincustomer(logindetails);
        return result.map(ResponseEntity::ok).orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result.get()));
    }



    @GetMapping("/profile/{customerid}")
    public ResponseEntity<?> getprofile(@PathVariable int customerid) {
        try{
        Optional<CustomerDto> customerprofile = customerserv.getcustomerprofile(customerid);
            return ResponseEntity.ok().body(customerprofile.get());
         }catch(EntitynotfoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }}



    @PutMapping("/editprofile/{customerid}")
    public ResponseEntity<?> customerprofieledit(@RequestBody CustomerDto profile,@PathVariable int customerid) {
        try {
            CustomerDto Customerprofile = customerserv.updatecustomerprofile(profile, customerid).orElseThrow();
            return ResponseEntity.ok().body(Customerprofile);
        } catch (EntitynotfoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }}


    @DeleteMapping("/deleteaccount/{customerid}")
    public ResponseEntity<?>deleteaccount(@PathVariable int customerid,@RequestBody CustomerDto password){
        try{
            boolean result=customerserv.deleteaccount(customerid,password);
            return  ResponseEntity.ok().body("account deleted successfully");
        }catch (RuntimeException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }}


    @GetMapping("/allworker")
    public ResponseEntity<?> findallworker() {
        List<WorkerDto> workers = customerserv.findallworker();
        if (!workers.isEmpty()) {
            return ResponseEntity.ok().body(workers);
        } else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no workers  found");
    }



    @GetMapping("findworker/{workerid}")
    public ResponseEntity<?> findworkerwithid(@PathVariable int workerid) {
        try{
            WorkerDto worker = customerserv.findworkerwithid(workerid);
            return ResponseEntity.ok().body(worker);
        } catch (EntitynotfoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }}



    @GetMapping("findworkerwithfield/{workerfield}")
    public ResponseEntity<?> findworkerwithfeild(@PathVariable String workerfield) {
        try {
           List <WorkerDto> worker = customerserv.findworkerwithfeild(workerfield);
            return ResponseEntity.ok().body(worker);
        } catch (EntitynotfoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no worker  found");
        }}



    @PostMapping("/{customerid}/postwork")
    public ResponseEntity<?> addwork(@RequestBody WorkDto work,@PathVariable int customerid) {
        System.out.println(work.toString());
        try {
            WorkDto workdto = customerserv.addwork(work,customerid);
            return ResponseEntity.ok().body("registration successfull" + workdto);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body("registrstion unsuccessfull"+": " + ex.getMessage());
        }}



    @GetMapping("/{workid}/{customerid}")
    public ResponseEntity<?> getworkbycustomer(@PathVariable int workid, @PathVariable int customerid) {
        try{
       WorkDto workdto = customerserv.findworkfromid(workid, customerid);
       return ResponseEntity.ok().body(workdto);
        } catch ( EntitynotfoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }}



    @GetMapping("getallwork/{customerid}")
    public ResponseEntity<?> getallworkbycustomer(@PathVariable int customerid) {
        try{
        List<WorkDto> workdto = customerserv.findallworkfromid(customerid);
        if (!workdto.isEmpty()) {
            return ResponseEntity.ok().body(workdto);
        } else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("customer has no work");
    }catch(EntitynotfoundException ex){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }}



    @PutMapping("/updatework/{customerid}/{workid}")
    public ResponseEntity<?> updatework(@RequestBody WorkDto work, @PathVariable int customerid,@PathVariable int workid) {
        try {
            Optional<WorkDto> updatedwork = customerserv.updatecustomerwork(work, customerid, workid);
            if (!updatedwork.isEmpty()) {
                return ResponseEntity.ok().body(updatedwork);
            } else
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("work not updated");
        } catch (EntitynotfoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }}



    @PostMapping("/{customerid}/{workid}/{workerid}/feedback")
    public ResponseEntity<?> writeFeedback(@RequestBody feedbackDto feedback1,@PathVariable int customerid, @PathVariable int workerid, @PathVariable int workid) {
        System.out.println(feedback1.toString());
        try {
            feedbackDto feedback2 = customerserv.savefeedback(feedback1,customerid, workerid, workid);
            return ResponseEntity.ok().body(feedback2);
        } catch (EntitynotfoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }catch (RuntimeException ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());}
    }



    @GetMapping("/getworkerallfeedback/{workerid}")
    public ResponseEntity<?> getworkerFeedback(@PathVariable int workerid) {
        try{
        List<feedbackDto> feedbackdto = customerserv.findworkerallfeedback(workerid);
        return ResponseEntity.ok().body(feedbackdto);
        } catch(EntitynotfoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }}



    @GetMapping("/getworkerfeedbackbbbbyid/{workerid}/{feedbackid}")
    public ResponseEntity<?> getworkerFeedbackbyid(@PathVariable int workerid, @PathVariable int feedbackid) {
       try{
           Optional<feedbackDto> feedbackdto = customerserv.findworkerfeedbackbyid(workerid,feedbackid);
           return ResponseEntity.ok().body(feedbackdto);
        } catch (EntitynotfoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }}



    @GetMapping("/getselffeedback/{selfid}")
    public ResponseEntity<?>getselfFeedback(@PathVariable int selfid) {
        try{
            Map<String,List<feedbackDto>> feedbackdto= customerserv.findallseftfeedback(selfid);
        if(!feedbackdto.isEmpty()){
            return ResponseEntity.ok().body(feedbackdto);
        }
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no feedback yet");
    }catch(EntitynotfoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }}
    @GetMapping("/getselffeedbackgivenv/{selfid}")
    public ResponseEntity<?>getselfFeedbackgiven(@PathVariable int selfid) {
        try{
            List<feedbackDto>feedbackdto= customerserv.findseftfeedbackgiven(selfid);
            if(!feedbackdto.isEmpty()){
                return ResponseEntity.ok().body(feedbackdto);
            }
            else
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no feedback yet");
        }catch(EntitynotfoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }}

    @GetMapping("/feedbackrec/{customerid}")
    public ResponseEntity<?>getfeedbackreceived(@PathVariable int customerid) {
        try{
           List<feedbackDto>feedbackdto=customerserv.getfeedbackreceived(customerid);

            return ResponseEntity.ok().body(feedbackdto);
        }
        catch(RuntimeException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }}



    @GetMapping("/feedbackbyid/{customerid}/{feedbackid}")
    public ResponseEntity<?>getfeedbackbyid(@PathVariable int customerid,@PathVariable int feedbackid) {
        try{
            feedbackDto feedbackdto=customerserv.getfeedbackbyid(customerid,feedbackid);

            return ResponseEntity.ok().body(feedbackdto);
        }
        catch(EntitynotfoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }}



        @PutMapping("/upfeedback/{customerid}/{feedbackid}")
        public ResponseEntity<?>updatefeedbackbyid(@PathVariable int customerid,@PathVariable int feedbackid,@RequestBody feedbackDto feedback){
           try{
                feedbackDto updatefeedback=customerserv.updatecustomerfeedback(feedbackid,feedback,customerid).orElseThrow();

                    return ResponseEntity.ok().body(updatefeedback);
                }
               catch(EntitynotfoundException ex){
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }}



    @DeleteMapping("/deletefeedback/{customerid}/{feedbackid}/{workid}")
    public ResponseEntity<?>deleteselffeedbackbyid(@PathVariable int customerid,@PathVariable int feedbackid,@PathVariable int workid){
        try{
            Boolean deletefeedback1=customerserv.deleteselffeedback(feedbackid,customerid,workid);
            if(deletefeedback1){
                return ResponseEntity.ok().body("deleted succesfuly");
            }else {
                throw new FeedbackNotFoundException("not deleted");
            }
        }catch (RuntimeException ex){
           return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }}



    @DeleteMapping("/Deletework/{workid}/{customerid}")
    public ResponseEntity<?>deleteselfwork(@PathVariable int customerid,@PathVariable int workid) {
        try {
            Boolean deletefeedback1 = customerserv.deleteselfwork(customerid, workid);
            if (deletefeedback1) {
                return ResponseEntity.ok().body("deleted succesfuly");
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("no work with id found");
        }catch(EntitynotfoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }}



    @PostMapping("/sendworkerrequest/{customerid}/{workerid}")
    public ResponseEntity<?>sendrequest(@PathVariable int customerid, @PathVariable int workerid) {
        try{
            Boolean result=customerserv.sendrequest(workerid,customerid);
            if(result) {
                return ResponseEntity.ok().body("send request successfully");
            }
            else{
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("request not send");
            }}
        catch(EntitynotfoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("entity not found");
        }}



    @PutMapping("/acceptrequest/{workrequestid}")
    public  ResponseEntity<?>acceptrequest(@PathVariable int workrequestid){
        try {
            WorkrequestDto workrequestDto = customerserv.acceptrequest(workrequestid);
            return ResponseEntity.ok().body(workrequestDto);
        }catch (EntitynotfoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }}



    @PutMapping("/rejectrequest/{workrequestid}")
    public  ResponseEntity<?>rejectrequest(@PathVariable int workrequestid){
        try {
            WorkrequestDto workrequestDto = customerserv.rejectrequest(workrequestid);
            return ResponseEntity.ok().body(workrequestDto);
        }catch (EntitynotfoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }}



    @GetMapping("/getworkerwork/{workerid}/{customerid}")
    public ResponseEntity<?>getworkerallwork(@PathVariable int workerid){
        try{
            List<WorkDto>works=customerserv.getworkerallwork(workerid);
            return ResponseEntity.ok().body(works);
        }catch(Exception ex)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }}


    @GetMapping("/getworkerworkbyid/{workerid}/{customerid}/{workid}")
    public ResponseEntity<?>getworkerworkbyid(@PathVariable int workerid, @PathVariable int workid){
        try{
            WorkDto works=customerserv.getworkerworkbyid(workerid,workid);
            return ResponseEntity.ok().body(works);
        }catch(Exception ex)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }}



    @GetMapping("/getfeedbackofworker/{workerid}/{workid}")
    public ResponseEntity<?>getworkerfeedbackonwork(@PathVariable int workerid,@PathVariable int workid){
        try{
            feedbackDto feedbackdto=customerserv.getworkerfeedbackonwork(workerid,workid);
            return  ResponseEntity.ok().body(feedbackdto);
        }catch (Exception ex){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }}


@PostMapping("/acceptaddingworktoworkerrequest/{customerid}/{temporarystoredid}")
    public ResponseEntity<?>acceptaddingworktoworkerrequest(@PathVariable int customerid,@PathVariable int temporarystoredid) {
    try {
          customerserv.acceptaddingworktoworkerrequest(customerid, temporarystoredid);
        return ResponseEntity.ok().body(" request accepted");

    } catch (RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    } catch (Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error");
    }
}


@PostMapping("/rejectaddingworktoworkerrequest/{customerid}/{temporarystoredid}")
    public ResponseEntity<?>rejectaddingworktoworkerrequest(@PathVariable int customerid, @PathVariable int temporarystoredid ){
        try{
          Boolean result=  customerserv.rejectaddingworktoworkerrequest(customerid,temporarystoredid);
            if(result){
                return ResponseEntity.ok().body("rejected");
            } else
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error");
        }catch (EntitynotfoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }}



        @PutMapping("/changeworkstatus/{workid}/{customerid}")
    public ResponseEntity<?>changeworkstatus(@RequestBody WorkDto workdto,@PathVariable int workid, @PathVariable int customerid){
        System.out.println(workdto);
        try{
           WorkDto work= customerserv.changeworkstatus(workdto,workid,customerid);
return ResponseEntity.ok().body(work);
        }catch (EntitynotfoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }        }}



