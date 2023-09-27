package com.developwithdaniele.sp04.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class Customer implements Serializable {

    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private String ipAddress;

}
