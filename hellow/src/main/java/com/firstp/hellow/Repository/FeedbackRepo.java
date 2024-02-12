package com.firstp.hellow.Repository;

import com.firstp.hellow.Entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface FeedbackRepo extends JpaRepository<Feedback,Integer> {


@Query(value = "Select c.feedbacks from Customer c where c.id=:customerid",nativeQuery = true)
   List<Feedback> findgivenBycustomerId(@Param("customerid") int customerid);


    @Query(value = "Select w.Feedbackgiven from Worker w where w.id=:workerid",nativeQuery = true)
    List<Feedback> findgivenByworkerid(@Param("workerid") int workerid);






 @Query(value = "Select c.receivedfeedbacks from Customer c where c.id=:customerid", nativeQuery = true)
 List<Feedback> findreceivedBycustomerId(int customerid);


    @Query(value = "Select w.Feedbackreceived from Worker w where w.id=:workerid",nativeQuery = true)
    List<Feedback> findreceivedByworkerid(int workerid);

@Query(value="select CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM Feedback f WHERE f.customergiven.id = :customerid AND f.work.id = :workid AND f.workerrec.id= :workerid ")
    boolean ExistsBycustomeridandworkidandworkerid(int customerid, int workid, int workerid);
}
