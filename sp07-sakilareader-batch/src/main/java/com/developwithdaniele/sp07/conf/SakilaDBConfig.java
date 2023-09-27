package com.developwithdaniele.sp07.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("sakiladatasource")
@Data
public class SakilaDBConfig {

    private String url;
    private String username;
    private String password;

}
