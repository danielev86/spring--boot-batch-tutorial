package com.developwithdaniele.sp11;

import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
@AllArgsConstructor
public class Sp11Application implements CommandLineRunner {

    private JobLauncher jobLauncher;

    private Job job;

    public static void main(String[] args) {
        SpringApplication.run(Sp11Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("JobId", "sp11" + String.valueOf(System.currentTimeMillis()))
                .toJobParameters();
        jobLauncher.run(job,jobParameters);
    }
}
