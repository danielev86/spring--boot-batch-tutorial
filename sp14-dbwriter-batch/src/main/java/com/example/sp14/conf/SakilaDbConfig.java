package com.example.sp14.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("sakiladatasource")
@Data
public class SakilaDbConfig {

    private String url;
    private String username;
    private String password;

}
