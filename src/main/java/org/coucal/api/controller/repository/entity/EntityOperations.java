package org.coucal.api.controller.repository.entity;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.Response;
import net.fortuna.ical4j.vcard.VCard;
import net.fortuna.ical4j.vcard.property.Uid;
import org.coucal.api.controller.ICalendarResponseVariants;

import java.util.Collections;

public interface EntityOperations extends ICalendarResponseVariants {

    @GET
    default Response listEntities(@PathParam("repository") String repository,
                                  @CookieParam("workspace") String workspace, @Context Request req) {
        return ok(Collections::emptyList, req);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    default Response createEntity(@PathParam("repository") String repository,
                                  @CookieParam("workspace") String workspace, VCard entity, @Context Request req) {
        return ok(() -> entity, req);
    }

    @GET
    @Path("{uid}")
    default Response getEntity(@PathParam("repository") String repository, @PathParam("uid") String uid,
                               @CookieParam("workspace") String workspace, @Context Request req) {
        VCard entity = new VCard().add(new Uid(uid));
        return ok(() -> entity, req);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{uid}")
    default Response updateEntity(@PathParam("repository") String repository, @PathParam("uid") String uid,
                                  @CookieParam("workspace") String workspace, VCard entity,
                                  @Context Request req) {
        entity.add(new Uid(uid));
        return ok(() -> entity, req);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{uid}")
    default Response setEntity(@PathParam("repository") String repository, @CookieParam("workspace") String workspace,
                               @PathParam("uid") String uid, VCard entity, @Context Request req) {
        entity.add(new Uid(uid));
        return ok(() -> entity, req);
    }

    @DELETE
    @Path("{uid}")
    default Response deleteEntity(@PathParam("repository") String repository,
                                  @CookieParam("workspace") String workspace, @PathParam("uid") String uid,
                                  @Context Request req) {
        VCard entity = new VCard().add(new Uid(uid));
        return ok(() -> entity, req);
    }
}
