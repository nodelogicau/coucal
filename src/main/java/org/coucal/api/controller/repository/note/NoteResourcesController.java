package org.coucal.api.controller.repository.note;

import jakarta.ws.rs.Path;
import org.coucal.api.controller.repository.resource.ResourceOperations;

@Path("/{repository}/notes/{c}/resources")
public class NoteResourcesController implements ResourceOperations {
}
