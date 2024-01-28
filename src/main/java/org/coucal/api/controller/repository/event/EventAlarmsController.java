package org.coucal.api.controller.repository.event;

import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.Response;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.util.UidGenerator;
import org.coucal.api.controller.AbstractCalendarController;
import org.coucal.api.controller.repository.alarm.AlarmOperations;
import org.ical4j.connector.CalendarCollection;
import org.ical4j.connector.CalendarStore;
import org.ical4j.connector.ObjectNotFoundException;
import org.ical4j.connector.ObjectStoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Path("/{repository}/events/{c}/alarms")
public class EventAlarmsController extends AbstractCalendarController implements AlarmOperations {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventAlarmsController.class);

    @Inject public EventAlarmsController(CalendarStore<CalendarCollection> calendarCollection, UidGenerator uidGenerator) {
        super(calendarCollection, uidGenerator);
    }

    @Override
    public Response listAlarms(String repository, String component, Request req) {
        try {
            Optional<Calendar> eventCalendar = getCalendarCollection(repository, "default").get(component);
            if (eventCalendar.isPresent()) {
                VEvent event = (VEvent) eventCalendar.get().getComponent("VEVENT").get();
                return ok(event::getAlarms, req);
            } else {
                return notFound();
            }
        } catch (ObjectNotFoundException e) {
            return notFound();
        } catch (ObjectStoreException e) {
            LOGGER.error("Unexpected error", e);
            return serverError();
        }
    }
}
