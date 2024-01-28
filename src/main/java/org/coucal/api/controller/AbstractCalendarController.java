package org.coucal.api.controller;

import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.util.UidGenerator;
import org.ical4j.connector.CalendarCollection;
import org.ical4j.connector.CalendarStore;
import org.ical4j.connector.ObjectNotFoundException;
import org.ical4j.connector.ObjectStoreException;

public abstract class AbstractCalendarController {

    private final CalendarStore<CalendarCollection> calendarCollection;

    private final UidGenerator uidGenerator;

    public AbstractCalendarController(CalendarStore<CalendarCollection> calendarCollection, UidGenerator uidGenerator) {
        this.calendarCollection = calendarCollection;
        this.uidGenerator = uidGenerator;
    }

    public CalendarCollection getCalendarCollection(String collectionId, String workspace) throws ObjectStoreException,
            ObjectNotFoundException {
        if (workspace != null && !workspace.isBlank()) {
            return calendarCollection.getCollection(collectionId, workspace);
        } else {
            return calendarCollection.getCollection(collectionId);
        }
    }

    public Uid newUid() {
        return uidGenerator.generateUid();
    }

    //    @GET
//    @Operation(summary = "List content", description = "List content in the specified repository")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Operation successful"),
//            @ApiResponse(responseCode = "404", description = "Not found - The repository was not found")
//    })
//    public Response listContent(
//            @Parameter(name = "r", description = "Identifier of repository containing the resource")
//            @PathParam("repository") String repository,
//
//            @Context Request request) {
//
//        return response(() -> {
//            return null;
//        }, request);
//    }
}
