package com.developwithdaniele.sp08.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("batchdatasource")
@Data
public class BatchDBConfig {

    private String url;
    private String username;
    private String password;

}
