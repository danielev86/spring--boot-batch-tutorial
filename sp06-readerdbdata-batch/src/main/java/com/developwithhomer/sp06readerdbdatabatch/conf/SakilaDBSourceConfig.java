package com.developwithhomer.sp06readerdbdatabatch.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@ConfigurationProperties("sakiladbsource")
@Data
public class SakilaDBSourceConfig {

    private String url;
    private String username;
    private String password;

}
