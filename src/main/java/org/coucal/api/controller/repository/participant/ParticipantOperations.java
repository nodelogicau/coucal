package org.coucal.api.controller.repository.participant;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.Response;
import net.fortuna.ical4j.model.component.Participant;
import net.fortuna.ical4j.model.property.Uid;
import org.coucal.api.controller.ICalendarResponseVariants;

import java.util.Collections;

public interface ParticipantOperations extends ICalendarResponseVariants {

    @GET
    default Response listParticipants(@PathParam("repository") String repository, @PathParam("c") String component,
                                      @Context Request req) {
        return ok(Collections::emptyList, req);
    }

    @POST
    default Response createParticipant(@PathParam("repository") String repository, @PathParam("c") String component,
                                       Participant participant, @Context Request req) {
        return ok(() -> participant, req);
    }

    @GET
    @Path("{uid}")
    default Response getParticipant(@PathParam("repository") String repository, @PathParam("c") String component,
                                    @PathParam("uid") String uid, @Context Request req) {
        Participant participant = new Participant().add(new Uid(uid));
        return ok(() -> participant, req);
    }

    @POST
    @Path("{uid}")
    default Response updateParticipant(@PathParam("repository") String repository, @PathParam("c") String component,
                                       @PathParam("uid") String uid, Participant participant,
                                       @Context Request req) {
        return ok(() -> participant, req);
    }

    @PUT
    @Path("{uid}")
    default Response setParticipant(@PathParam("repository") String repository, @PathParam("c") String component,
                                    @PathParam("uid") String uid, Participant participant,
                                    @Context Request req) {
        return ok(() -> participant, req);
    }

    @DELETE
    @Path("{uid}")
    default Response deleteParticipant(@PathParam("repository") String repository, @PathParam("c") String component,
                                       @PathParam("uid") String uid, @Context Request req) {
        Participant participant = new Participant().add(new Uid(uid));
        return ok(() -> participant, req);
    }
}
