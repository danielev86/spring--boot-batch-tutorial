package com.developwithdaniele.sp10.conf;

import com.developwithdaniele.sp10.repository.IFilmRepository;
import com.developwithdaniele.sp10.repository.model.Film;
import com.developwithdaniele.sp10.repository.model.FilmActor;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@Slf4j
@AllArgsConstructor
public class FilmExtractorServiceJob {

    private JobBuilderFactory jobBuilderFactory;

    private StepBuilderFactory stepBuilderFactory;

    private IFilmRepository filmRepository;

    @Bean
    public Job filmJobExtractor(){
        return this.jobBuilderFactory.get("filmJobExtractor").start(filmExtractorStep()).build();
    }

    @Bean
    public Step filmExtractorStep(){
        return this.stepBuilderFactory.get("filmExtractorStep").<Film, Film>chunk(10).reader(reader()).writer(writer()).build();
    }

    @Bean
    @StepScope
    public RepositoryItemReader<Film> reader(){
        RepositoryItemReader<Film> reader = new RepositoryItemReader<>();
        reader.setName("filmRepoReader");
        reader.setMethodName("findAll");
        reader.setArguments(new ArrayList<>());
        Map<String, Sort.Direction> mapSort = new HashMap<>();
        mapSort.put("filmId", Sort.Direction.ASC);
        reader.setSort(mapSort);
        reader.setRepository(filmRepository);
        return reader;
    }

    @Bean
    public ItemWriter<Film> writer(){
        return new ItemWriter<Film>() {
            @Override
            public void write(List<? extends Film> list) throws Exception {
                for (Film film : list){
                    StringBuilder actor = new StringBuilder();
                    int i = 1;
                    for (FilmActor filmActor : film.getActors()){
                        actor.append(filmActor.getActor().getFirstName() + " " + filmActor.getActor().getLastName());
                        if (i != film.getActors().size()){
                            actor.append(",");
                        }
                        i++;
                    }
                    log.info("{};{};{}", film.getFilmId(), film.getTitle(),actor);
                }
            }
        };
    }

}
