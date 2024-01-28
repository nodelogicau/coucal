package org.coucal.api.controller.repository.alarm;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.Response;
import net.fortuna.ical4j.model.component.VAlarm;
import net.fortuna.ical4j.model.component.VLocation;
import org.coucal.api.controller.ICalendarResponseVariants;

import java.util.Collections;

public interface AlarmOperations extends ICalendarResponseVariants {

    @GET
    default Response listAlarms(@PathParam("repository") String repository, @PathParam("c") String component,
                                @Context Request req) {
        return ok(Collections::emptyList, req);
    }

    @GET
    @Path("{a}/locations")
    default Response listAlarmLocations(@PathParam("repository") String repository, @PathParam("c") String component,
                                        @PathParam("a") String alarm, @Context Request req) {
        return ok(() -> null, req);
    }

    @POST
    default Response createAlarm(@PathParam("repository") String repository, @PathParam("c") String component,
                                 VAlarm alarm, @Context Request req) {
        return ok(() -> null, req);
    }

    @POST
    @Path("{a}/locations")
    default Response createAlarmLocation(@PathParam("repository") String repository, @PathParam("c") String component,
                                         @PathParam("a") String alarm, VLocation location, @Context Request req) {
        return ok(() -> null, req);
    }

    @GET
    @Path("{uid}")
    default Response getAlarm(@PathParam("repository") String repository, @PathParam("c") String component,
                              @PathParam("uid") String uid, @Context Request req) {
        return ok(() -> null, req);
    }

    @GET
    @Path("{a}/locations/{uid}")
    default Response getAlarmLocation(@PathParam("repository") String repository, @PathParam("c") String component,
                                      @PathParam("a") String alarm, @PathParam("uid") String uid,
                                      @Context Request req) {
        return ok(() -> null, req);
    }

    @POST
    @Path("{uid}")
    default Response updateAlarm(@PathParam("repository") String repository, @PathParam("c") String component,
                                 @PathParam("uid") String uid, VAlarm alarm, @Context Request req) {
        return ok(() -> null, req);
    }

    @POST
    @Path("{a}/locations/{uid}")
    default Response updateAlarmLocation(@PathParam("repository") String repository, @PathParam("c") String component,
                                         @PathParam("a") String alarm, @PathParam("uid") String uid,
                                         VLocation location, @Context Request req) {
        return ok(() -> null, req);
    }

    @PUT
    @Path("{uid}")
    default Response setAlarm(@PathParam("repository") String repository, @PathParam("c") String component,
                              @PathParam("uid") String uid, VAlarm alarm, @Context Request req) {
        return ok(() -> null, req);
    }

    @PUT
    @Path("{a}/locations/{uid}")
    default Response setAlarmLocation(@PathParam("repository") String repository, @PathParam("c") String component,
                                      @PathParam("a") String alarm, @PathParam("uid") String uid,
                                      VLocation location, @Context Request req) {
        return ok(() -> null, req);
    }

    @DELETE
    @Path("{uid}")
    default Response deleteAlarm(@PathParam("repository") String repository, @PathParam("c") String component,
                                 @PathParam("uid") String uid, @Context Request req) {
        return ok(() -> null, req);
    }

    @DELETE
    @Path("{a}/locations/{uid}")
    default Response deleteAlarmLocation(@PathParam("repository") String repository, @PathParam("c") String component,
                                         @PathParam("a") String alarm, @PathParam("uid") String uid,
                                         @Context Request req) {
        return ok(() -> null, req);
    }
}
