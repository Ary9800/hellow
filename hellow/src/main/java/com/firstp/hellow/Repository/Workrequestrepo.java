package com.firstp.hellow.Repository;

import com.firstp.hellow.Entity.Workrequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface Workrequestrepo extends JpaRepository< Workrequest,Integer> {

}
