package com.firstp.hellow.Repository;

import com.firstp.hellow.Entity.Customer;
import com.firstp.hellow.Entity.Feedback;
import com.firstp.hellow.Entity.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
public interface WorkRepo extends JpaRepository<Work,Integer> {

@Query(value = "Select * from work w where w.field=:workfeild",nativeQuery = true)
    Optional<Work> findByfeild(String workfeild);


@Query(value = "Select w.customerid from work w where w.id=:workid",nativeQuery = true)
    Optional<Integer> findbyworkId(int workid);

    @Query(value = "SELECT CASE WHEN COUNT(w) > 0 THEN true ELSE false END FROM Work w WHERE w.workname = :workname AND w.feild = :field AND w.customer.id = :customerId")
    boolean existsByworknameandfeildandcustomerid(String workname, String field, int customerId);




}
