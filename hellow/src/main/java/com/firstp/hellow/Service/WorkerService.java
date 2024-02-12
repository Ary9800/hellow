package com.firstp.hellow.Service;

import com.firstp.hellow.DTO.*;
import com.firstp.hellow.Entity.Feedback;
import com.firstp.hellow.Entity.Work;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface WorkerService {
   WorkerDto registerworker(WorkerDto worker);

   Optional<String> loginworker(WorkerDto logindetails);

    WorkerDto getworkerprofile(int workerid);

    WorkerDto updateworkerprofile(WorkerDto profile,int workerid);

    List<WorkDto> getallwork();

    WorkDto getworkbyid(int workid);

   WorkDto getworkbyfeild(String workfeild);

    CustomerDto getworkowner(int workid);

    Map<String,List<feedbackDto>> getallfeedbackbyid(int workerid);





    Boolean deletefeedback(int feedbackid, int workerid);

 Optional<?> getfeedbackbyid(int feedbackid,int workerid);

 Optional<feedbackDto> updatefeedback(int feedbackid,int workerid,feedbackDto feedbackdto);

 List<feedbackDto> getallcustomerfeedback(int customerid);

    boolean sendrequest(int workid, int workerid,WorkrequestDto workrequestdto);

    feedbackDto getcustomerfeedbackbyid(int customerid, int feedbackid);


    CustomerWorkerReqDto acceptcustomerrequest(int customerworkerrequestid);

    CustomerWorkerReqDto rejectcustomerrequest(int customerworkerrequestid);

    boolean deleteaccount(int workerid, WorkerDto password);

    List<WorkDto> getdonework(int workerid);

    WorkDto getdoneworkbyid(int workerid, int workid);

    void addworktoworker( FeedbackEmbedDto feedbackdto, int workerid, int workid);
}
