package com.firstp.hellow.Repository;

import com.firstp.hellow.Entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface AdminRepository extends JpaRepository<Admin,Integer> {

@Query(value = "Select * from Admin ad where ad.email=:adminemail",nativeQuery = true)
    Admin findByEmail(String adminemail);
@Query(value = "Select a.password from admin a where a.email=:adminemail",nativeQuery = true)
   Optional<String> findPasswordByEmail(String adminemail);
    @Query(value = "Select a.password from admin a where a.employeeid=:adminemployeeid",nativeQuery = true)
    Optional<String> findPasswordByEmployeeid(String adminemployeeid);
@Query(value = "Select * from Admin ad where ad.employeeid=:adminemployeeid",nativeQuery = true)
    Admin findByEmployeeid(String adminemployeeid);
}

