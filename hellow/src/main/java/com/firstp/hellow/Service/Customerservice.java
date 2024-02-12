package com.firstp.hellow.Service;

import com.firstp.hellow.DTO.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface Customerservice {

    Optional<String> logincustomer(CustomerDto logindetails) ;

    CustomerDto registercustomer(CustomerDto customer);

    Optional<CustomerDto> getcustomerprofile(int customerid);

    List<WorkerDto> findallworker();

    WorkerDto findworkerwithid(int workerid);

   List <WorkerDto> findworkerwithfeild(String workerfeild);

    WorkDto addwork(WorkDto work,int customerid);

    WorkDto findworkfromid(int workid, int customerid);

    List<WorkDto> findallworkfromid(int customerid);

    feedbackDto savefeedback(feedbackDto feedback, int customerid, int wokrerid, int workid);

    List<feedbackDto> findworkerallfeedback(int workerid);

    Map<String,List<feedbackDto>> findallseftfeedback(int selfid);

    Boolean deleteselffeedback(int feedbackid, int customerid, int workid);

    Boolean deleteselfwork(int customerid, int workid);


    Optional<feedbackDto> updatecustomerfeedback(int feedbackid, feedbackDto feedback, int customerid);

    Optional<WorkDto> updatecustomerwork(WorkDto work, int customerid, int workid);

    Optional<CustomerDto> updatecustomerprofile(CustomerDto profile, int customerid);

    Optional<feedbackDto> findworkerfeedbackbyid(int workerid, int feedbackid);

    WorkrequestDto acceptrequest(int workrequestid);

    WorkrequestDto rejectrequest(int workrequestid);

    Boolean sendrequest( int workerid, int customerid);

    List<WorkDto> getworkerallwork(int workerid);

    feedbackDto getworkerfeedbackonwork(int workerid, int workid);

    boolean deleteaccount(int customerid, CustomerDto password);

    WorkDto getworkerworkbyid(int workerid,int workid);

    void acceptaddingworktoworkerrequest(int customerid,int temporarystoredid);

    Boolean rejectaddingworktoworkerrequest(int customerid, int temporarystoredid);

    WorkDto changeworkstatus(WorkDto workdto, int workid, int customerid);

    List<feedbackDto> findseftfeedbackgiven(int selfid);

    List<feedbackDto> getfeedbackreceived(int customerid);

 feedbackDto getfeedbackbyid(int customerid, int feedbackid);

}
