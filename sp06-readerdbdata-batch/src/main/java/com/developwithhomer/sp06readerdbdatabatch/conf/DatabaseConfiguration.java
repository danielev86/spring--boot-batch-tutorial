package com.developwithhomer.sp06readerdbdatabatch.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfiguration {

    @Autowired
    private BatchDBSourceConfig batchDBConf;

    @Autowired
    private SakilaDBSourceConfig sakilaDBConf;

    @Bean
    @Primary
    public DataSource dataSource(){
        return DataSourceBuilder.create()
                .url(batchDBConf.getUrl())
                .username(batchDBConf.getUsername())
                .password(batchDBConf.getPassword())
                .build();
    }

    @Bean
    public DataSource dataSourceSakila(){
        return DataSourceBuilder.create()
                .url(sakilaDBConf.getUrl())
                .username(sakilaDBConf.getUsername())
                .password(sakilaDBConf.getPassword())
                .build();
    }


}
