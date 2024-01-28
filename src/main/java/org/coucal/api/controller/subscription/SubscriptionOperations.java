package org.coucal.api.controller.subscription;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.Response;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Uid;
import org.coucal.api.controller.ICalendarResponseVariants;
import org.coucal.api.controller.workspace.WorkspaceOperations;

import java.util.Collections;

public interface SubscriptionOperations extends ICalendarResponseVariants {

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(summary = "List subscriptions",
            description = "List subscription objects in the specified repository")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "Not found - The repository was not found")
    })
    default Response listRepositories(@CookieParam(WorkspaceOperations.WORKSPACE_COOKIE) String workspace,
                                      @Context Request req) {
        return ok(Collections::emptyList, req);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create a new subscription object",
            description = "Create a new subscription object from the specified payload")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "404", description = "Not found - The repository was not found")
    })
    default Response createSubscription(VEvent subscription, @Context Request req) {
        return ok(() -> subscription, req);
    }

    @GET
    @Path("{subscription}")
    @Operation(summary = "Retrieve a single subscription",
            description = "Return the latest revision of the subscription object with the specified UID property")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "Not found - The subscription was not found")
    })
    default Response getSubscription(@PathParam("subscription") String uid, @Context Request req) {
        VEvent subscription = new VEvent().add(new Uid(uid));
        return ok(() -> subscription, req);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{subscription}")
    @Operation(summary = "Create a new revision of an existing subscription object",
            description = "Create a new revision of an existing subscription with the specified UID property.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated"),
            @ApiResponse(responseCode = "404", description = "Not found - The repository was not found")
    })
    default Response updateSubscription(@PathParam("subscription") String uid, VEvent subscription, @Context Request req) {
        return ok(() -> subscription.replace(new Uid(uid)), req);
    }

//    @PUT
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Path("{repository}")
//    @Operation(summary = "Overwrite a repository object",
//            description = "Replace all revisions of a repository (if it exists) with the specified UID property" +
//                    " using the specified payload")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Successfully replaced"),
//            @ApiResponse(responseCode = "404", description = "Not found - The repository was not found")
//    })
    default Response setSubscription(@PathParam("repository") String uid, VEvent subscription, @Context Request req) {
        return ok(() -> subscription.replace(new Uid(uid)), req);
    }

    @DELETE
    @Path("{subscription}")
    @Operation(summary = "Delete a subscription object",
            description = "Remove all revisions of an existing subscription with the specified UID property.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Not found - The repository was not found")
    })
    default Response deleteSubscription(@PathParam("subscription") String uid, @Context Request req) {
        VEvent subscription = new VEvent().add(new Uid(uid));
        return ok(() -> subscription, req);
    }
}
