package com.ashwin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;

import javax.validation.Valid;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JerseyClientDemoConfiguration extends Configuration {
    @Valid
    private JerseyClientConfiguration jerseyClient = new JerseyClientConfiguration();

    public JerseyClientConfiguration getJerseyClient() {
        return jerseyClient;
    }
}
