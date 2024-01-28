/*
 *  Copyright 2023 Ben Fortuna
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.coucal.api.controller.workspace;

import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.Response;
import net.fortuna.ical4j.util.UidGenerator;
import net.fortuna.ical4j.vcard.VCard;
import org.coucal.api.controller.AbstractCardController;
import org.coucal.api.controller.user.TeamController;
import org.ical4j.connector.CardCollection;
import org.ical4j.connector.CardStore;
import org.ical4j.connector.ObjectNotFoundException;
import org.ical4j.connector.ObjectStoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

/**
 * Manage workspace settings, access, etc. This could include a group of users, a
 * group of other groups, etc.  Only vCards of
 *  * type {@link net.fortuna.ical4j.vcard.property.immutable.ImmutableKind#GROUP} are managed by this
 *  * controller.
 */
@Path("/workspaces")
public class WorkspaceController extends AbstractCardController implements WorkspaceOperations {

    private static final String WORKSPACES_COLLECTION = "workspaces";

    private static final String WORKSPACES_WORKSPACE = "system";

    private static final Logger LOGGER = LoggerFactory.getLogger(TeamController.class);

    @Inject
    public WorkspaceController(CardStore<CardCollection> cardStore, UidGenerator uidGenerator) throws ObjectStoreException {
        super(cardStore, uidGenerator);
        cardStore.addCollection(WORKSPACES_COLLECTION, WORKSPACES_WORKSPACE);
    }

    @Override
    public Response listWorkspaces(Request req) {
        try {
            List<VCard> entities = getCardCollection(WORKSPACES_COLLECTION, WORKSPACES_WORKSPACE).getAll();
            return ok(() -> entities, req);
        } catch (ObjectNotFoundException e) {
            return notFound();
        } catch (ObjectStoreException e) {
            LOGGER.error("Unexpected error", e);
            return serverError();
        }
    }

    @Override
    public Response createWorkspace(VCard workspace, Request req) {
        return ok(() -> {
            try {
                return getCardCollection(WORKSPACES_COLLECTION, WORKSPACES_WORKSPACE).add(workspace.replace(newUid()));
            } catch (ObjectStoreException e) {
                LOGGER.error("Unexpected error", e);
                return serverError();
            } catch (ObjectNotFoundException e) {
                return notFound();
            }
        }, req);
    }

    @Override
    public Response getWorkspace(String uid, Request req) {
        try {
            Optional<VCard> workspace = getCardCollection(WORKSPACES_COLLECTION, WORKSPACES_WORKSPACE).get(uid);
            if (workspace.isPresent()) {
                return ok(workspace::get, req);
            } else {
                return notFound();
            }
        } catch (ObjectStoreException e) {
            LOGGER.error("Unexpected error", e);
            return serverError();
        } catch (ObjectNotFoundException e) {
            return notFound();
        }
    }

    @Override
    public Response updateWorkspace(String uid, VCard workspaceMods, Request req) {
        try {
            Optional<VCard> workspace = getCardCollection(WORKSPACES_COLLECTION, WORKSPACES_WORKSPACE).get(uid);
            if (workspace.isPresent()) {
                workspace.get().with(VCard.MERGE, workspaceMods.getProperties());
                getCardCollection(WORKSPACES_COLLECTION, WORKSPACES_WORKSPACE).merge(workspace.get());
                return getWorkspace(uid, req);
            } else {
                return notFound();
            }
        } catch (ObjectStoreException e) {
            LOGGER.error("Unexpected error", e);
            return serverError();
        } catch (ObjectNotFoundException e) {
            return notFound();
        }
    }
}