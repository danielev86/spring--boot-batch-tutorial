package com.developwithdaniele.sp08.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class CustomerInfo implements Serializable {

    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String city;
    private String country;

}
