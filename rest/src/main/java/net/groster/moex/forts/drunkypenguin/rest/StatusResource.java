package net.groster.moex.forts.drunkypenguin.rest;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("status")
@Singleton
public class StatusResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String get() {
        return "REST service of drunkyPenguin was started and working!";
    }
}
