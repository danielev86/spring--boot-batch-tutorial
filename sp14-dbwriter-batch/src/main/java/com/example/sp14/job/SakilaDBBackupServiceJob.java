package com.example.sp14.job;

import com.example.sp14.model.CustomerData;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Configuration
@Slf4j
@AllArgsConstructor
public class SakilaDBBackupServiceJob {

    private JobBuilderFactory jobBuilderFactory;

    private StepBuilderFactory stepBuilderFactory;

    private DataSource dataSource;

    @Qualifier(value = "sakilaBackupDatasource")
    private DataSource sakilaDbDataSource;

    @Bean
    public Job sakilaBackupJob(){
        return jobBuilderFactory.get("sakilaBackupJob").start(sakilaBackupStep()).build();
    }

    @Bean
    public Step sakilaBackupStep(){
        return stepBuilderFactory.get("sakilaBackupStep").<CustomerData,CustomerData>chunk(10)
                .reader(reader())
                .writer(writer())
                .build();
    }

    @Bean
    public ItemReader<CustomerData> reader(){
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
                .name("jdbcSakilaReader")
                .sql(query)
                .dataSource(dataSource)
                .beanRowMapper(CustomerData.class)
                .build();
    }

    @Bean
    public ItemWriter<CustomerData> writer(){
        String insertQuery = "INSERT INTO sakila_backup.customer(first_name,last_name,city,country) VALUES(:firstName,:lastName,:city,:country)";
        return new JdbcBatchItemWriterBuilder<CustomerData>()
                .dataSource(sakilaDbDataSource)
                .sql(insertQuery)
                .beanMapped()
                .build();
    }

}
