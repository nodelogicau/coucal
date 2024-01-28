package org.coucal.api.controller.repository.issue;

import jakarta.ws.rs.Path;
import org.coucal.api.controller.repository.participant.ParticipantOperations;

@Path("/{repository}/issues/{c}/participants")
public class IssueParticipantsController implements ParticipantOperations {
}
