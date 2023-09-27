package com.develpwithdaniele.sp12.repository.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CustomerData implements Serializable {

    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String city;
    private String country;

}
