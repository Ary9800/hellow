package com.firstp.hellow.Controller;

import com.firstp.hellow.CustomException.EntitynotfoundException;
import com.firstp.hellow.DTO.*;
import com.firstp.hellow.Entity.CustomerWorkerrequest;
import com.firstp.hellow.Entity.Feedback;
import com.firstp.hellow.Entity.Work;
import com.firstp.hellow.Entity.Worker;
import com.firstp.hellow.Service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/worker")
public class WorkerController {
    @Autowired
    private WorkerService workerserv;


    @PostMapping("/register")
    public ResponseEntity<?> registerworker(@RequestBody WorkerDto worker) {
        try {
            System.out.println(worker);
            WorkerDto result = workerserv.registerworker(worker);
            return ResponseEntity.ok().body("registration successfull" + result);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body("registrstion unsuccessfull"+ex.getMessage());
        }}


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody WorkerDto logindetails) {
        try{
       Optional<String>result= workerserv.loginworker(logindetails);
            if (result.isEmpty()){
                return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("incorrect password");
            }
            else{
                return ResponseEntity.ok().body(result.get());
            }
    }catch (EntitynotfoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }}



    @GetMapping("/profile/{workerid}")
    public ResponseEntity<?>getprofile(@PathVariable int workerid){
        try{
        WorkerDto workerprofile=workerserv.getworkerprofile( workerid);
        return ResponseEntity.ok().body(workerprofile);
}catch (Exception ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }}



    @PutMapping("/editprofile/{workerid}")
    public ResponseEntity<?>workerprofieledit(@RequestBody WorkerDto profile,@PathVariable int workerid){
        try{
       WorkerDto updatedworker=workerserv.updateworkerprofile(profile,workerid);

            return ResponseEntity.ok().body(updatedworker);
        }catch (EntitynotfoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }}



