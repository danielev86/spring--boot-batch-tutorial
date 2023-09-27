package com.developwithdaniele.sp08.job;

import com.developwithdaniele.sp08.conf.ParameterQuery;
import com.developwithdaniele.sp08.mapper.CustomerInfoRowMapper;
import com.developwithdaniele.sp08.model.CustomerInfo;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;

import javax.sql.DataSource;
import java.util.List;

@Configuration
@AllArgsConstructor
@Slf4j
public class CustomerDataReportServiceJob {

    private JobBuilderFactory jobBuilderFactory;

    private StepBuilderFactory stepBuilderFactory;

    @Qualifier("sakilaDatasource")
    private final DataSource sakilaDataSource;

    private ParameterQuery parameterQuery;

    @Bean
    public Job customerReportJob(){
        return this.jobBuilderFactory.get("customerReportJob").start(customerReportStep()).build();
    }

    @Bean
    public Step customerReportStep(){
        return this.stepBuilderFactory.get("customerReportStep")
                .<CustomerInfo,CustomerInfo>chunk(20)
                .reader(reader())
                .writer(writer())
                .build();
    }

    @Bean
    @StepScope
    public JdbcCursorItemReader<CustomerInfo> reader(){
        String query = " select customer.first_name as first_name "
                + " , customer.last_name as last_name " +
                " , customer.email as email         " +
                " , address.address as address      " +
                " , city.city as city               " +
                " , country.country as country      " +
                " from sakila.customer customer     " +
                " inner join sakila.address address on customer.address_id = address.address_id " +
                " inner join sakila.city city on address.city_id = city.city_id " +
                " inner join sakila.country country on city.country_id = country.country_id ";

        JdbcCursorItemReader<CustomerInfo> reader = new JdbcCursorItemReader<CustomerInfo>();
        reader.setName("jdbcCustomerDataReaderBuilder");
        reader.setDataSource(sakilaDataSource);
        reader.setRowMapper(new CustomerInfoRowMapper());
        String queryParams = parameterQuery.getCountry();
        if (queryParams != null && !queryParams.isBlank()){
            query += " where country = ?";
            ArgumentPreparedStatementSetter setter = new ArgumentPreparedStatementSetter(new Object[]{queryParams});
            reader.setPreparedStatementSetter(setter);
        }
        reader.setSql(query);
        return reader;
    }

    @Bean
    public ItemWriter<CustomerInfo> writer(){
        return new ItemWriter<CustomerInfo>() {
            @Override
            public void write(List<? extends CustomerInfo> list) throws Exception {
                log.info("Element size: {}", list.size());
                list.forEach(customerInfo -> log.info("Customer data: {}", customerInfo));
            }
        };
    }


}
