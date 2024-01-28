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

public interface UserOperations extends ICalendarResponseVariants {

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(summary = "List users",
            description = "List user objects in the specified repository")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "Not found - The repository was not found")
    })
    default Response listUsers(@Context Request req) {
        return ok(Collections::emptyList, req);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create a new user object",
            description = "Create a new user object from the specified payload")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "404", description = "Not found - The repository was not found")
    })
    default Response createUser(VCard user, @Context Request req) {
        return ok(() -> user, req);
    }

    @GET
    @Path("{uid}")
    @Operation(summary = "Retrieve a single user",
            description = "Return the latest revision of the user object with the specified UID property")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "Not found - The user was not found")
    })
    default Response getUser(@PathParam("uid") String uid, @Context Request req) {
        VCard user = new VCard().add(new Uid(uid));
        return ok(() -> user, req);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{uid}")
    @Operation(summary = "Create a new revision of an existing user object",
            description = "Create a new revision of an existing user with the specified UID property.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated"),
            @ApiResponse(responseCode = "404", description = "Not found - The user was not found")
    })
    default Response updateUser(@PathParam("uid") String uid, VCard user, @Context Request req) {
        user.add(new Uid(uid));
        return ok(() -> user, req);
    }

//    @PUT
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Path("{uid}")
//    @Operation(summary = "Overwrite a user object",
//            description = "Replace all revisions of a user (if it exists) with the specified UID property" +
//                    " using the specified payload")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Successfully replaced"),
//            @ApiResponse(responseCode = "404", description = "Not found - The repository was not found")
//    })
    default Response setUser(@PathParam("uid") String uid, VCard user, @Context Request req) {
        user.add(new Uid(uid));
        return ok(() -> user, req);
    }

    @DELETE
    @Path("{uid}")
    @Operation(summary = "Delete a user object",
            description = "Remove all revisions of an existing user with the specified UID property.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Not found - The repository was not found")
    })
    default Response deleteUser(@PathParam("uid") String uid, @Context Request req) {
        VCard user = new VCard().add(new Uid(uid));
        return ok(() -> user, req);
    }
}
