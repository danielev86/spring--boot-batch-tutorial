package com.developwithdaniele.sp08.conf;

import lombok.AllArgsConstructor;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
@AllArgsConstructor
public class DatasourcesConfiguration {

    private BatchDBConfig batchDBConfig;

    private SakilaDBConfig sakilaDBConfig;

    @Bean
    @Primary
    public DataSource dataSource(){
        return DataSourceBuilder.create()
                .url(batchDBConfig.getUrl())
                .username(batchDBConfig.getUsername())
                .password(batchDBConfig.getPassword())
                .build();
    }

    @Bean("sakilaDatasource")
    public DataSource sakilaDatasource(){
        return DataSourceBuilder.create()
                .url(sakilaDBConfig.getUrl())
                .username(sakilaDBConfig.getUsername())
                .password(sakilaDBConfig.getPassword())
                .build();
    }

}
