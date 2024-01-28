package org.coucal.api.controller;

import net.fortuna.ical4j.util.UidGenerator;
import net.fortuna.ical4j.vcard.property.Uid;
import org.ical4j.connector.CardCollection;
import org.ical4j.connector.CardStore;
import org.ical4j.connector.ObjectNotFoundException;
import org.ical4j.connector.ObjectStoreException;

public abstract class AbstractCardController implements ICalendarResponseVariants {

    private final CardStore<CardCollection> cardStore;

    private final UidGenerator uidGenerator;

    public AbstractCardController(CardStore<CardCollection> cardStore, UidGenerator uidGenerator) {
        this.cardStore = cardStore;
        this.uidGenerator = uidGenerator;
    }

    public CardCollection getCardCollection(String collectionId, String workspace) throws ObjectStoreException,
            ObjectNotFoundException {
        if (workspace != null && !workspace.isBlank()) {
            return cardStore.getCollection(collectionId, workspace);
        } else {
            return cardStore.getCollection(collectionId);
        }
    }

    public Uid newUid() {
        return new Uid(uidGenerator.generateUid().getValue());
    }
}
