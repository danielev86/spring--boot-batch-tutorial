package com.developwithdaniele.sp09.conf;

import com.developwithdaniele.sp09.repository.IActorRepository;
import com.developwithdaniele.sp09.repository.model.Actor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@AllArgsConstructor
@Slf4j
public class ActorServiceExtractorJob {

    private JobBuilderFactory jobBuilderFactory;

    private StepBuilderFactory stepBuilderFactory;

    private QueryParamsConfig queryParamsConfig;

    private IActorRepository actorRepository;

    @Bean
    public Job actorExtractorServiceJob(){
        return this.jobBuilderFactory.get("actorExtractorServiceJob").start(actorExtractorStep()).build();
    }

    @Bean
    public Step actorExtractorStep(){
        return this.stepBuilderFactory.get("actorExtractorStep").<Actor,Actor>chunk(5)
                .reader(reader())
                .writer(writer())
                .build();
    }

    @Bean
    @StepScope
    public RepositoryItemReader<Actor> reader(){
        RepositoryItemReader<Actor> reader = new RepositoryItemReader<>();
        reader.setName("actorRepositoryData");

        String firstName = queryParamsConfig.getFirstname();
        if (firstName != null && !firstName.isBlank()){
            reader.setMethodName("findActorsByFirstName");
            reader.setArguments(Collections.singletonList(firstName));
        }else{
            reader.setMethodName("findAll");
        }
        reader.setRepository(actorRepository);
        Map<String, Sort.Direction> mapSort = new HashMap<>();
        mapSort.put("firstName", Sort.Direction.ASC);
        mapSort.put("lastName", Sort.Direction.ASC);
        reader.setSort(mapSort);
        return reader;
    }

    @Bean
    public ItemWriter<Actor> writer(){
        return new ItemWriter<Actor>() {
            @Override
            public void write(List<? extends Actor> list) throws Exception {
                list.forEach(actor -> log.info(actor.getFirstName() + " " + actor.getLastName()));
            }
        };
    }
}
