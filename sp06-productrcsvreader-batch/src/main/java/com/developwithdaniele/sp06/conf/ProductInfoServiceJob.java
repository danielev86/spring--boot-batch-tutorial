package com.developwithdaniele.sp06.conf;

import com.developwithdaniele.sp06.dto.ProductDTO;
import com.developwithdaniele.sp06.model.Product;
import com.developwithdaniele.sp06.processor.ProductManipulateProcessor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
@AllArgsConstructor
@Slf4j
public class ProductInfoServiceJob {

    private JobBuilderFactory jobBuilderFactory;

    private StepBuilderFactory stepBuilderFactory;

    private ResourceLoader resourceLoader;

    @Bean
    public Job productDataJob(){
        return this.jobBuilderFactory.get("productDataJob").start(productManipulateStep()).build();
    }

    @Bean
    public Step productManipulateStep(){
        return this.stepBuilderFactory.get("productManipulateStep")
                .<ProductDTO,Product>chunk(10)
                .reader(reader())
                .processor(productManipulateProcessor())
                .writer(writer())
                .build();
    }

    @Bean
    public Resource productCsvResource(){
        return resourceLoader.getResource("data/products.csv");
    }

    @Bean
    public ItemReader<ProductDTO> reader(){
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames(new String[]{"productId", "productName"
                , "category", "price"
                , "quantity", "manufacturer"
                , "expirationDate", "weight", "available"});

        BeanWrapperFieldSetMapper<ProductDTO> wrapperField = new BeanWrapperFieldSetMapper<>();
        wrapperField.setTargetType(ProductDTO.class);

        DefaultLineMapper<ProductDTO> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(wrapperField);

        FlatFileItemReader<ProductDTO> reader = new FlatFileItemReader<>();
        reader.setResource(productCsvResource());
        reader.setLineMapper(lineMapper);
        reader.setLinesToSkip(0);
        return reader;
    }

    @Bean
    public ItemProcessor<ProductDTO, Product> productManipulateProcessor(){
        return new ProductManipulateProcessor();
    }

    @Bean
    public ItemWriter<Product> writer(){
        return new ItemWriter<Product>() {
            @Override
            public void write(List<? extends Product> list) throws Exception {
                List<Product> products = list.stream().filter(prod-> prod.isAvailable()).collect(Collectors.toList());
                products.forEach(prod -> log.info("Product detail: {}", prod));
            }
        };
    }
}
