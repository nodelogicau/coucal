package org.coucal.api.controller.repository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.Response;
import org.coucal.api.controller.ICalendarResponseVariants;
import org.coucal.api.controller.workspace.WorkspaceOperations;
import org.coucal.core.Repository;

import java.util.Collections;

public interface RepositoryOperations extends ICalendarResponseVariants {

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(summary = "List repositories",
            description = "List repository objects in the specified repository")
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
    @Operation(summary = "Create a new repository object",
            description = "Create a new repository object from the specified payload")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "404", description = "Not found - The repository was not found")
    })
    default Response createRepository(Repository repository, @Context Request req) {
        return ok(() -> repository, req);
    }

    @GET
    @Path("{repository}")
    @Operation(summary = "Retrieve a single repository",
            description = "Return the latest revision of the repository object with the specified UID property")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "Not found - The repository was not found")
    })
    default Response getRepository(@PathParam("repository") String uid, @Context Request req) {
//        Repository repository = new Repository();
//        repository.setId(uid);
        return ok(() -> "repository", req);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{repository}")
    @Operation(summary = "Create a new revision of an existing repository object",
            description = "Create a new revision of an existing repository with the specified UID property.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated"),
            @ApiResponse(responseCode = "404", description = "Not found - The repository was not found")
    })
    default Response updateRepository(@PathParam("repository") String uid, Repository repository, @Context Request req) {
//        repository.setId(uid);
        return ok(() -> repository, req);
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
    default Response setRepository(@PathParam("repository") String uid, Repository repository, @Context Request req) {
//        repository.setId(uid);
        return ok(() -> repository, req);
    }

    @DELETE
    @Path("{repository}")
    @Operation(summary = "Delete a repository object",
            description = "Remove all revisions of an existing repository with the specified UID property.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Not found - The repository was not found")
    })
    default Response deleteRepository(@PathParam("repository") String uid, @Context Request req) {
//        Repository repository = new Repository();
//        repository.setId(uid);
        return ok(() -> "repository", req);
    }
}
