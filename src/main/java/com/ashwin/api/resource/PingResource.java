package com.ashwin.api.resource;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.Date;

@Path("/ping")
public class PingResource {
    @PostConstruct
    public void onCreate() {
        System.out.println("PingResource: onCreate");
    }

    @GET
    @Path("/")
    public Response ping() {
        System.out.println((new Date()) + " | ping | thread: " + Thread.currentThread().getName());
        try {
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Response.ok("pong").build();
    }

    @PreDestroy
    public void onDestroy() {
        System.out.println("PingResource: onDestroy");
    }
}
