package com.developwithdaniele.sp07.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString
public class Actor implements Serializable {

    private Long actorId;
    private String firstName;
    private String lastName;
    private Date lastUpdate;

}
