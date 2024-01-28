package org.coucal.api.controller.channel.webhooks;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.Response;
import net.fortuna.ical4j.model.component.VEvent;
import org.coucal.api.controller.ICalendarResponseVariants;

/**
 * An IFTTT Webhook supports requests and updates from an "If this, then that (IFTTT)" applet.
 *
 * An IFTTT Webhook requires authentication so that content is implicitly verified and can be applied to the
 * target repository without an approval step.
 */
public interface IFTTTWebhookOperations extends ICalendarResponseVariants {

    @POST
    @Path("create_event")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create a new event",
            description = "Create a new event from an IFTT invocation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not found - The repository was not found")
    })
    default Response createEvent(VEvent event,  @Context Request req) {
        return ok(() -> null, req);
    }
}
