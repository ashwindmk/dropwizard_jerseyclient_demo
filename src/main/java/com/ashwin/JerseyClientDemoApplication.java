package com.ashwin;

import com.ashwin.api.di.JerseyClientModule;
import com.ashwin.api.resource.JerseyClientResource;
import com.ashwin.api.resource.PingResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

class JerseyClientDemoApplication extends Application<JerseyClientDemoConfiguration> {
    public static void main(String[] args) throws Exception {
        new JerseyClientDemoApplication().run(args);
    }

    @Override
    public void run(JerseyClientDemoConfiguration config, Environment env) throws Exception {
        env.jersey().getResourceConfig().register(new JerseyClientModule(env, config));
        env.jersey().register(PingResource.class);
        env.jersey().register(JerseyClientResource.class);
    }
}
