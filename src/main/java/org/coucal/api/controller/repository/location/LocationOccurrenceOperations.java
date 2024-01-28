package org.coucal.api.controller.repository.location;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.Response;
import net.fortuna.ical4j.model.component.VLocation;
import net.fortuna.ical4j.model.property.Uid;
import org.coucal.api.controller.ICalendarResponseVariants;

import java.util.Collections;

public interface LocationOccurrenceOperations extends ICalendarResponseVariants {

    @GET
    default Response listLocations(@PathParam("repository") String repository, @PathParam("c") String component,
                                   @PathParam("recurrenceId") String recurrenceId, @Context Request req) {
        return ok(Collections::emptyList, req);
    }

    @POST
    default Response createLocation(@PathParam("repository") String repository, @PathParam("c") String component,
                                    @PathParam("recurrenceId") String recurrenceId, VLocation location,
                                    @Context Request req) {
        return ok(() -> location, req);
    }

    @GET
    @Path("{uid}")
    default Response getLocation(@PathParam("repository") String repository, @PathParam("c") String component,
                                 @PathParam("recurrenceId") String recurrenceId, @PathParam("uid") String uid,
                                 @Context Request req) {
        VLocation location = new VLocation().withProperty(new Uid(uid)).getFluentTarget();
        return ok(() -> location, req);
    }

    @POST
    @Path("{uid}")
    default Response updateLocation(@PathParam("repository") String repository, @PathParam("c") String component,
                                    @PathParam("recurrenceId") String recurrenceId, @PathParam("uid") String uid,
                                    VLocation location, @Context Request req) {
        return ok(() -> location, req);
    }

    @PUT
    @Path("{uid}")
    default Response setLocation(@PathParam("repository") String repository, @PathParam("c") String component,
                                 @PathParam("recurrenceId") String recurrenceId, @PathParam("uid") String uid,
                                 VLocation location, @Context Request req) {
        return ok(() -> location, req);
    }

    @DELETE
    @Path("{uid}")
    default Response deleteLocation(@PathParam("repository") String repository, @PathParam("c") String component,
                                    @PathParam("recurrenceId") String recurrenceId, @PathParam("uid") String uid,
                                    @Context Request req) {
        VLocation location = new VLocation().withProperty(new Uid(uid)).getFluentTarget();
        return ok(() -> location, req);
    }
}
