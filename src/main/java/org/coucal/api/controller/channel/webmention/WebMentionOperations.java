package org.coucal.api.controller.channel.webmention;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.Response;
import net.fortuna.ical4j.model.component.VToDo;
import net.fortuna.ical4j.model.property.Status;
import net.fortuna.ical4j.model.property.Uid;
import org.coucal.api.controller.ICalendarResponseVariants;

import java.util.Collections;

/**
 * When an external site registers a new Webmention via a POST call to the registration endpoint, a new
 * VTODO object is created with concept LINK_REGISTRATION. Periodically the repository owner(s) should check
 * for new registrations and approve or reject accordingly.
 *
 * Upon approval the content corresponding to the source URL (i.e. has a matching URL property) will be updated
 * with a new LINK property of type "replies".
 *
 * A Webmention channel differs from most other channels in that it requires an approval step to apply incoming
 * changes. This is because the endpoint is unauthenticated and the validity of link registrations must be
 * verified.
 */
public interface WebMentionOperations extends ICalendarResponseVariants {

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(summary = "List link registrations",
            description = "List link registrations for the specified repository")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "Not found - The repository was not found")
    })
    default Response listLinkRegistrations(@PathParam("channel") String channel, @Context Request req) {
        return ok(Collections::emptyList, req);
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Operation(summary = "Register a Webmention link",
            description = "Create a new link from the specified form data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "404", description = "Not found - The repository was not found")
    })
    default Response registerLink(@PathParam("channel") String channel, @FormParam("source") String source,
                                  @FormParam("target") String target, @Context Request req) {
        VToDo linkRegistration = new VToDo();
        return ok(() -> linkRegistration, req);
    }

    @GET
    @Path("{uid}")
    @Operation(summary = "Retrieve a single link registration",
            description = "Return the latest revision of the link registration with the specified UID property")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "Not found - The registration was not found")
    })
    default Response getLinkRegistration(@PathParam("channel") String channel, @PathParam("uid") String uid,
                                         @Context Request req) {
        VToDo linkRegistration = new VToDo().withProperty(new Uid(uid)).getFluentTarget();
        return ok(() -> linkRegistration, req);
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("{uid}")
    @Operation(summary = "Create a new revision of an existing link registration",
            description = "Create a new revision of an existing link registration with the specified UID property.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated"),
            @ApiResponse(responseCode = "404", description = "Not found - The link registration was not found")
    })
    default Response updateLinkRegistrationStatus(@PathParam("channel") String channel, @PathParam("uid") String uid,
                                                  @FormParam("status") String status, @Context Request req) {
        VToDo linkRegistration = new VToDo().add(new Uid(uid)).replace(new Status(status));
        return ok(() -> linkRegistration, req);
    }

    @DELETE
    @Path("{uid}")
    @Operation(summary = "Delete a link registration",
            description = "Remove all revisions of an existing link registration with the specified UID property.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Not found - The repository was not found")
    })
    default Response deleteLinkRegistration(@PathParam("channel") String channel, @PathParam("uid") String uid,
                                 @Context Request req) {
        VToDo linkRegistration = new VToDo().withProperty(new Uid(uid)).getFluentTarget();
        return ok(() -> linkRegistration, req);
    }
}
