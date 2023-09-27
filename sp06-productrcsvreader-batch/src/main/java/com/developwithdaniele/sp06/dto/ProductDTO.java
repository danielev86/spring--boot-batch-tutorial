package com.developwithdaniele.sp06.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ToString
public class ProductDTO implements Serializable {

    private Integer productId;
    private String productName;
    private String category;
    private BigDecimal price;
    private BigDecimal quantity;
    private String manufacturer;
    private String expirationDate;
    private BigDecimal weight;
    private boolean available;

}