@DeleteMapping ("/deleteaccount/{workerid}")
public ResponseEntity<?>deleteaccount(@PathVariable int workerid,@RequestBody WorkerDto password){
        try{
            boolean result=workerserv.deleteaccount(workerid,password);
    return  ResponseEntity.ok().body("deleted successfully");
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }}



    @GetMapping("/allwork")
    public ResponseEntity<?>getallwork(){
        List<WorkDto>works=workerserv.getallwork();
        if(works.isEmpty()){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("no work present");
        }
        return ResponseEntity.ok().body(works);
    }



    @GetMapping("/workbyid/{workid}")
    public ResponseEntity<?>getworkbyid(@PathVariable int workid){
        try {
            WorkDto work = workerserv.getworkbyid(workid);
            return ResponseEntity.ok().body(work);

        }catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }}



    @GetMapping("/workfeild/{workfeild}")
    public ResponseEntity<?>getworkbyfeild(@PathVariable String workfeild){
        try {
            WorkDto work = workerserv.getworkbyfeild(workfeild);
            return ResponseEntity.ok().body(work);
        }catch(Exception ex){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }}



    @GetMapping("/workowner/{workid}")
    public ResponseEntity<?>getcustomerofwork(@PathVariable int workid) {
        try {
        CustomerDto workowner = workerserv.getworkowner(workid);
        return ResponseEntity.ok().body(workowner);
    }catch(Exception ex){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }}






    @GetMapping("/getallfeedback/{workerid}")
    public ResponseEntity<?>getallFeedback(@PathVariable int workerid) {
        try{
            Map<String,List<feedbackDto>> feedback1=workerserv.getallfeedbackbyid(workerid);
       if(!feedback1.isEmpty()){
           return ResponseEntity.ok().body(feedback1);
       } else
       return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no feedback found");
    }catch(EntitynotfoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }}



    @GetMapping("/feedbackbyid/{feedbackid}")
    public ResponseEntity<?>getfeedbackbyid(@PathVariable int feedbackid,@PathVariable int workerid) {
        try {
            Optional<?> feedback = workerserv.getfeedbackbyid(feedbackid, workerid);
            if (!feedback.isEmpty()) {
                return ResponseEntity.ok().body(feedback);
            } else
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no feedback by this id");
        } catch (EntitynotfoundException ex) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage()) ;
        }}


    
    @GetMapping("/allcustomerfeedback/{customerid}")
    public ResponseEntity<?>getallcustomerfeedback(@PathVariable int customerid){
        try{
        List<feedbackDto>customerdto=workerserv.getallcustomerfeedback(customerid);
        if(!customerdto.isEmpty()){
            return ResponseEntity.ok().body(customerdto);
        }
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no feedback of customer");
    }catch (EntitynotfoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }}



    @GetMapping("/customerfeedbackbyid/{feedbackid}/{customerid}")
    public ResponseEntity<?>getcustomerfeedbackbyid(@PathVariable int customerid, @PathVariable int feedbackid){
        try{
            feedbackDto customerfeedbackdto=workerserv.getcustomerfeedbackbyid(customerid,feedbackid);
            return ResponseEntity.ok().body( customerfeedbackdto);
        }catch (EntitynotfoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }}



    @PutMapping("/upfeedback/{feedbackid}/{workerid}")
    public ResponseEntity<?>updatefeedback(@PathVariable int feedbackid,@PathVariable int workerid,@RequestBody feedbackDto feedbackdto){
        try{
        feedbackDto updatefeedback=workerserv.updatefeedback(feedbackid,workerid,feedbackdto).orElseThrow();
        return ResponseEntity.ok().body(updatefeedback);
        }
        catch(EntitynotfoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }}



    @DeleteMapping("/deletefeedback/{workerid}/{feedbackid}")
    public ResponseEntity<?>deletefeedbackbyid(@PathVariable int feedbackid,@PathVariable int workerid) {
        try {
            Boolean deletefeedback1 = workerserv.deletefeedback(feedbackid, workerid);
            if (deletefeedback1) {
                return ResponseEntity.ok().body("deleted succesfuly");
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("deletion unsuccessfull");
        } catch (EntitynotfoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }



        @PostMapping("/sendworkrequest/{workid}/{workerid}")
                public ResponseEntity<?>sendrequest(@PathVariable int workid, @PathVariable int workerid, @RequestBody WorkrequestDto workrequestdto) {
            try{
     Boolean result=workerserv.sendrequest(workid,workerid,workrequestdto);
     if(result) {
    return ResponseEntity.ok().body("send request successfully");
}
    else{
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("request not send");
}

}
            catch(EntitynotfoundException ex){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("entity not found");
            }
        }


@PutMapping("/accpetrequest/{customerworkrequestid}")
    public ResponseEntity<?>acceptcustomerrequest(@PathVariable int customerworkerrequestid){
        try{
        CustomerWorkerReqDto customerworkerrequestdto=workerserv.acceptcustomerrequest(customerworkerrequestid);
        return ResponseEntity.ok().body(customerworkerrequestdto);
}catch (EntitynotfoundException ex){
          return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
}


    @PutMapping("/rejectrequest/{customerworkerrequestid}")
    public ResponseEntity<?>rejectcustomerrequest(@PathVariable int customerworkerrequestid){
        try{
            CustomerWorkerReqDto customerworkerrequestdto=workerserv.rejectcustomerrequest(customerworkerrequestid);
            return ResponseEntity.ok().body(customerworkerrequestdto);
        }catch (EntitynotfoundException ex){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
}

@GetMapping("/getalldonework/{workerid}")
    public ResponseEntity<?>getalldonework(@PathVariable int workerid){
        try{
            List<WorkDto>works=workerserv.getdonework(workerid);
            return ResponseEntity.ok().body(works);
        }catch (EntitynotfoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
}


    @GetMapping("/getdoneworkbyid/{workerid}/{workid}")
    public ResponseEntity<?>getalldonework(@PathVariable int workerid, @PathVariable int workid){
        try{
            WorkDto works=workerserv.getdoneworkbyid(workerid,workid);
            return ResponseEntity.ok().body(works);
        }catch (EntitynotfoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }



    @PostMapping("/addworkdonebyworker/{workerid}/{workid}")
    public ResponseEntity<?>addworktoworker( @RequestBody TemporaryworkfeedbacktableDto tempfeedbackdto, @PathVariable int workerid, @PathVariable int workid){
        System.out.println(tempfeedbackdto );
        try{
             workerserv.addworktoworker( tempfeedbackdto.getFeedback(),workerid, workid);
             return ResponseEntity.ok().body("send add request successfully");
        }catch (RuntimeException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

}
