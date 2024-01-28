package org.coucal.api.controller.channel.email;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.Response;
import org.coucal.api.controller.ICalendarResponseVariants;

import java.util.Collections;

/**
 * An Email channel differs from other channels in that content is not transferred via HTTP, but via SMTP and
 * other Email-related protocols. However, Email channels still require some API functionality for triggering
 * send, receive and other workflows.
 *
 * Also unlike most channel types, Email channels require an approval step for specific content updates, such as
 * registering new events. This is required to combat potential spam content from unknown sources.
 */
public interface EmailQuarantineOperations extends ICalendarResponseVariants {

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(summary = "List quarantine queue",
            description = "List content that has been quarantined pending approval")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "Not found - The repository was not found")
    })
    default Response listQuarantineQueue(@PathParam("channel") String channel, @Context Request req) {
        return ok(Collections::emptyList, req);
    }
}
