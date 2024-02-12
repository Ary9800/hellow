package com.firstp.hellow.Service;

import com.firstp.hellow.DTO.AdminDto;

import com.firstp.hellow.DTO.CustomerDto;
import com.firstp.hellow.DTO.WorkerDto;
import com.firstp.hellow.DTO.feedbackDto;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


public interface AdminServie {
    public AdminDto loginadmin(AdminDto logindetails);

    List<feedbackDto> ccustgivenfeedback(int customerid);

    List<feedbackDto> cworkergivenfeedback(int workerid);

    WorkerDto workerprofile(int workerid);

  CustomerDto customerprofile(int customerid);

   Boolean deletecustomerprofile(int customerid);

    Boolean deleteworerrofile(int workerid);

    List<WorkerDto> allworkerprofile();

    List<CustomerDto> allcustomerprofile();

    feedbackDto customergivenfeedbackbyid(int customerid, int feedbackid);

    feedbackDto workerreceivedfeedbackbyid(int workerid, int feedbackid);

    List<feedbackDto> ccustreceivedfeedback(int customerid);

    List<feedbackDto> cworkerreceivedfeedback(int workerid);

    feedbackDto customerreceivedfeedbackbyid(int customerid, int feedbackid);

    feedbackDto workergivenfeedbackbyid(int workerid, int feedbackid);
}
