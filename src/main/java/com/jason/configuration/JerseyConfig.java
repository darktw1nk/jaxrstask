package com.jason.configuration;

import com.jason.controller.AccountRestService;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(AccountRestService.class);
    }

}
