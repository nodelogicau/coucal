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

package org.coucal.api.controller.repository;

import jakarta.inject.Inject;
import jakarta.ws.rs.CookieParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.Response;
import org.coucal.api.controller.user.TeamController;
import org.coucal.api.controller.workspace.WorkspaceOperations;
import org.coucal.core.Repository;
import org.coucal.core.RepositoryManager;
import org.ical4j.connector.ObjectCollection;
import org.ical4j.connector.ObjectNotFoundException;
import org.ical4j.connector.ObjectStoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Path("/")
public class RepositoryController implements RepositoryOperations {

    private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryController.class);

    private final RepositoryManager repositoryManager;

    @Inject public RepositoryController(RepositoryManager repositoryManager) {
        this.repositoryManager = repositoryManager;
    }

    @Override
    public Response listRepositories(String workspace,
//            @QueryParam("f") String filter,
            Request req) {
        try {
            List<Repository> repositoryList = repositoryManager.listRepositories(workspace != null ? workspace : "default");
            return ok(() -> repositoryList, req);
        } catch (ObjectStoreException e) {
            LOGGER.error("Unexpected error", e);
            return serverError();
        } catch (ObjectNotFoundException e) {
            return notFound();
        }
    }

    @Override
    public Response createRepository(Repository repository,  Request req) {
        return created(() -> {
//            AtomicReference<ObjectCollection<?>> result = new AtomicReference<>();
//            new CreateCollection(result::set, store).withCollectionName(DEFAULT_COLLECTION).call();
            try {
                repositoryManager.createRepository(repository, "default");
            } catch (ObjectStoreException e) {
                throw new RuntimeException(e);
            }
            return URI.create("uuid:" + repository.getId());
        }, req);
    }

    @Override
    public Response getRepository(@PathParam("repository") String uid, Request req) {
        return ok(() -> {
            AtomicReference<ObjectCollection<?>> result = new AtomicReference<>();
//            new GetCollectionDetails(result::set, store).call();
            return result.get();
        }, req);
    }

//    @PUT
//    @Path("{uid}")
//    public Response setCollection(@PathParam("{uid}") String uid, Request req) {
//        return response(() -> "OK", req);
//    }

    @Override
    public Response updateRepository(@PathParam("{repository}") String uid, Repository repository, Request req) {
        return ok(() -> {
            AtomicReference<Void> result = new AtomicReference<>();
//            new UpdateCollection(result::set, store).call();
            return result.get();
        }, req);
    }

    @Override
    public Response deleteRepository(@PathParam("{repository}") String uid, Request req) {
        return ok(() -> {
            AtomicReference<ObjectCollection<?>> result = new AtomicReference<>();
//            new DeleteCollection(result::set, store).call();
            return result.get();
        }, req);
    }
}
