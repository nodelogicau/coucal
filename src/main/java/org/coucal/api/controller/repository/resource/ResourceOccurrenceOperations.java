package org.coucal.api.controller.repository.resource;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.Response;
import net.fortuna.ical4j.model.component.VResource;
import net.fortuna.ical4j.model.property.Uid;
import org.coucal.api.controller.ICalendarResponseVariants;

import java.util.Collections;

public interface ResourceOccurrenceOperations extends ICalendarResponseVariants {

    @GET
    default Response listResources(@PathParam("repository") String repository, @PathParam("c") String component,
                                   @PathParam("recurrenceId") String recurrenceId, @Context Request req) {
        return ok(Collections::emptyList, req);
    }

    @POST
    default Response createResource(@PathParam("repository") String repository, @PathParam("c") String component,
                                    @PathParam("recurrenceId") String recurrenceId, VResource resource,
                                    @Context Request req) {
        return ok(() -> resource, req);
    }

    @GET
    @Path("{uid}")
    default Response getResource(@PathParam("repository") String repository, @PathParam("c") String component,
                                 @PathParam("recurrenceId") String recurrenceId, @PathParam("uid") String uid,
                                 @Context Request req) {
        VResource resource = new VResource().add(new Uid(uid));
        return ok(() -> resource, req);
    }

    @POST
    @Path("{uid}")
    default Response updateResource(@PathParam("repository") String repository, @PathParam("c") String component,
                                    @PathParam("recurrenceId") String recurrenceId, @PathParam("uid") String uid,
                                    VResource resource, @Context Request req) {
        return ok(() -> resource, req);
    }

    @PUT
    @Path("{uid}")
    default Response setResource(@PathParam("repository") String repository, @PathParam("c") String component,
                                 @PathParam("recurrenceId") String recurrenceId, @PathParam("uid") String uid,
                                 VResource resource, @Context Request req) {
        return ok(() -> resource, req);
    }

    @DELETE
    @Path("{uid}")
    default Response deleteResource(@PathParam("repository") String repository, @PathParam("c") String component,
                                    @PathParam("recurrenceId") String recurrenceId, @PathParam("uid") String uid,
                                    @Context Request req) {
        VResource resource = new VResource().add(new Uid(uid));
        return ok(() -> resource, req);
    }
}
