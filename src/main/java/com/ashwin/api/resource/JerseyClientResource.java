package com.ashwin.api.resource;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;
import java.util.Date;

@Path("/")
public class JerseyClientResource {
    @Inject
    @Named("jersey-client")
    private Client client;

    @GET
    @Path("/task")
    public Response task() {
        System.out.println((new Date()) + " | task | start | thread: " + Thread.currentThread().getName());
        String url = "http://localhost:9090/process";
        Response response = client.target(url).request().get();
        System.out.println((new Date()) + " | task | finish | thread: " + Thread.currentThread().getName());
        return response;
    }
}
