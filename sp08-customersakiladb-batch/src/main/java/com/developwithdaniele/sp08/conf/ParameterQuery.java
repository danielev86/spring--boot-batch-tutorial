package com.developwithdaniele.sp08.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("queryparameter")
@Data
public class ParameterQuery {
    private String country;
}
