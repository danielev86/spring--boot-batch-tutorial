package com.developwithdaniele.sp05.conf;

import com.developwithdaniele.sp05.model.Employee;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
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
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.util.List;

@Configuration
@Slf4j
@AllArgsConstructor
public class EmployeeServiceJob {

    private JobBuilderFactory jobBuilderFactory;

    private StepBuilderFactory stepBuilderFactory;

    private ResourceLoader resourceLoader;

    @Bean
    public Resource employeeResource(){
        return resourceLoader.getResource("input/employees.csv");
    }

    @Bean
    public Job employeeJob(){
        return jobBuilderFactory.get("employeeJob").start(employeStep()).build();
    }

    @Bean
    public Step employeStep(){
        return stepBuilderFactory.get("employeeStep")
                .<Employee, Employee>chunk(10)
                .reader(reader())
                .writer(writer())
                .build();
    }

    @Bean
    public ItemReader<Employee> reader(){
        DefaultLineMapper<Employee> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames(new String[]{"firstName", "lastName", "email", "gender", "department", "salary"});
        BeanWrapperFieldSetMapper<Employee> wrapperField = new BeanWrapperFieldSetMapper<>();
        wrapperField.setTargetType(Employee.class);
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(wrapperField);

        FlatFileItemReader<Employee> reader = new FlatFileItemReader<>();
        reader.setLinesToSkip(0);
        reader.setResource(employeeResource());
        reader.setLineMapper(lineMapper);

        return reader;
    }

    @Bean
    public ItemWriter<Employee> writer(){
        return new ItemWriter<Employee>() {
            @Override
            public void write(List<? extends Employee> list) throws Exception {
                list.forEach(employee -> log.info("Employee data: {}", employee));
            }
        };
    }

}
