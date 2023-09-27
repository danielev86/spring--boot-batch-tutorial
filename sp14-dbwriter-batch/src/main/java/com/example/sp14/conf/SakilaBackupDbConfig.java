package com.example.sp14.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("sakilabackupdatasource")
@Data
public class SakilaBackupDbConfig {

    private String url;
    private String username;
    private String password;

}
