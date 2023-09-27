package com.developwithdaniele.sp07.job;

import com.developwithdaniele.sp07.mapper.ActorRowMapper;
import com.developwithdaniele.sp07.model.Actor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.List;

@Configuration
@AllArgsConstructor
@Slf4j
public class SakilaActorServiceJob {

    private static final String SQL_QUERY_STRING = "SELECT actor_id, first_name, last_name, last_update FROM sakila.actor actor";

    private JobBuilderFactory jobBuilderFactory;

    private StepBuilderFactory stepBuilderFactory;

    @Qualifier("sakilaDataSource")
    private DataSource sakilaDatasource;

    @Bean
    public Job sakilaActorJob(){
        return jobBuilderFactory.get("sakilaActorJob").start(sakilaActorStep()).build();
    }

    @Bean
    public Step sakilaActorStep(){
        return stepBuilderFactory.get("sakilaActorStep")
                .<Actor,Actor>chunk(10)
                .reader(reader()).writer(writer())
                .build();
    }

    @Bean
    public ItemReader<Actor> reader(){
        return new JdbcCursorItemReaderBuilder<Actor>()
                .name("jdbcActorReader")
                .dataSource(sakilaDatasource)
                .sql(SQL_QUERY_STRING)
                .rowMapper(new ActorRowMapper())
                .build();
    }

    @Bean
    public ItemWriter<Actor> writer(){
        return new ItemWriter<Actor>() {
            @Override
            public void write(List<? extends Actor> list) throws Exception {
                log.info("Num of elements: {}", list.size());
                list.forEach(actor -> log.info("Actor detail: {}", actor));
            }
        };
    }

}
