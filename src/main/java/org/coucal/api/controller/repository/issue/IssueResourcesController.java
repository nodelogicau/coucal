package org.coucal.api.controller.repository.issue;

import jakarta.ws.rs.Path;
import org.coucal.api.controller.repository.resource.ResourceOperations;

@Path("/{repository}/issues/{c}/resources")
public class IssueResourcesController implements ResourceOperations {
}
