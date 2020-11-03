package com.ashwin.api.di;

import com.ashwin.JerseyClientDemoConfiguration;
import com.ashwin.api.resource.PingResource;
import com.codahale.metrics.InstrumentedExecutorService;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.httpclient.HttpClientMetricNameStrategies;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.setup.Environment;
import org.glassfish.hk2.api.Factory;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class JerseyClientFactory implements Factory<Client> {
    private Environment env;
    private JerseyClientDemoConfiguration config;
    private ExecutorService service;

    @Inject
    public JerseyClientFactory(Environment env, JerseyClientDemoConfiguration config) {
        this.env = env;
        this.config = config;
    }

    @Override
    public Client provide() {
        service = new InstrumentedExecutorService(
                env.lifecycle()
                        .executorService("jersey-client-%d")
                        .rejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy())
                        .minThreads(config.getJerseyClient().getMinThreads())
                        .maxThreads(config.getJerseyClient().getMaxThreads())
                        .workQueue(new LinkedBlockingQueue<Runnable>(config.getJerseyClient().getWorkQueueSize()))
                        .build(),
                env.metrics(),
                MetricRegistry.name(PingResource.class, "jersey_client_service")
        );

        final Client client = new JerseyClientBuilder(env.metrics())
                        .using(env)
                        .using(service)
                        .using(config.getJerseyClient())
                        .using(HttpClientMetricNameStrategies.HOST_AND_METHOD)
                        .build("jersey_client_demo");

        return client;
    }

    @Override
    public void dispose(Client client) {
        if (service != null) {
            service.shutdown();
            try {
                if (!service.awaitTermination(60, TimeUnit.SECONDS)) {
                    System.err.println("Timeout while waiting for graceful shutdown of executor service");
                }
            } catch (InterruptedException e) {
                System.err.println("Exception while graceful shutdown of executor service");
            }
        }
        client.close();
    }
}
