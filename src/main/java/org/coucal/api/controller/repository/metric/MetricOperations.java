package org.coucal.api.controller.repository.metric;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.Response;
import net.fortuna.ical4j.model.component.VJournal;
import net.fortuna.ical4j.model.property.RecurrenceId;
import net.fortuna.ical4j.model.property.Uid;
import org.coucal.api.controller.ICalendarResponseVariants;

import java.util.Collections;

public interface MetricOperations extends ICalendarResponseVariants {

    @GET
    default Response listMetrics(@PathParam("repository") String repository, @Context Request req) {
        return ok(Collections::emptyList, req);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, "application/icalendar"})
    default Response createMetric(@PathParam("repository") String repository, VJournal metric, @Context Request req) {
        return ok(() -> metric, req);
    }

    @GET
    @Path("{uid}")
    default Response getMetric(@PathParam("repository") String repository, @PathParam("uid") String uid, @Context Request req) {
        VJournal metric = new VJournal().withProperty(new Uid(uid)).getFluentTarget();
        return ok(() -> metric, req);
    }

    @GET
    @Path("{uid}/{recurrenceId}")
    default Response getMetricOccurrence(@PathParam("repository") String repository, @PathParam("uid") String uid,
                                        @PathParam("recurrenceId") String recurrenceId, @Context Request req) {
        VJournal metric = new VJournal().withProperty(new Uid(uid)).withProperty(new RecurrenceId<>(recurrenceId))
                .getFluentTarget();
        return ok(() -> metric, req);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{uid}")
    default Response updateMetric(@PathParam("repository") String repository, @PathParam("uid") String uid, VJournal metric,
                                 @Context Request req) {
        metric.add(new Uid(uid));
        return ok(() -> metric, req);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{uid}/{recurrenceId}")
    default Response updateMetricOccurrence(@PathParam("repository") String repository, @PathParam("uid") String uid,
                                           @PathParam("recurrenceId") String recurrenceId, VJournal metric,
                                           @Context Request req) {
        metric.add(new Uid(uid)).add(new RecurrenceId<>(recurrenceId));
        return ok(() -> metric, req);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{uid}")
    default Response setMetric(@PathParam("repository") String repository, @PathParam("uid") String uid, VJournal metric,
                              @Context Request req) {
        metric.add(new Uid(uid));
        return ok(() -> metric, req);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{uid}/{recurrenceId}")
    default Response setMetricOccurrence(@PathParam("repository") String repository, @PathParam("uid") String uid,
                                        @PathParam("recurrenceId") String recurrenceId, VJournal metric,
                                        @Context Request req) {
        metric.add(new Uid(uid)).add(new RecurrenceId<>(recurrenceId));
        return ok(() -> metric, req);
    }

    @DELETE
    @Path("{uid}")
    default Response deleteMetric(@PathParam("repository") String repository, @PathParam("uid") String uid,
                                 @Context Request req) {
        VJournal metric = new VJournal().withProperty(new Uid(uid)).getFluentTarget();
        return ok(() -> metric, req);
    }

    @DELETE
    @Path("{uid}/{recurrenceId}")
    default Response deleteMetricOccurrence(@PathParam("repository") String repository, @PathParam("uid") String uid,
                                           @PathParam("recurrenceId") String recurrenceId, @Context Request req) {
        VJournal metric = new VJournal().withProperty(new Uid(uid)).withProperty(new RecurrenceId<>(recurrenceId))
                .getFluentTarget();
        return ok(() -> metric, req);
    }
}
