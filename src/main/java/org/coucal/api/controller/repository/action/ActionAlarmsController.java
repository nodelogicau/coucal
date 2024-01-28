package org.coucal.api.controller.repository.action;

import jakarta.ws.rs.Path;
import org.coucal.api.controller.repository.alarm.AlarmOccurrenceOperations;
import org.coucal.api.controller.repository.alarm.AlarmOperations;

@Path("/{repository}/actions/{c}/alarms")
public class ActionAlarmsController implements AlarmOperations {
}
