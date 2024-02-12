package com.firstp.hellow.Repository;

import com.firstp.hellow.Entity.Feedback;
import com.firstp.hellow.Entity.Work;
import com.firstp.hellow.Entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface WorkerRepo extends JpaRepository<Worker,Integer> {

    @Query(value = "Select w.password from Worker w where w.email in COALESCE(:email,:mobno) or w.mobno in COALESCE(:email,:mobno) ",nativeQuery = true)
   Optional <String> findbyEmailormob(String mobno, String email);


@Query(value = "Select * from Worker w where w.profession=:workerfield",nativeQuery = true)
    Optional<List<Worker>> findByFiled(String workerfield);


@Query(value = "Select * from worker w where w.id=:workerid",nativeQuery = true)
  Optional  <Worker> findByfeedbackreceived(int workerid);


@Query(value = "Select w.feedbackgiven from worker w where w.id=:workerid",nativeQuery = true)
    Optional<List<Feedback> >findByworkerId(int workerid);



    boolean existsByEmail(String email);



@Query(value="Select w.works from worker w where w.id=:workerid",nativeQuery = true)
    List<Work> findworkbyid(int workerid);



}
