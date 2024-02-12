package com.firstp.hellow.Service;
import com.firstp.hellow.CustomException.*;
import com.firstp.hellow.DTO.*;
import com.firstp.hellow.Entity.*;
import com.firstp.hellow.Repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class WorkerServiceImpl implements WorkerService{
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private WorkerRepo workerrepo;
    @Autowired
    private WorkRepo workrepo;
    @Autowired
    private CustomerRepo customerrepo;
    @Autowired
    private FeedbackRepo feedbackrepo;
    @Autowired
    private Workrequestrepo workrequestrepo;
    @Autowired
    private Cudtomer_Workerreqrepo customer_workerreqrepo;
    @Autowired
    private TemporaryworkfeedbacktableRepo temporaryworkfeedbacktablerepo;


    @Override
    public WorkerDto registerworker(WorkerDto worker) {
        Worker worker2=modelMapper.map(worker,Worker.class);
        boolean result=workerrepo.existsByEmail(worker2.getEmail());
        if(result){
            throw new NoSuchElementException("worker id "+worker2.getId()+"already exists");
        }
        Worker workerget=workerrepo.save(worker2);
      return   modelMapper.map(workerget,WorkerDto.class);
    }



    @Override
    public Optional<String> loginworker(WorkerDto logindetails) {
        Worker worker=modelMapper.map(logindetails,Worker.class);
        String mobno=worker.getMobno();
        String email=worker.getEmail();
        String password=worker.getPassword();
        String dbpass=workerrepo.findbyEmailormob(mobno,email).orElseThrow();
        if (dbpass.equals(password)) {
                return Optional.of("login successfull");
            } else
                return Optional.empty();
    }




    @Override
    public WorkerDto getworkerprofile(int workerid) {
        Worker worker=workerrepo.findById(workerid).orElseThrow();
        return modelMapper.map(worker,WorkerDto.class);
    }



    @Override
    public WorkerDto updateworkerprofile(WorkerDto profile, int workerid) {
Worker worker=workerrepo.findById(workerid).orElseThrow();
Field[] fields=profile.getClass().getDeclaredFields();
    for(Field field:fields) {
        try {
            field.setAccessible(true);
            Object value = field.get(profile);
            if (value != null && field.getType().isPrimitive() ? (int) value != 0 : true) {
                Field workerfeild = Worker.class.getDeclaredField(field.getName());
                workerfeild.setAccessible(true);
                workerfeild.set(worker, value);
            }
        } catch (NoSuchFieldException | IllegalAccessException ex) {
            ex.printStackTrace();
        }
    }
        Worker worker1 = workerrepo.save(worker);
        return modelMapper.map(worker1, WorkerDto.class);
    }



    @Override
    public List<WorkDto> getallwork() {
        List<Work> work = workrepo.findAll();
        if (!work.isEmpty()) {
            return work.stream().
                    map(workdto -> modelMapper.map(workdto, WorkDto.class)).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }



    @Override
    public WorkDto getworkbyid(int workid) {
        Work work=workrepo.findById(workid).orElseThrow();
        return modelMapper.map(work,WorkDto.class);
    }



    @Override
    public WorkDto getworkbyfeild(String workfeild) {
        Work work=workrepo.findByfeild(workfeild).orElseThrow();
        return modelMapper.map(work,WorkDto.class);
    }



    @Override
    public CustomerDto getworkowner(int workid) {
      int  customerid=workrepo.findbyworkId(workid).orElseThrow(()->new EntitynotfoundException("no customer found"));
      Customer customer=customerrepo.findById(customerid).orElseThrow(()->new CustomerNotFoundException("NO customer found"));
      return modelMapper.map(customer,CustomerDto.class);
    }



    @Override
    public Map<String,List<feedbackDto>> getallfeedbackbyid(int workerid) {
        Worker worker=workerrepo.findById(workerid).orElseThrow(()->new EntitynotfoundException("no customer found"));
        List <Feedback> givenfeedback=worker.getFeedbackgiven();
        List<Feedback> feedbackreceived=worker.getFeedbackrecieved();
        if (!givenfeedback.isEmpty() || !feedbackreceived.isEmpty()) {
            Map<String, List<feedbackDto>> allfeedback = new HashMap<>();
            if (!givenfeedback.isEmpty()) {
                allfeedback.put("givenfeedback", givenfeedback.stream().map(feedbackdto -> modelMapper.map(feedbackdto, feedbackDto.class)).collect(Collectors.toList()));
            }
            if (!feedbackreceived.isEmpty()) {
                allfeedback.put("receivedfeedback", feedbackreceived.stream().map(feedbackdto ->modelMapper.map(feedbackdto, feedbackDto.class)).collect(Collectors.toList()));
            }
            return allfeedback;
        }
        throw new FeedbackNotFoundException("no feedback found");
    }







    @Override
    public Boolean deletefeedback(int feedbackid,int workerid) {
        List<Feedback>feedbacks=workerrepo.findByworkerId(workerid).orElseThrow(()->new EntitynotfoundException("no worker found"));
      return  feedbacks.removeIf(feedback -> feedback.getId()==feedbackid);
    }



    @Override
    public Optional<?> getfeedbackbyid(int feedbackid,int workerid) {
        List<Feedback> feedback = workerrepo.findByworkerId(workerid).orElseThrow(()->new EntitynotfoundException("no customer found"));
        if (!feedback.isEmpty()) {
            return Optional.of(feedback.stream().filter(feedback1->feedback1.getId()==feedbackid).findAny().map(feedbackdto -> modelMapper.map(feedbackdto, feedbackDto.class)).orElseThrow(()->new EntitynotfoundException("no feedback with this id")));
        }
        return Optional.empty();
    }



    @Override
    public Optional<feedbackDto> updatefeedback(int feedbackid,int workerid, feedbackDto feedbackdto) {
     Worker worker=workerrepo.findById(workerid).orElseThrow();
     List<Feedback>feedbacks=worker.getFeedbackgiven();

      Feedback feedbacktoupdate=feedbacks.stream().filter(feedback1->feedback1.getId()==feedbackid).findAny().orElseThrow();
      Customer customer=feedbacktoupdate.getCustomerrec();
      List<Feedback>customerrecivefeedback=customer.getReceivedfeedbacks();
           if(feedbackdto.getDescription()!=null){
           feedbacktoupdate.setDescription(feedbackdto.getDescription());}
           if(feedbackdto.getName()!=null) {
           feedbacktoupdate.setName(feedbackdto.getName());}
           if(feedbackdto.getRating()!=0) {
           feedbacktoupdate.setRating(feedbackdto.getRating());}
           Feedback updatedfeedback=feedbackrepo.save(feedbacktoupdate);
           customerrecivefeedback.removeIf(feedback2->feedback2.getId()==feedbackid);
           customerrecivefeedback.add(feedbacktoupdate);
           feedbacks.removeIf(f->f.getId()==feedbackid);
           feedbacks.add(feedbacktoupdate);
           return Optional.of(modelMapper.map(updatedfeedback, feedbackDto.class));
    }



    @Override
    public List<feedbackDto> getallcustomerfeedback(int customerid) {
        List <Feedback> customerfeedback=customerrepo.findBycustomerId(customerid).orElseThrow(()->new EntitynotfoundException("no customer found"));
        if(!customerfeedback.isEmpty()){
            return customerfeedback.stream().map(feedbackdto->modelMapper.map(feedbackdto,feedbackDto.class)).collect(Collectors.toList());
        }
        throw  new EntitynotfoundException("no feedback  found");

    }



    @Override
    public boolean sendrequest(int workid, int workerid, WorkrequestDto workrequestdto) {
        try {
            Work work = workrepo.findById(workid).orElseThrow();
            if(work.getStatus()==StatusforWork.PENDING){
                Worker worker = workerrepo.findById(workerid).orElseThrow();
            Customer customer =work.getCustomer();
                Workrequest workrequest=modelMapper.map(workrequestdto,Workrequest.class);
                workrequest.setWorks(work);
                customer.acceptrequest(workrequest);
                worker.sendrequest(workrequest);
                customerrepo.save(customer);
                workerrepo.save(worker);
                return true;}
            else throw new RuntimeException("work already started or completed");
        } catch (RuntimeException ex) {
            throw new EntitynotfoundException("entity not found");
        }}



        @Override
    public feedbackDto getcustomerfeedbackbyid(int customerid, int feedbackid) {
        List <Feedback> customerfeedback=customerrepo.findBycustomerId(customerid).orElseThrow(()->new EntitynotfoundException("no customer found"));
        if(!customerfeedback.isEmpty()){
            return customerfeedback.stream().filter(feedback -> feedback.getId()==feedbackid).findAny().map(feedbackdto->modelMapper.map(feedbackdto,feedbackDto.class)).orElseThrow();
        }
        else{
            throw  new EntitynotfoundException("no feedback found");
        }
    }



    @Override
    public CustomerWorkerReqDto acceptcustomerrequest(int customerworkerrequestid) {
        CustomerWorkerrequest customerworkerrequest = customer_workerreqrepo.findById(customerworkerrequestid).orElseThrow();
        customerworkerrequest.setStatus(Status.ACCEPTED);
        customerworkerrequest.setMessege("request accepted");
        return modelMapper.map(customerworkerrequest,CustomerWorkerReqDto.class);

    }



    @Override
    public CustomerWorkerReqDto rejectcustomerrequest(int customerworkerrequestid) {
        CustomerWorkerrequest customerworkerrequest =customer_workerreqrepo.findById(customerworkerrequestid).orElseThrow();
        customerworkerrequest.setStatus(Status.REJECTED);
        customerworkerrequest.setMessege("request rejected");
        return modelMapper.map(customerworkerrequest,CustomerWorkerReqDto.class);
    }



    @Override
    public boolean deleteaccount(int workerid, WorkerDto password) {
        Worker worker=workerrepo.findById(workerid).orElseThrow(()->new WorkerNotFoundException("no worker found"));
        String password1=password.getPassword();
        if(worker.getPassword().equals(password1)){
            workerrepo.deleteById(workerid);
            return true;
        }else
         throw new EntitynotfoundException("invalid password");
    }

    @Override
    public List<WorkDto> getdonework(int workerid) {
        List<Work>works=workerrepo.findworkbyid(workerid);
        if(!works.isEmpty()){
            return works.stream().map(work -> modelMapper.map(work,WorkDto.class)).collect(Collectors.toList());
        }
        else
            throw  new EntitynotfoundException("no work found");
    }

    @Override
    public WorkDto getdoneworkbyid(int workerid, int workid) {
        List<Work>works=workerrepo.findworkbyid(workerid);
        if(!works.isEmpty()){
            return works.stream().filter(work -> work.getId()==workid).findAny().map(work -> modelMapper.map(work,WorkDto.class)).orElseThrow();
        }
        else
            throw  new EntitynotfoundException("no work found");
    }

    @Override
    public void addworktoworker(FeedbackEmbedDto feedbackdto, int workerid, int workid) {
        Work work=workrepo.findById(workid).orElseThrow(()->new WorknotFoundException("no work found"));
        FeedbackEmbed feedback=modelMapper.map(feedbackdto,FeedbackEmbed.class);
        System.out.println(feedback);
        Temporaryworkfeedbacktable newobject=new Temporaryworkfeedbacktable();
        newobject.setWork(work);
        newobject.setWorkerid(workerid);
        newobject.setFeedback(feedback);
        temporaryworkfeedbacktablerepo.save(newobject);

    }
}

