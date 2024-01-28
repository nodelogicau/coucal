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

package org.coucal.api;

import net.fortuna.ical4j.util.RandomUidGenerator;
import net.fortuna.ical4j.util.UidGenerator;
import org.coucal.core.RepositoryManager;
import org.coucal.core.WorkspaceManager;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.ical4j.connector.CalendarStore;
import org.ical4j.connector.CardStore;
import org.ical4j.connector.local.LocalCalendarStore;
import org.ical4j.connector.local.LocalCardStore;

import java.io.File;

public class ControllerBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(RepositoryManager.class).to(RepositoryManager.class);
        bind(WorkspaceManager.class).to(WorkspaceManager.class);
        bind(new LocalCalendarStore(new File("build/calendar/store"))).to(CalendarStore.class);
        bind(new LocalCardStore(new File("build/card/store"))).to(CardStore.class);
        bind(RandomUidGenerator.class).to(UidGenerator.class);
    }
}
