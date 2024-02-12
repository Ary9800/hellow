package com.firstp.hellow.DTO;

import com.firstp.hellow.Entity.Customer;
import com.firstp.hellow.Entity.Worker;
import lombok.*;

@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerWorkerReqDto {
    private int id;
    private Customer cusotomer;
    private Worker worker;
    private String messege;
}
