package org.coucal.api.controller.repository.event;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.Response;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.RecurrenceId;
import net.fortuna.ical4j.model.property.Uid;
import org.coucal.api.controller.ICalendarResponseVariants;
import org.coucal.api.controller.workspace.WorkspaceOperations;

import java.util.Collections;

public interface EventOperations extends ICalendarResponseVariants {

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(summary = "List events",
            description = "List event objects in the specified repository")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "Not found - The repository was not found")
    })
    default Response listEvents(@PathParam("repository") String repository,
                                @CookieParam(WorkspaceOperations.WORKSPACE_COOKIE) String workspace,
                                @Context Request req) {
        return ok(Collections::emptyList, req);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, "application/icalendar"})
    @Operation(summary = "Create a new event object",
            description = "Create a new event object from the specified payload")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "404", description = "Not found - The repository was not found")
    })
    default Response createEvent(@PathParam("repository") String repository, VEvent event, @Context Request req) {
        return ok(() -> event, req);
    }

    @GET
    @Path("{uid}")
    @Operation(summary = "Retrieve a single event",
            description = "Return the latest revision of the event object with the specified UID property")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "Not found - The event was not found")
    })
    default Response getEvent(@PathParam("repository") String repository, @PathParam("uid") String uid, @Context Request req) {
        VEvent event = new VEvent().withProperty(new Uid(uid)).getFluentTarget();
        return ok(() -> event, req);
    }

    @GET
    @Path("{uid}/{recurrenceId}")
    @Operation(summary = "Retrieve a single occurrence of a recurring event",
            description = "Return the latest revision of the event occurrence with the specified UID and RECURRENCE-ID properties")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "Not found - The event occurrence was not found")
    })
    default Response getEventOccurrence(@PathParam("repository") String repository, @PathParam("uid") String uid,
                                        @PathParam("recurrenceId") String recurrenceId, @Context Request req) {
        VEvent event = new VEvent().withProperty(new Uid(uid)).withProperty(new RecurrenceId<>(recurrenceId))
                .getFluentTarget();
        return ok(() -> event, req);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{uid}")
    @Operation(summary = "Create a new revision of an existing event object",
            description = "Create a new revision of an existing event with the specified UID property.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated"),
            @ApiResponse(responseCode = "404", description = "Not found - The event was not found")
    })
    default Response updateEvent(@PathParam("repository") String repository, @PathParam("uid") String uid, VEvent event,
                                 @Context Request req) {
        event.add(new Uid(uid));
        return ok(() -> event, req);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{uid}/{recurrenceId}")
    @Operation(summary = "Create a new revision of an event occurrence for recurring events",
            description = "Create a new revision of an existing event occurrence with the specified UID and RECURRENCE-ID properties.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated"),
            @ApiResponse(responseCode = "404", description = "Not found - The event occurrence was not found")
    })
    default Response updateEventOccurrence(@PathParam("repository") String repository, @PathParam("uid") String uid,
                                           @PathParam("recurrenceId") String recurrenceId, VEvent event,
                                           @Context Request req) {
        event.add(new Uid(uid)).add(new RecurrenceId<>(recurrenceId));
        return ok(() -> event, req);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{uid}")
    @Operation(summary = "Overwrite an event object",
            description = "Replace all revisions of an event (if it exists) with the specified UID property" +
                    " using the specified payload")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully replaced"),
            @ApiResponse(responseCode = "404", description = "Not found - The repository was not found")
    })
    default Response setEvent(@PathParam("repository") String repository, @PathParam("uid") String uid, VEvent event,
                              @Context Request req) {
        event.add(new Uid(uid));
        return ok(() -> event, req);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{uid}/{recurrenceId}")
    @Operation(summary = "Overwrite an event object",
            description = "Replace all revisions of an event occurrence (if it exists) with the specified UID and RECURRENCE-ID properties" +
                    " using the specified payload")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully replaced"),
            @ApiResponse(responseCode = "404", description = "Not found - The repository was not found")
    })
    default Response setEventOccurrence(@PathParam("repository") String repository, @PathParam("uid") String uid,
                                        @PathParam("recurrenceId") String recurrenceId, VEvent event,
                                        @Context Request req) {
        event.add(new Uid(uid)).add(new RecurrenceId<>(recurrenceId));
        return ok(() -> event, req);
    }

    @DELETE
    @Path("{uid}")
    @Operation(summary = "Delete an event object",
            description = "Remove all revisions of an existing event with the specified UID property.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Not found - The repository was not found")
    })
    default Response deleteEvent(@PathParam("repository") String repository, @PathParam("uid") String uid,
                                 @Context Request req) {
        VEvent event = new VEvent().withProperty(new Uid(uid)).getFluentTarget();
        return ok(() -> event, req);
    }

    @DELETE
    @Path("{uid}/{recurrenceId}")
    @Operation(summary = "Delete an event occurrence",
            description = "Remove all revisions of an existing event occurrence with the specified UID and RECURRENCE-ID properties.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Not found - The repository was not found")
    })
    default Response deleteEventOccurrence(@PathParam("repository") String repository, @PathParam("uid") String uid,
                                           @PathParam("recurrenceId") String recurrenceId, @Context Request req) {
        VEvent event = new VEvent().withProperty(new Uid(uid)).withProperty(new RecurrenceId<>(recurrenceId))
                .getFluentTarget();
        return ok(() -> event, req);
    }
}
