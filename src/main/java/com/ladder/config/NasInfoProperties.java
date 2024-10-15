package com.ladder.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("nas")
public class NasInfoProperties {

    private String url;
    private int port;
    private String id;
    private String password;
}
