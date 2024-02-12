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
public class CustomerServiceImpl implements Customerservice {
    @Autowired
    private CustomerRepo customerrepo;
    @Autowired
    private ModelMapper modelmapper;
    @Autowired
    private WorkerRepo workerrepo;
    @Autowired
    private WorkRepo workrepo;
    @Autowired
    private FeedbackRepo feedbackrepo;
    @Autowired
    private Workrequestrepo workrequestrepo;
    @Autowired
    private Cudtomer_Workerreqrepo customerworkerreqrepo;
    @Autowired
    private TemporaryworkfeedbacktableRepo temporaryworkfeedbacktableRepo;



    @Override
    public Optional<String> logincustomer(CustomerDto logindetails) {
        String customermobno = logindetails.getMobno();
        String customeremail = logindetails.getEmail();
        String password = logindetails.getPassword();
        try {
            String dbpassword = customerrepo.findBymobnooremail(customermobno, customeremail);

            if (dbpassword!=null){
                return Optional.of("login successfull");
            } else
                return Optional.of("password incorrect");
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return Optional.of("password incorrect");
    }



    @Override
    public CustomerDto registercustomer(CustomerDto customer) {
        Customer result = modelmapper.map(customer, Customer.class);
        if (customerrepo.existsByEmail( result.getEmail())) {
            throw new NoSuchElementException("customer with" + result.getId() + "already exits");
        }
        Customer newresult = customerrepo.save(result);

        CustomerDto resultdto = modelmapper.map(newresult, CustomerDto.class);
        return resultdto;
    }




    @Override
    public Optional<CustomerDto> getcustomerprofile(int customerid) {
       Customer customer = customerrepo.findById(customerid).orElseThrow(()->new EntitynotfoundException("no customer with this id"));
       CustomerDto customerdto = modelmapper.map(customer, CustomerDto.class);
            return Optional.of(customerdto);

    }



    @Override
    public List<WorkerDto> findallworker() {
        List<Worker> workers = workerrepo.findAll();
        if (!workers.isEmpty()) {
            return workers.stream().
                    map(workerdto -> modelmapper.map(workerdto, WorkerDto.class)).collect(Collectors.toList());
        }
        return Collections.emptyList();


    }




    @Override
    public WorkerDto findworkerwithid(int workerid) {
        Worker worker = workerrepo.findById(workerid).orElseThrow(()->new EntitynotfoundException("no worker with this id"));
        return modelmapper.map(worker, WorkerDto.class);
    }




    @Override
    public List<WorkerDto> findworkerwithfeild(String workerfeild) {
       List< Worker> worker = workerrepo.findByFiled(workerfeild).orElseThrow(()->new EntitynotfoundException("no worker with this profession"));
       if(!worker.isEmpty()){
        return worker.stream().map(worker1->modelmapper.map(worker1, WorkerDto.class)).collect(Collectors.toList());

       }else
    throw new WorkerNotFoundException("no worker found");
    }



    @Override
    public WorkDto addwork(WorkDto work, int customerid) {
        Customer customer=customerrepo.findById(customerid).orElseThrow(()->new EntitynotfoundException("no customer present with this id"));
        Work result = modelmapper.map(work, Work.class);
        if (workrepo.existsByworknameandfeildandcustomerid(result.getWorkname(),result.getFeild(),customerid)) {
            throw new NoSuchElementException("work with this name in this feild by same customer already exists");
        }
        result.setStatus(StatusforWork.PENDING);
        customer.addwork(result);
        customerrepo.save(customer);

        WorkDto resultdto = modelmapper.map(result, WorkDto.class);
        return resultdto;
    }



    @Override
    public WorkDto findworkfromid(int workid,int customerid) {
        Customer customer = customerrepo.findById(customerid).orElseThrow(()->new EntitynotfoundException("no customerfound with thhis id"));
        List<Work>works=customer.getCustomerwork();
       return works.stream().filter(work->work.getId()==workid).map(work -> modelmapper.map(work,WorkDto.class)).findAny().orElseThrow(()->new EntitynotfoundException("no work found with this id")) ;

    }



    @Override
    public List<WorkDto> findallworkfromid(int customerid) {
        Customer customer = customerrepo.findById(customerid).orElseThrow(()->new EntitynotfoundException("no customerfound with thhis id"));
        List<Work>works=customer.getCustomerwork();
            return works.stream().map(work -> modelmapper.map(work, WorkDto.class)).collect(Collectors.toList());
        }



    @Override
    public feedbackDto savefeedback(feedbackDto feedback, int customerid, int workerid, int workid) {
        System.out.println("Customer ID: " + customerid);
        System.out.println("Worker ID: " + workerid);
        System.out.println("Work ID: " + workid);
        if (feedbackrepo.ExistsBycustomeridandworkidandworkerid(customerid,workerid, workid)) {

            throw new NoSuchElementException("hello borhter");
        }
        Customer customer=customerrepo.findById(customerid).orElseThrow(()->new EntitynotfoundException("no customer found with this id"));
        Worker worker=workerrepo.findById(workerid).orElseThrow(()->new EntitynotfoundException("no worker found with this id"));
        Feedback feedback1 = modelmapper.map(feedback, Feedback.class);
        feedback1.setWork(workrepo.findById(workid).orElseThrow(()->new WorknotFoundException("no work found")));
        System.out.println(feedback1.toString());
        customer.addfeedback(feedback1);
        worker.addreceivedfeedback(feedback1);
        customerrepo.save(customer);
        workerrepo.save(worker);
        return modelmapper.map(feedback1, feedbackDto.class);
    }



    @Override
    public List<feedbackDto> findworkerallfeedback(int workerid) {
        Worker worker = workerrepo.findByfeedbackreceived(workerid).orElseThrow(() -> new WorkerNotFoundException(("no worker found")));
        List<Feedback> workerfeedback = worker.getFeedbackrecieved();
        if (!workerfeedback.isEmpty()) {
            return workerfeedback.stream().map(feedbackdto -> modelmapper.map(feedbackdto, feedbackDto.class)).collect(Collectors.toList());
        } else
            throw new FeedbackNotFoundException("no feedback found");
    }



    @Override
    public Map<String,List<feedbackDto>> findallseftfeedback(int selfid) {
       Customer customer = customerrepo.findByselfId1(selfid).orElseThrow(()->new EntitynotfoundException("no customer found with this id "));
        List<Feedback> givenfeedback=customer.getReceivedfeedbacks();
        List<Feedback>receivedfeedback=customer.getFeedbacks();
        if (!givenfeedback.isEmpty() || !receivedfeedback.isEmpty()) {
            Map<String, List<feedbackDto>> allfeedback = new HashMap<>();
            if (!givenfeedback.isEmpty()) {
                allfeedback.put("givenfeedback", givenfeedback.stream().map(feedbackdto -> modelmapper.map(feedbackdto, feedbackDto.class)).collect(Collectors.toList()));
            }
            if (!receivedfeedback.isEmpty()) {
                allfeedback.put("receivedfeedback", receivedfeedback.stream().map(feedbackdto -> modelmapper.map(feedbackdto, feedbackDto.class)).collect(Collectors.toList()));
            }
            return allfeedback;
        }
        throw new FeedbackNotFoundException("no feedback found");
    }




    @Override
    public Boolean deleteselffeedback(int feedbackid, int customerid, int workid) {
        Customer customer   =customerrepo.findByselfId1(customerid).orElseThrow(()->new EntitynotfoundException("no customer found with this id "));
        Work work=workrepo.findById(workid).orElseThrow(()->new WorknotFoundException("no work found"));
        Feedback feedback=feedbackrepo.findById(feedbackid).orElseThrow();
       customer.removefeedback(feedback);
         Worker worker=feedback.getWorkerrec();
         worker.removereceivedfeedback(feedback); ;
         work.removefeedback(feedback);
         customerrepo.save(customer);
         workerrepo.save(worker);
         workrepo.save(work);
         return true;
    }



    @Override
    public Boolean deleteselfwork(int customerid, int workid) {
      Customer customer   = customerrepo.findById(customerid).orElseThrow(() -> new EntitynotfoundException("no work found with this id "));
        List<Work> works=customer.getCustomerwork();
        return works.removeIf(work -> work.getId() == workid);

    }



    @Override
    public Optional<feedbackDto> updatecustomerfeedback(int feedbackid, feedbackDto feedback, int customerid) {

        Customer customer = customerrepo.findById(customerid).orElseThrow(() -> new EntitynotfoundException("no customer found with this id "));
        List<Feedback> feedbacks = customer.getFeedbacks();
        Feedback finalfeedback = feedbacks.stream().filter(feedback1 -> feedback1.getId() == feedbackid).findAny().orElseThrow(() -> new EntitynotfoundException("no feedback found with this id "));
Worker worker=finalfeedback.getWorkerrec();
List<Feedback>workerfeedbacks=worker.getFeedbackrecieved();
Field[] fields = feedback.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(feedback);
                if (value != null && field.getType().isPrimitive() ? (int) value != 0 : true) {
                    Field feedbackfeild = Feedback.class.getDeclaredField(field.getName());
                    feedbackfeild.setAccessible(true);
                    feedbackfeild.set(finalfeedback, value);
                }} catch (NoSuchFieldException | IllegalAccessException ex) {
                 throw new NoSuchElementException("no elemet found");
                }}
                customerrepo.save(customer);
                workerfeedbacks.removeIf(feedback1 -> feedback1.getId()==feedbackid);
                workerfeedbacks.add(finalfeedback);
                workerrepo.save(worker);
                return Optional.of(modelmapper.map(finalfeedback, feedbackDto.class));
                }



    @Override
    public Optional<WorkDto> updatecustomerwork(WorkDto work, int customerid, int workid) {
        Customer customer = customerrepo.findById(customerid).orElseThrow(() -> new EntitynotfoundException("no customer found with this entity"));
        List<Work> works = customer.getCustomerwork();
        Work work1 = works.stream().filter(work2 -> work2.getId() == workid).findFirst().orElseThrow(() -> new EntitynotfoundException("no customer found with this entity"));
        Field[] fields = work.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(work);
                if (value != null && field.getType().isPrimitive() ? (int) value != 0 : true) {
                    Field workfeild = Work.class.getDeclaredField(field.getName());
                    workfeild.setAccessible(true);
                    workfeild.set(work1, value);
                }
            } catch (NoSuchFieldException | IllegalAccessException ex) {
               throw new NoSuchElementException("no element found");
            }
        }
          customerrepo.save(customer);

            return Optional.of(modelmapper.map(work1, WorkDto.class));
        }




    @Override
    public Optional<CustomerDto> updatecustomerprofile(CustomerDto profile, int customerid) {
        Customer Customer = customerrepo.findById(customerid).orElseThrow();
        Field[] fields = profile.getClass().getDeclaredFields();
            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    Object value = field.get(profile);
                    if (value != null && field.getType().isPrimitive() ? (int) value != 0 : true) {
                        Field customerfeild = Customer.class.getDeclaredField(field.getName());
                        customerfeild.setAccessible(true);
                        customerfeild.set(Customer, value);
                    }


                } catch (NoSuchFieldException | IllegalAccessException ex) {
                    throw new NoSuchElementException("no elemnet found");
                }
            }
                Customer customer1 = customerrepo.save(Customer);
                return Optional.of(modelmapper.map(customer1, CustomerDto.class));

        }




    @Override
    public Optional<feedbackDto> findworkerfeedbackbyid(int workerid, int feedbackid) {
        Worker worker = workerrepo.findByfeedbackreceived(workerid).orElseThrow(()->new EntitynotfoundException("no customerfound with thhis id"));
        List<Feedback>feedbacks= worker.getFeedbackrecieved();
        if(!feedbacks.isEmpty()) {
            return  Optional.of(feedbacks.stream().filter(feedback -> feedback.getId() == feedbackid).map(feedback1 -> modelmapper.map(feedback1, feedbackDto.class)).findAny().orElseThrow(() -> new EntitynotfoundException("no work found with this id")));
        }
        throw new FeedbackNotFoundException("no feedback found");
    }



    @Override
    public WorkrequestDto acceptrequest(int workrequestid) {
        Workrequest workrequest = workrequestrepo.findById(workrequestid).orElseThrow();
        workrequest.setStatus(Status.ACCEPTED);
        workrequest.setMessege("request accepted");
        return modelmapper.map(workrequest,WorkrequestDto.class);
    }




    @Override
    public WorkrequestDto rejectrequest(int workrequestid) {
        Workrequest workrequest = workrequestrepo.findById(workrequestid).orElseThrow();
        workrequest.setStatus(Status.REJECTED);
        workrequest.setMessege("request rejected");
        return modelmapper.map(workrequest,WorkrequestDto.class);
    }



    @Override
    public Boolean sendrequest( int workerid, int customerid) {
        try {
            Worker worker = workerrepo.findById(workerid).orElseThrow(()->new WorkerNotFoundException("no worker found"));
            Customer customer = customerrepo.findById(customerid).orElseThrow(()->new CustomerNotFoundException("no customer found"));
            CustomerWorkerrequest customerworkerrequest=new CustomerWorkerrequest();
            worker.acceptrequest(customerworkerrequest);
            customer.sendrequest(customerworkerrequest);
            customerworkerrequest.setStatus(Status.PENDING);
            customerworkerrequest.setMessege("request send");
            customerworkerreqrepo.save(customerworkerrequest);
            return true;
        } catch (RuntimeException ex) {
            throw new EntitynotfoundException("entity not found");
        }}



    @Override
    public List<WorkDto> getworkerallwork(int workerid) {
        Worker worker=workerrepo.findById(workerid).orElseThrow(()->new WorkerNotFoundException("no worker found"));
        List<Work>works=worker.getWorks();
        if(!works.isEmpty()){
       return works.stream().map(work -> modelmapper.map(work,WorkDto.class)).collect(Collectors.toList());
    }
        else
            throw new EntitynotfoundException("no work found");
    }


    @Override
    public feedbackDto getworkerfeedbackonwork(int workerid, int workid) {
      Work work=workrepo.findById(workid).orElseThrow(()->new WorknotFoundException("no work found"));
     return  work.getFeedback().stream().filter(feedback -> feedback.getWorkerrec().getId()==workerid).findAny().map(feedback1->modelmapper.map(feedback1,feedbackDto.class)).orElseThrow(()->new FeedbackNotFoundException("no feedback found"));
    }




    @Override
    public boolean deleteaccount(int customerid, CustomerDto password) {
            Customer customer=customerrepo.findById(customerid).orElseThrow(()->new CustomerNotFoundException("no customer found with this id"));
            String password1=password.getPassword();
            if(customer.getPassword().equals(password1)){
                customerrepo.deleteById(customerid);
                return true;
            }else
                throw new EntitynotfoundException("invalid password");
    }



    @Override
    public WorkDto getworkerworkbyid(int workerid,int workid) {
      Worker worker =workerrepo.findById(workerid).orElseThrow(()->new WorkerNotFoundException("no worker found with this id"));
        List<Work>works=worker.getWorks();
        if(!works.isEmpty()){
            return works.stream().filter(work -> work.getId()==workid).findAny().map(work -> modelmapper.map(work,WorkDto.class)).orElseThrow(()->new WorknotFoundException("no work found with this id"));
        }
        else
            throw new EntitynotfoundException("no work found");
    }



    @Override
    public void  acceptaddingworktoworkerrequest(int customerid,int temporarystoredid) {
       Temporaryworkfeedbacktable temporarywork= temporaryworkfeedbacktableRepo.findById(temporarystoredid).orElseThrow(()->new FeedbackNotFoundException("no request fouund"));
       Customer customer=customerrepo.findById(customerid).orElseThrow(()->new CustomerNotFoundException("no customer fouund"));
       Feedback feedback= modelmapper.map(temporarywork.getFeedback(),Feedback.class);
        Worker worker=workerrepo.findById(temporarywork.getWorkerid()).orElseThrow(()->new WorkerNotFoundException("no worker fouund"));
        try {
            worker.addfeedback(feedback);
            worker.addwork(temporarywork.getWork());
            customer.addrecievedfeedback(feedback);
            workerrepo.save(worker);
            customerrepo.save(customer);
            temporaryworkfeedbacktableRepo.deleteById(temporarystoredid);
        }catch(Exception e){
            throw new RuntimeException("request not accepted");
        }

    }



    @Override
    public Boolean rejectaddingworktoworkerrequest(int customerid, int temporarystoredid) {
       boolean result=temporaryworkfeedbacktableRepo.existsById(temporarystoredid);
       if(result){
           temporaryworkfeedbacktableRepo.deleteById(temporarystoredid);
           return true;
       }
        else throw  new EntitynotfoundException("no work added");
    }

    @Override
    public WorkDto changeworkstatus(WorkDto workdto, int workid, int customerid) {
       Work work=modelmapper.map(workdto,Work.class);
       System.out.println(work.toString());
       Work dwork=workrepo.findById(workid).orElseThrow(()->new WorknotFoundException("no work found"));
      if(work.getStatus()==StatusforWork.STARTED){
          dwork.setStatus(StatusforWork.STARTED);
         Work startedwork= workrepo.save(dwork);
         return modelmapper.map(startedwork,WorkDto.class);
      }
      else if(work.getStatus()==StatusforWork.COMPLETED){
         dwork.setStatus(StatusforWork.COMPLETED);
         Work completedwork= workrepo.save(dwork);
          return modelmapper.map(completedwork,WorkDto.class);
      }
      else if(work.getStatus()==StatusforWork.PENDING){
          return workdto;
      }
      else throw new EntitynotfoundException("not updated");
    }

    @Override
    public List<feedbackDto> findseftfeedbackgiven(int selfid) {
        Customer customer = customerrepo.findByselfId1(selfid).orElseThrow(()->new EntitynotfoundException("no customer found with this id "));
        List<Feedback> selffeedback=customer.getFeedbacks();
        if (!selffeedback.isEmpty()) {
            return selffeedback.stream().map(feedbackdto -> modelmapper.map(feedbackdto, feedbackDto.class)).collect(Collectors.toList());
        }
        throw new FeedbackNotFoundException("no feedback found");
    }

    @Override
    public List<feedbackDto> getfeedbackreceived(int selfid) {
        Customer customer = customerrepo.findByselfId1(selfid).orElseThrow(()->new EntitynotfoundException("no customer found with this id "));
        List<Feedback> selffeedback=customer.getReceivedfeedbacks();
        if (!selffeedback.isEmpty()) {
            return selffeedback.stream().map(feedbackdto -> modelmapper.map(feedbackdto, feedbackDto.class)).collect(Collectors.toList());
        }
        throw new FeedbackNotFoundException("no feedback found");
    }

    @Override
    public feedbackDto getfeedbackbyid(int customerid, int feedbackid) {
        Customer customer  = customerrepo.findByselfId1(customerid).orElseThrow(()->new EntitynotfoundException("no customerfound with thhis id"));
        List<Feedback>feedbacks=customer.getFeedbacks();
        if(!feedbacks.isEmpty()) {
            return  feedbacks.stream().filter(feedback -> feedback.getId() == feedbackid).map(feedback1 -> modelmapper.map(feedback1, feedbackDto.class)).findAny().orElseThrow(() -> new EntitynotfoundException("no work found with this id"));
        }
        throw  new FeedbackNotFoundException("no feedback found");
    }}















