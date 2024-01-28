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

package org.coucal.core;

import jakarta.inject.Inject;
import org.ical4j.connector.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * A Repository provides abstractions for managing storage and retrieval of data objects. Objects
 * may persist in various forms, but in Coucal we use the following primary object types for
 * managing information:
 *
 * * Card - represents an entity, either physical or virtual
 * * Event - a temporal marker representing a collaborative activity or reminder
 * * ToDo - an action or activity that has an associated completion status
 * * Journal - a record optionally linked to a time, place or other activity
 * * Availability - resource planning by time, and optionally location
 *
 * Each Repository implementation may support one or more of these types concurrently.
 */
public class RepositoryManager {

    private static final String REPOSITORIES_DIR = "repositories";

    private final CalendarStore<CalendarCollection> calendarStore;

    private final CardStore<CardCollection> cardStore;

    @Inject public RepositoryManager(CalendarStore<CalendarCollection> calendarStore,
                                     CardStore<CardCollection> cardStore) {
        this.calendarStore = calendarStore;
        this.cardStore = cardStore;
    }

    public List<Repository> listRepositories(String namespace) throws ObjectStoreException, ObjectNotFoundException {
//        return cardStore.getCollection(REPOSITORIES_DIR).;
        return calendarStore.getCollections(namespace).stream().map(c -> {
            Repository r = new Repository();
            r.setId("1234");
            r.setDisplayName("c.getDisplayName()");
            return r;
        }).collect(Collectors.toList());
    }

    public boolean createRepository(Repository repository, String workspace) throws ObjectStoreException {
        calendarStore.addCollection(repository.getId(), workspace);
        cardStore.addCollection(repository.getId(), workspace);
        return true;
    }

    public Repository getRepository(String repositoryId) {
        return null;
    }

    public Repository archiveRepository(String repositoryId) {
        return null;
    }

    public Repository deleteRepository(String repositoryId) {
        return null;
    }
}
