package com.developwithdaniele.sp11.conf;

import com.developwithdaniele.sp11.dto.FilmDTO;
import com.developwithdaniele.sp11.processor.FilmExtractProcessor;
import com.developwithdaniele.sp11.repository.IFilmRepository;
import com.developwithdaniele.sp11.repository.model.Film;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
@AllArgsConstructor
public class FilmExtractorDataJob {

    private JobBuilderFactory jobBuilderFactory;

    private StepBuilderFactory stepBuilderFactory;

    private IFilmRepository filmRepository;

    @Bean
    public Job filmExtractorJob(){
        return jobBuilderFactory.get("filmExtractorJob").start(filmExtractorStep()).build();
    }

    @Bean
    public Step filmExtractorStep(){
        return this.stepBuilderFactory.get("filmExtractorStep")
                .<Film,FilmDTO>chunk(20)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    @StepScope
    public RepositoryItemReader<Film> reader(){
        RepositoryItemReader<Film> reader = new RepositoryItemReader<>();
        reader.setName("filmRepitoryReader");
        reader.setMethodName("findAll");
        reader.setRepository(filmRepository);
        reader.setArguments(new ArrayList<>());
        Map<String, Sort.Direction> map = new HashMap<>();
        map.put("title", Sort.Direction.ASC);
        reader.setSort(map);
        return reader;
    }

    @Bean
    public ItemProcessor<Film, FilmDTO> processor(){
        return new FilmExtractProcessor();
    }

    @Bean
    public FlatFileItemWriter<FilmDTO> writer(){
        FlatFileItemWriter<FilmDTO> writer = new FlatFileItemWriter<>();
        //writer.setName("filmExtractorWriter");
        writer.setResource(new FileSystemResource("output/film.csv"));

        BeanWrapperFieldExtractor<FilmDTO> lineExtractor = new BeanWrapperFieldExtractor<>();
        lineExtractor.setNames(new String[]{"filmId", "title", "releaseDate", "actors"});

        DelimitedLineAggregator<FilmDTO> aggregator = new DelimitedLineAggregator<>();
        aggregator.setDelimiter(";");
        aggregator.setFieldExtractor(lineExtractor);

        writer.setLineAggregator(aggregator);
        return writer;
    }
}
