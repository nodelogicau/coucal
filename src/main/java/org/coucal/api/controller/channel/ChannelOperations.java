package org.coucal.api.controller.channel;

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

/**
 * Channels control the ingress and egress of content stored in repositories. Specifically channels can transform
 * content to alternate formats for publishing and conform with update mechanisms defined externally.
 *
 * Channels are managed globally, however each channel definition is specifically linked to a single repository. This
 * ensures the internal content structure is not leaked externally, but external updates can still be linked back to
 * the corresponding repository.
 */
public interface ChannelOperations extends ICalendarResponseVariants {

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(summary = "List channels",
            description = "List channel identifiers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "Not found - The repository was not found")
    })
    default Response listChannels(@Context Request req) {
        return ok(Collections::emptyList, req);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create a new channel",
            description = "Create a new channel from the specified identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "404", description = "Not found - The repository was not found")
    })
    default Response createChannel(VCard channel, @Context Request req) {
        return ok(() -> channel, req);
    }

    @GET
    @Path("{uid}")
    @Operation(summary = "Retrieve a single channel",
            description = "Return the latest revision of the channel object with the specified UID property")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "Not found - The channel was not found")
    })
    default Response getChannel(@PathParam("uid") String uid, @Context Request req) {
        VCard channel = new VCard().add(new Uid(uid));
        return ok(() -> channel, req);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{uid}")
    @Operation(summary = "Create a new revision of an existing channel object",
            description = "Create a new revision of an existing channel with the specified UID property.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated"),
            @ApiResponse(responseCode = "404", description = "Not found - The channel was not found")
    })
    default Response updateChannel(@PathParam("uid") String uid, VCard channelMods, @Context Request req) {
        channelMods.add(new Uid(uid));
        return ok(() -> channelMods, req);
    }

    //    @PUT
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Path("{uid}")
//    @Operation(summary = "Overwrite a channel object",
//            description = "Replace all revisions of a channel (if it exists) with the specified UID property" +
//                    " using the specified payload")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Successfully replaced"),
//            @ApiResponse(responseCode = "404", description = "Not found - The repository was not found")
//    })
    default Response setChannel(@PathParam("uid") String uid, VCard channel, @Context Request req) {
        channel.add(new Uid(uid));
        return ok(() -> channel, req);
    }

    @DELETE
    @Path("{uid}")
    @Operation(summary = "Delete a channel object",
            description = "Remove all revisions of an existing channel with the specified UID property.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Not found - The repository was not found")
    })
    default Response deleteChannel(@PathParam("uid") String uid, @Context Request req) {
        VCard channel = new VCard().add(new Uid(uid));
        return ok(() -> channel, req);
    }
}
