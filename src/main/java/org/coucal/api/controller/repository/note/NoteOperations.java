package org.coucal.api.controller.repository.note;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.Response;
import net.fortuna.ical4j.model.component.VJournal;
import net.fortuna.ical4j.model.property.Uid;
import org.coucal.api.controller.ICalendarResponseVariants;

import java.util.Collections;

public interface NoteOperations extends ICalendarResponseVariants {

    @GET
    default Response listNotes(@PathParam("repository") String repository, @Context Request req) {
        return ok(Collections::emptyList, req);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, "application/icalendar"})
    default Response createNote(@PathParam("repository") String repository, VJournal note, @Context Request req) {
        return ok(() -> note, req);
    }

    @GET
    @Path("{uid}")
    default Response getNote(@PathParam("repository") String repository, @PathParam("uid") String uid, @Context Request req) {
        VJournal note = new VJournal().withProperty(new Uid(uid)).getFluentTarget();
        return ok(() -> note, req);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{uid}")
    default Response updateNote(@PathParam("repository") String repository, @PathParam("uid") String uid, VJournal note,
                                 @Context Request req) {
        note.add(new Uid(uid));
        return ok(() -> note, req);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{uid}")
    default Response setNote(@PathParam("repository") String repository, @PathParam("uid") String uid, VJournal note,
                              @Context Request req) {
        note.add(new Uid(uid));
        return ok(() -> note, req);
    }

    @DELETE
    @Path("{uid}")
    default Response deleteNote(@PathParam("repository") String repository, @PathParam("uid") String uid,
                                 @Context Request req) {
        VJournal note = new VJournal().withProperty(new Uid(uid)).getFluentTarget();
        return ok(() -> note, req);
    }
}
