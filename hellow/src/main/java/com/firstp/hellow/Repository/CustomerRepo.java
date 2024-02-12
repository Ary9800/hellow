package com.firstp.hellow.Repository;

import com.firstp.hellow.Entity.Customer;
import com.firstp.hellow.Entity.Feedback;
import com.firstp.hellow.Entity.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepo extends JpaRepository<Customer,Integer> {

    @Query(value = "SELECT c.password FROM Customer c WHERE COALESCE(:customermobno, c.mobno) = c.mobno OR COALESCE(:customeremail, c.email) = c.email", nativeQuery = true)
    String findBymobnooremail(@Param("customermobno") String customermobno, @Param("customeremail") String customeremail);


@Query(value = "Select * from Customer c where c.id=:selfid ",nativeQuery = true)
    Optional<Customer> findByselfId1(int selfid);


@Query(value = "Select c.receivedfeedbacks from Customer c where c.id=:customerid",nativeQuery = true)
   Optional< List<Feedback>> findBycustomerId(@Param("customerid") int customerid);








    boolean existsByEmail(String email);
}
