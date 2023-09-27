package com.example.sp14.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
public class CustomerData implements Serializable {

    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String country;

}
