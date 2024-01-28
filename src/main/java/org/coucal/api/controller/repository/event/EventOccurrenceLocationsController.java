package org.coucal.api.controller.repository.event;

import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import net.fortuna.ical4j.util.UidGenerator;
import org.coucal.api.controller.AbstractCalendarController;
import org.coucal.api.controller.repository.location.LocationOccurrenceOperations;
import org.ical4j.connector.CalendarCollection;
import org.ical4j.connector.CalendarStore;

@Path("/{repository}/events/{c}/{recurrenceId}/locations")
public class EventOccurrenceLocationsController extends AbstractCalendarController implements LocationOccurrenceOperations {
    @Inject public EventOccurrenceLocationsController(CalendarStore<CalendarCollection> calendarCollection, UidGenerator uidGenerator) {
        super(calendarCollection, uidGenerator);
    }
}
