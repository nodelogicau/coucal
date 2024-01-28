package org.coucal.api.controller.repository.action;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.Response;
import net.fortuna.ical4j.model.component.VToDo;
import net.fortuna.ical4j.model.property.RecurrenceId;
import net.fortuna.ical4j.model.property.Uid;
import org.coucal.api.controller.ICalendarResponseVariants;

import java.util.Collections;

public interface ActionOperations extends ICalendarResponseVariants {

    @GET
    default Response listActions(@PathParam("repository") String repository, @Context Request req) {
        return ok(Collections::emptyList, req);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, "application/icalendar"})
    default Response createAction(@PathParam("repository") String repository, VToDo action, @Context Request req) {
        return ok(() -> action, req);
    }

    @GET
    @Path("{uid}")
    default Response getAction(@PathParam("repository") String repository, @PathParam("uid") String uid, @Context Request req) {
        VToDo action = new VToDo().withProperty(new Uid(uid)).getFluentTarget();
        return ok(() -> action, req);
    }

    @GET
    @Path("{uid}/{recurrenceId}")
    default Response getActionOccurrence(@PathParam("repository") String repository, @PathParam("uid") String uid,
                                        @PathParam("recurrenceId") String recurrenceId, @Context Request req) {
        VToDo action = new VToDo().withProperty(new Uid(uid)).withProperty(new RecurrenceId<>(recurrenceId))
                .getFluentTarget();
        return ok(() -> action, req);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{uid}")
    default Response updateAction(@PathParam("repository") String repository, @PathParam("uid") String uid, VToDo action,
                                 @Context Request req) {
        action.add(new Uid(uid));
        return ok(() -> action, req);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{uid}/{recurrenceId}")
    default Response updateActionOccurrence(@PathParam("repository") String repository, @PathParam("uid") String uid,
                                           @PathParam("recurrenceId") String recurrenceId, VToDo action,
                                           @Context Request req) {
        action.add(new Uid(uid)).add(new RecurrenceId<>(recurrenceId));
        return ok(() -> action, req);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{uid}")
    default Response setAction(@PathParam("repository") String repository, @PathParam("uid") String uid, VToDo action,
                              @Context Request req) {
        action.add(new Uid(uid));
        return ok(() -> action, req);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{uid}/{recurrenceId}")
    default Response setActionOccurrence(@PathParam("repository") String repository, @PathParam("uid") String uid,
                                        @PathParam("recurrenceId") String recurrenceId, VToDo action,
                                        @Context Request req) {
        action.add(new Uid(uid)).add(new RecurrenceId<>(recurrenceId));
        return ok(() -> action, req);
    }

    @DELETE
    @Path("{uid}")
    default Response deleteAction(@PathParam("repository") String repository, @PathParam("uid") String uid,
                                 @Context Request req) {
        VToDo action = new VToDo().withProperty(new Uid(uid)).getFluentTarget();
        return ok(() -> action, req);
    }

    @DELETE
    @Path("{uid}/{recurrenceId}")
    default Response deleteActionOccurrence(@PathParam("repository") String repository, @PathParam("uid") String uid,
                                           @PathParam("recurrenceId") String recurrenceId, @Context Request req) {
        VToDo action = new VToDo().withProperty(new Uid(uid)).withProperty(new RecurrenceId<>(recurrenceId))
                .getFluentTarget();
        return ok(() -> action, req);
    }
}
