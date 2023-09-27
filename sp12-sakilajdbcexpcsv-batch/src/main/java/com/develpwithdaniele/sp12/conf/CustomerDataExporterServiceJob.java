package com.develpwithdaniele.sp12.conf;

import com.develpwithdaniele.sp12.mapper.CustomerDataRowMapper;
import com.develpwithdaniele.sp12.repository.model.CustomerData;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;

@Configuration
@AllArgsConstructor
@Slf4j
public class CustomerDataExporterServiceJob {

    private JobBuilderFactory jobBuilderFactory;

    private StepBuilderFactory stepBuilderFactory;

    private ResourceLoader resourceLoader;

    private DataSource dataSource;

    @Bean
    public Job customerDataExporterJob(){
        return jobBuilderFactory.get("customerDataExporterJob").start(customerDataExporterStep()).build();
    }

    @Bean
    public Step customerDataExporterStep(){
        return this.stepBuilderFactory.get("customerDataExporterStep")
                .<CustomerData,CustomerData>chunk(10)
                .reader(reader())
                .writer(writer()).build();
    }


    @Bean
    public Resource csvResource(){
        return new FileSystemResource("output/customer_jdbc.csv");
    }

    @Bean
    @StepScope
    public JdbcCursorItemReader<CustomerData> reader(){
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
        return new JdbcCursorItemReaderBuilder<CustomerData>()
                .name("jdbcCustomerReader")
                .dataSource(dataSource)
                .rowMapper(rowMapper())
                .sql(query)
                .build();
    }

    @Bean
    public FlatFileItemWriter<CustomerData> writer(){
        return new FlatFileItemWriterBuilder<CustomerData>()
                .name("jdbcCustomeWriter")
                .resource(csvResource())
                .delimited()
                .delimiter(";")
                .names(new String[]{"firstName", "lastName", "email", "address", "city", "country"})
                .build();
    }

    @Bean
    public RowMapper<CustomerData> rowMapper(){
        return new CustomerDataRowMapper();
    }

}
