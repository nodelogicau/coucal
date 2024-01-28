package org.coucal.api.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.Response;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.vcard.VCard;
import org.coucal.api.controller.ICalendarResponseVariants;

import java.util.Collections;

public interface TeamOperations extends ICalendarResponseVariants {

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(summary = "List teams",
            description = "List team objects in the specified repository")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "Not found - The repository was not found")
    })
    default Response listTeams(@Context Request req) {
        return ok(Collections::emptyList, req);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create a new team object",
            description = "Create a new team object from the specified payload")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "404", description = "Not found - The repository was not found")
    })
    default Response createTeam(VCard team, @Context Request req) {
        return ok(() -> team, req);
    }

    @GET
    @Path("{uid}")
    @Operation(summary = "Retrieve a single team",
            description = "Return the latest revision of the team object with the specified UID property")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "Not found - The team was not found")
    })
    default Response getTeam(@PathParam("uid") String uid, @Context Request req) {
        VCard team = new VCard().add(new Uid(uid));
        return ok(() -> team, req);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{uid}")
    @Operation(summary = "Create a new revision of an existing team object",
            description = "Create a new revision of an existing team with the specified UID property.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated"),
            @ApiResponse(responseCode = "404", description = "Not found - The team was not found")
    })
    default Response updateTeam(@PathParam("uid") String uid, VCard team, @Context Request req) {
        team.add(new Uid(uid));
        return ok(() -> team, req);
    }

//    @PUT
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Path("{uid}")
//    @Operation(summary = "Overwrite a team object",
//            description = "Replace all revisions of a team (if it exists) with the specified UID property" +
//                    " using the specified payload")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Successfully replaced"),
//            @ApiResponse(responseCode = "404", description = "Not found - The repository was not found")
//    })
    default Response setTeam(@PathParam("uid") String uid, VCard team, @Context Request req) {
        team.add(new Uid(uid));
        return ok(() -> team, req);
    }

    @DELETE
    @Path("{uid}")
    @Operation(summary = "Delete a team object",
            description = "Remove all revisions of an existing team with the specified UID property.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Not found - The repository was not found")
    })
    default Response deleteTeam(@PathParam("uid") String uid, @Context Request req) {
        VCard team = new VCard().add(new Uid(uid));
        return ok(() -> team, req);
    }
}
