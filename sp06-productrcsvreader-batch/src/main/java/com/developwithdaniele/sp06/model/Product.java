package com.developwithdaniele.sp06.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ToString
public class Product implements Serializable {

    private Integer productId;
    private String productName;
    private String category;
    private BigDecimal price;
    private BigDecimal quantity;
    private String manufacturer;
    private Date expirationDate;
    private BigDecimal weight;
    private boolean available;
}
