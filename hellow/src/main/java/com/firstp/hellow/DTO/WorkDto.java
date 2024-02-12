package com.firstp.hellow.DTO;

import com.firstp.hellow.Entity.Customer;
import com.firstp.hellow.Entity.StatusforWork;
import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Date;
@Valid
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WorkDto {

    private String workname;

    private String description;

    private String feild;

   private StatusforWork status;

    private int Rate;

    private String address;
    private int duration;
    private LocalDate startdate;
    private LocalDate enddate;


}
