package com.developwithdaniele.sp08.mapper;

import com.developwithdaniele.sp08.model.CustomerInfo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerInfoRowMapper implements RowMapper<CustomerInfo> {
    @Override
    public CustomerInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        CustomerInfo customer = new CustomerInfo();
        customer.setFirstName(rs.getString("first_name"));
        customer.setLastName(rs.getString("last_name"));
        customer.setEmail(rs.getString("email"));
        customer.setAddress(rs.getString("address"));
        customer.setCity(rs.getString("city"));
        customer.setCountry(rs.getString("country"));
        return customer;
    }
}
