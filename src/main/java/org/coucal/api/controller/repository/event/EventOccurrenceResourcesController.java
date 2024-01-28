package org.coucal.api.controller.repository.event;

import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import net.fortuna.ical4j.util.UidGenerator;
import org.coucal.api.controller.AbstractCalendarController;
import org.coucal.api.controller.repository.resource.ResourceOccurrenceOperations;
import org.ical4j.connector.CalendarCollection;
import org.ical4j.connector.CalendarStore;

@Path("/{repository}/events/{c}/{recurrenceId}/resources")
public class EventOccurrenceResourcesController extends AbstractCalendarController implements ResourceOccurrenceOperations {
    @Inject public EventOccurrenceResourcesController(CalendarStore<CalendarCollection> calendarCollection, UidGenerator uidGenerator) {
        super(calendarCollection, uidGenerator);
    }
}
