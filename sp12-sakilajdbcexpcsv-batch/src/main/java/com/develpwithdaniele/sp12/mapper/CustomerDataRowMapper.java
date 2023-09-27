package com.develpwithdaniele.sp12.mapper;

import com.develpwithdaniele.sp12.repository.model.CustomerData;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDataRowMapper implements RowMapper<CustomerData> {
    @Override
    public CustomerData mapRow(ResultSet rs, int rowNum) throws SQLException {
        CustomerData customer = new CustomerData();
        customer.setFirstName(rs.getString("first_name"));
        customer.setLastName(rs.getString("last_name"));
        customer.setEmail(rs.getString("email"));
        customer.setAddress(rs.getString("address"));
        customer.setCity(rs.getString("city"));
        customer.setCountry(rs.getString("country"));
        return customer;
    }
}
