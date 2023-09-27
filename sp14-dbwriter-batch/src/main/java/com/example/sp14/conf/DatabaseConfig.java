package com.example.sp14.conf;

import lombok.AllArgsConstructor;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
@AllArgsConstructor
public class DatabaseConfig {

    private SakilaDbConfig sakilaDbConfig;

    private SakilaBackupDbConfig sakilaBackupDbConfig;


    @Bean
    @Primary
    public DataSource dataSource(){
        return DataSourceBuilder.create().url(sakilaDbConfig.getUrl())
                .username(sakilaDbConfig.getUsername())
                .password(sakilaDbConfig.getPassword())
                .build();
    }

    @Bean("sakilaBackupDatasource")
    public DataSource sakilaBackupDatasource(){
        return DataSourceBuilder.create().url(sakilaBackupDbConfig.getUrl())
                .username(sakilaBackupDbConfig.getUsername())
                .password(sakilaBackupDbConfig.getPassword())
                .build();
    }

}
