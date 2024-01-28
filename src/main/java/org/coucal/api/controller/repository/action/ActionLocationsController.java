package org.coucal.api.controller.repository.action;

import jakarta.ws.rs.Path;
import org.coucal.api.controller.repository.location.LocationOccurrenceOperations;
import org.coucal.api.controller.repository.location.LocationOperations;

@Path("/{repository}/actions/{c}/locations")
public class ActionLocationsController implements LocationOperations {
}
