package org.coucal.api.controller.repository.issue;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.Response;
import net.fortuna.ical4j.model.component.VToDo;
import net.fortuna.ical4j.model.property.Uid;
import org.coucal.api.controller.ICalendarResponseVariants;

import java.util.Collections;

public interface IssueOperations extends ICalendarResponseVariants {

    @GET
    default Response listIssues(@PathParam("repository") String repository, @Context Request req) {
        return ok(Collections::emptyList, req);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, "application/icalendar"})
    default Response createIssue(@PathParam("repository") String repository, VToDo issue, @Context Request req) {
        return ok(() -> issue, req);
    }

    @GET
    @Path("{uid}")
    default Response getIssue(@PathParam("repository") String repository, @PathParam("uid") String uid, @Context Request req) {
        VToDo issue = new VToDo().withProperty(new Uid(uid)).getFluentTarget();
        return ok(() -> issue, req);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{uid}")
    default Response updateIssue(@PathParam("repository") String repository, @PathParam("uid") String uid, VToDo issue,
                                 @Context Request req) {
        issue.add(new Uid(uid));
        return ok(() -> issue, req);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{uid}")
    default Response setIssue(@PathParam("repository") String repository, @PathParam("uid") String uid, VToDo issue,
                              @Context Request req) {
        issue.add(new Uid(uid));
        return ok(() -> issue, req);
    }

    @DELETE
    @Path("{uid}")
    default Response deleteIssue(@PathParam("repository") String repository, @PathParam("uid") String uid,
                                 @Context Request req) {
        VToDo issue = new VToDo().withProperty(new Uid(uid)).getFluentTarget();
        return ok(() -> issue, req);
    }
}
