package org.coucal.api.controller.repository.event;

import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import net.fortuna.ical4j.util.UidGenerator;
import org.coucal.api.controller.AbstractCalendarController;
import org.coucal.api.controller.repository.participant.ParticipantOperations;
import org.ical4j.connector.CalendarCollection;
import org.ical4j.connector.CalendarStore;

@Path("/{repository}/events/{c}/participants")
public class EventParticipantsController extends AbstractCalendarController implements ParticipantOperations {
    @Inject public EventParticipantsController(CalendarStore<CalendarCollection> calendarCollection, UidGenerator uidGenerator) {
        super(calendarCollection, uidGenerator);
    }
}
