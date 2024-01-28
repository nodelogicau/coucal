package org.coucal.api.controller.user;

import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.Response;
import net.fortuna.ical4j.util.UidGenerator;
import net.fortuna.ical4j.vcard.VCard;
import org.coucal.api.controller.AbstractCardController;
import org.ical4j.connector.CardCollection;
import org.ical4j.connector.CardStore;
import org.ical4j.connector.ObjectNotFoundException;
import org.ical4j.connector.ObjectStoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Path("/teams")
public class TeamController extends AbstractCardController implements TeamOperations {

    private static final String TEAMS_COLLECTION = "teams";

    private static final String TEAMS_WORKSPACE = "system";

    private static final Logger LOGGER = LoggerFactory.getLogger(TeamController.class);

    @Inject public TeamController(CardStore<CardCollection> cardStore, UidGenerator uidGenerator) throws ObjectStoreException {
        super(cardStore, uidGenerator);
        cardStore.addCollection(TEAMS_COLLECTION, TEAMS_WORKSPACE);
    }

    @Override
    public Response listTeams(Request req) {
        try {
            List<VCard> entities = getCardCollection(TEAMS_COLLECTION, TEAMS_WORKSPACE).getAll();
            return ok(() -> entities, req);
        } catch (ObjectNotFoundException e) {
            return notFound();
        } catch (ObjectStoreException e) {
            LOGGER.error("Unexpected error", e);
            return serverError();
        }
    }
}
