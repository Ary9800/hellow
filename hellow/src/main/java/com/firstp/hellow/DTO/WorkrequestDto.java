package com.firstp.hellow.DTO;

import com.firstp.hellow.Entity.Customer;
import com.firstp.hellow.Entity.Status;
import com.firstp.hellow.Entity.Work;
import com.firstp.hellow.Entity.Worker;
import lombok.*;

import javax.persistence.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WorkrequestDto {

    private String messege;

}
