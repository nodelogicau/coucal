package org.coucal.api.controller.repository.observance;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.Response;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.RecurrenceId;
import net.fortuna.ical4j.model.property.Uid;
import org.coucal.api.controller.ICalendarResponseVariants;

import java.util.Collections;

public interface ObservanceOperations extends ICalendarResponseVariants {

    @GET
    default Response listObservances(@PathParam("repository") String repository, @Context Request req) {
        return ok(Collections::emptyList, req);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, "application/icalendar"})
    default Response createObservance(@PathParam("repository") String repository, VEvent observance, @Context Request req) {
        return ok(() -> observance, req);
    }

    @GET
    @Path("{uid}")
    default Response getObservance(@PathParam("repository") String repository, @PathParam("uid") String uid, @Context Request req) {
        VEvent observance = new VEvent().withProperty(new Uid(uid)).getFluentTarget();
        return ok(() -> observance, req);
    }

    @GET
    @Path("{uid}/{recurrenceId}")
    default Response getObservanceOccurrence(@PathParam("repository") String repository, @PathParam("uid") String uid,
                                        @PathParam("recurrenceId") String recurrenceId, @Context Request req) {
        VEvent observance = new VEvent().withProperty(new Uid(uid)).withProperty(new RecurrenceId<>(recurrenceId))
                .getFluentTarget();
        return ok(() -> observance, req);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{uid}")
    default Response updateObservance(@PathParam("repository") String repository, @PathParam("uid") String uid, VEvent observance,
                                 @Context Request req) {
        observance.add(new Uid(uid));
        return ok(() -> observance, req);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{uid}/{recurrenceId}")
    default Response updateObservanceOccurrence(@PathParam("repository") String repository, @PathParam("uid") String uid,
                                           @PathParam("recurrenceId") String recurrenceId, VEvent observance,
                                           @Context Request req) {
        observance.add(new Uid(uid)).add(new RecurrenceId<>(recurrenceId));
        return ok(() -> observance, req);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{uid}")
    default Response setObservance(@PathParam("repository") String repository, @PathParam("uid") String uid, VEvent observance,
                              @Context Request req) {
        observance.add(new Uid(uid));
        return ok(() -> observance, req);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{uid}/{recurrenceId}")
    default Response setObservanceOccurrence(@PathParam("repository") String repository, @PathParam("uid") String uid,
                                        @PathParam("recurrenceId") String recurrenceId, VEvent observance,
                                        @Context Request req) {
        observance.add(new Uid(uid)).add(new RecurrenceId<>(recurrenceId));
        return ok(() -> observance, req);
    }

    @DELETE
    @Path("{uid}")
    default Response deleteObservance(@PathParam("repository") String repository, @PathParam("uid") String uid,
                                 @Context Request req) {
        VEvent observance = new VEvent().withProperty(new Uid(uid)).getFluentTarget();
        return ok(() -> observance, req);
    }

    @DELETE
    @Path("{uid}/{recurrenceId}")
    default Response deleteObservanceOccurrence(@PathParam("repository") String repository, @PathParam("uid") String uid,
                                           @PathParam("recurrenceId") String recurrenceId, @Context Request req) {
        VEvent observance = new VEvent().withProperty(new Uid(uid)).withProperty(new RecurrenceId<>(recurrenceId))
                .getFluentTarget();
        return ok(() -> observance, req);
    }
}
