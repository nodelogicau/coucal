package org.coucal.api.controller.repository.action;

import jakarta.ws.rs.Path;
import org.coucal.api.controller.repository.resource.ResourceOccurrenceOperations;
import org.coucal.api.controller.repository.resource.ResourceOperations;

@Path("/{repository}/actions/{c}/resources")
public class ActionResourcesController implements ResourceOperations {
}
