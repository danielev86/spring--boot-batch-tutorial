package com.developwithhomer.sp03app.conf;

import com.developwithhomer.sp03app.reader.ElementsReader;
import com.developwithhomer.sp03app.writer.ElementsLogWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChunckServiceJob {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public ItemReader<String> reader(){
        return new ElementsReader();
    }

    @Bean
    public ItemWriter<String> writer(){
        return new ElementsLogWriter();
    }

    @Bean
    public Job chunckJob(){
        return jobBuilderFactory.get("chunckJob").start(chunckStep()).build();
    }

    @Bean
    public Step chunckStep(){
        return this.stepBuilderFactory.get("chunckStep")
                .<String, String>chunk(10)
                .reader(reader())
                .writer(writer())
                .build();
    }

}
