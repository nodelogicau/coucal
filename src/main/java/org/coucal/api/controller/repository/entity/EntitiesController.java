package org.coucal.api.controller.repository.entity;

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

@Path("/{repository}/entities")
public class EntitiesController extends AbstractCardController implements EntityOperations {

    private static final Logger LOGGER = LoggerFactory.getLogger(EntitiesController.class);

    @Inject public EntitiesController(CardStore<CardCollection> cardStore, UidGenerator uidGenerator) {
        super(cardStore, uidGenerator);
    }

    @Override
    public Response listEntities(String repository, String workspace, Request req) {
        try {
            List<VCard> entities = getCardCollection(repository, workspace).getAll();
            return ok(() -> entities, req);
        } catch (ObjectNotFoundException e) {
            return notFound();
        } catch (ObjectStoreException e) {
            LOGGER.error("Unexpected error", e);
            return serverError();
        }
    }
}
