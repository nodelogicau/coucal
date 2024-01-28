package org.coucal.api.controller.repository.event;

import jakarta.inject.Inject;
import jakarta.ws.rs.CookieParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.Response;
import net.fortuna.ical4j.extensions.concept.EventType;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.PropertyContainer;
import net.fortuna.ical4j.model.component.CalendarComponent;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.util.UidGenerator;
import org.coucal.api.controller.AbstractCalendarController;
import org.coucal.api.controller.workspace.WorkspaceOperations;
import org.ical4j.connector.CalendarStore;
import org.ical4j.connector.ObjectNotFoundException;
import org.ical4j.connector.ObjectStoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@Path("/{repository}/events")
public class EventsController extends AbstractCalendarController implements EventOperations {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventsController.class);

    @Inject public EventsController(CalendarStore events, UidGenerator uidGenerator) {
        super(events, uidGenerator);
    }

    @Override
    public Response listEvents(String repository, String workspace, Request req) {
        try {
            //XXX: need to filter VEVENT by CONCEPT property to avoid affecting other VEVENT types..
            List<CalendarComponent> events = getCalendarCollection(repository, workspace).getAll().stream()
                    .map(c -> c.getComponents("VEVENT")).flatMap(List::stream).collect(Collectors.toList());
            return ok(() -> events, req);
        } catch (ObjectNotFoundException e) {
            return notFound();
        } catch (ObjectStoreException e) {
            LOGGER.error("Unexpected error", e);
            return serverError();
        }
    }

    @Override
    public Response createEvent(String repository, VEvent event, Request req) {
        return ok(() -> {
            try {
                return getCalendarCollection(repository, "default").add(new Calendar().add(
                        (CalendarComponent) event.replace(newUid()).with(PropertyContainer.ADD_IF_NOT_PRESENT,
                                EventType.EVENT)));
            } catch (ObjectNotFoundException e) {
                return notFound();
            } catch (ObjectStoreException e) {
                LOGGER.error("Unexpected error", e);
                return serverError();
            }
        }, req);
    }

    @Override
    public Response getEvent(String repository, String uid, Request req) {
        return ok(() -> {
            try {
                return getCalendarCollection(repository, "default").get(uid).get();
            } catch (ObjectNotFoundException e) {
                return notFound();
            } catch (ObjectStoreException e) {
                LOGGER.error("Unexpected error", e);
                return serverError();
            }
        }, req);
    }
}
