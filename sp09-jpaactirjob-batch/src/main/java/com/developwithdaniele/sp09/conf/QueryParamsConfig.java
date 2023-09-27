package com.developwithdaniele.sp09.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("queryparams")
@Data
public class QueryParamsConfig {

    private String firstname;

}
