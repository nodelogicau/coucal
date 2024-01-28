package org.coucal.api.controller.repository.request;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.Response;
import net.fortuna.ical4j.model.component.VToDo;
import net.fortuna.ical4j.model.property.Uid;
import org.coucal.api.controller.ICalendarResponseVariants;

import java.util.Collections;

public interface RequestOperations extends ICalendarResponseVariants {

    @GET
    default Response listRequests(@PathParam("repository") String repository, @Context Request req) {
        return ok(Collections::emptyList, req);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, "application/icalendar"})
    default Response createRequest(@PathParam("repository") String repository, VToDo request, @Context Request req) {
        return ok(() -> request, req);
    }

    @GET
    @Path("{uid}")
    default Response getRequest(@PathParam("repository") String repository, @PathParam("uid") String uid, @Context Request req) {
        VToDo request = new VToDo().withProperty(new Uid(uid)).getFluentTarget();
        return ok(() -> request, req);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{uid}")
    default Response updateRequest(@PathParam("repository") String repository, @PathParam("uid") String uid, VToDo request,
                                 @Context Request req) {
        request.add(new Uid(uid));
        return ok(() -> request, req);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{uid}")
    default Response setRequest(@PathParam("repository") String repository, @PathParam("uid") String uid, VToDo request,
                              @Context Request req) {
        request.add(new Uid(uid));
        return ok(() -> request, req);
    }

    @DELETE
    @Path("{uid}")
    default Response deleteRequest(@PathParam("repository") String repository, @PathParam("uid") String uid,
                                 @Context Request req) {
        VToDo request = new VToDo().withProperty(new Uid(uid)).getFluentTarget();
        return ok(() -> request, req);
    }
}
