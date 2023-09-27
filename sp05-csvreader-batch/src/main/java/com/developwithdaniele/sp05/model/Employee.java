package com.developwithdaniele.sp05.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ToString
public class Employee implements Serializable {

    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private String department;
    private BigDecimal salary;

}
