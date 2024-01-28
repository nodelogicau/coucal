
package org.coucal.api.controller.workspace;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.vcard.VCard;
import org.coucal.api.controller.ICalendarResponseVariants;

import java.util.Collections;

public interface WorkspaceOperations extends ICalendarResponseVariants {

    String WORKSPACE_COOKIE = "Workspace";

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(summary = "List workspaces",
            description = "List workspace identifiers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "Not found - The repository was not found")
    })
    default Response listWorkspaces(@Context Request req) {
        return ok(Collections::emptyList, req);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create a new workspace",
            description = "Create a new workspace from the specified identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "404", description = "Not found - The repository was not found")
    })
    default Response createWorkspace(VCard workspace, @Context Request req) {
        return ok(() -> workspace, req);
    }

    @GET
    @Path("{uid}")
    @Operation(summary = "Retrieve a single workspace",
            description = "Return the latest revision of the workspace object with the specified UID property")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "Not found - The workspace was not found")
    })
    default Response getWorkspace(@PathParam("uid") String uid, @Context Request req) {
        VCard workspace = new VCard().add(new Uid(uid));
        return ok(() -> workspace, req);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{uid}")
    @Operation(summary = "Create a new revision of an existing workspace object",
            description = "Create a new revision of an existing workspace with the specified UID property.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated"),
            @ApiResponse(responseCode = "404", description = "Not found - The workspace was not found")
    })
    default Response updateWorkspace(@PathParam("uid") String uid, VCard workspaceMods, @Context Request req) {
        workspaceMods.add(new Uid(uid));
        return ok(() -> workspaceMods, req);
    }

    //    @PUT
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Path("{uid}")
//    @Operation(summary = "Overwrite a workspace object",
//            description = "Replace all revisions of a workspace (if it exists) with the specified UID property" +
//                    " using the specified payload")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Successfully replaced"),
//            @ApiResponse(responseCode = "404", description = "Not found - The repository was not found")
//    })
    default Response setWorkspace(@PathParam("uid") String uid, VCard workspace, @Context Request req) {
        workspace.add(new Uid(uid));
        return ok(() -> workspace, req);
    }

    @DELETE
    @Path("{uid}")
    @Operation(summary = "Delete a workspace object",
            description = "Remove all revisions of an existing workspace with the specified UID property.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Not found - The repository was not found")
    })
    default Response deleteWorkspace(@PathParam("uid") String uid, @Context Request req) {
        VCard workspace = new VCard().add(new Uid(uid));
        return ok(() -> workspace, req);
    }

    @POST
    @Path("{uid}/select")
    @Operation(summary = "Select an existing workspace",
            description = "Select an existing workspace as current")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not found - The repository was not found")
    })
    default Response selectWorkspace(@PathParam("uid") String workspace, @Context Request req) {
        NewCookie workspaceCookie = new NewCookie.Builder(WORKSPACE_COOKIE).value(workspace).build();
        return ok(() -> workspace, req, workspaceCookie);
    }
}
