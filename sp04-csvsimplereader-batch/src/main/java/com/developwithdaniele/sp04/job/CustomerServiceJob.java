package com.developwithdaniele.sp04.job;

import com.developwithdaniele.sp04.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.util.List;

@Configuration
@Slf4j
public class CustomerServiceJob {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Value("input/customers.csv")
    private Resource resource;

    @Bean
    public Job customerParsingJob(){
        return jobBuilderFactory.get("customerParsingJob").start(customerParsingStep()).build();
    }

    @Bean
    public Step customerParsingStep(){
        return stepBuilderFactory.get("customerParsingStep")
                .<Customer,Customer>chunk(10)
                .reader(reader())
                .writer(writer())
                .build();
    }

    @Bean
    public ItemReader<Customer> reader(){
        DefaultLineMapper<Customer> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames(new String[]{"firstName", "lastName", "email", "gender", "ipAddress"});
        BeanWrapperFieldSetMapper<Customer> wrapperFields = new BeanWrapperFieldSetMapper<>();
        wrapperFields.setTargetType(Customer.class);
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(wrapperFields);

        FlatFileItemReader<Customer> reader = new FlatFileItemReader<>();
        reader.setLinesToSkip(0);
        reader.setResource(resource);
        reader.setLineMapper(lineMapper);
        return reader;
    }

    @Bean
    public ItemWriter<Customer> writer(){
        return new ItemWriter<Customer>() {
            @Override
            public void write(List<? extends Customer> list) throws Exception {
                list.forEach(customer -> log.info("Customer data: {}", customer));
            }
        };
    }

}
