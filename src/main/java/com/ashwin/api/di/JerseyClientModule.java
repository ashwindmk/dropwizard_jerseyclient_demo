package com.ashwin.api.di;

import com.ashwin.JerseyClientDemoConfiguration;
import com.ashwin.api.resource.JerseyClientResource;
import com.ashwin.api.resource.PingResource;
import io.dropwizard.setup.Environment;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import javax.inject.Singleton;
import javax.ws.rs.client.Client;

public class JerseyClientModule extends AbstractBinder {
    private Environment env;
    private JerseyClientDemoConfiguration config;

    public JerseyClientModule(Environment env, JerseyClientDemoConfiguration config) {
        this.env = env;
        this.config = config;
    }

    @Override
    protected void configure() {
        bind(env).to(Environment.class);
        bind(config).to(JerseyClientDemoConfiguration.class);

        bindFactory(JerseyClientFactory.class).to(Client.class).named("jersey-client").in(Singleton.class);

        bind(PingResource.class).to(PingResource.class).in(Singleton.class);
        bind(JerseyClientResource.class).to(JerseyClientResource.class).in(Singleton.class);
    }
}
