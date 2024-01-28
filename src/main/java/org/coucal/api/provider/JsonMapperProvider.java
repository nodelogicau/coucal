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

package org.coucal.api.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.ext.ContextResolver;
import jakarta.ws.rs.ext.Provider;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.component.*;
import net.fortuna.ical4j.vcard.VCard;
import org.mnode.ical4j.serializer.JCalMapper;
import org.mnode.ical4j.serializer.JCalSerializer;
import org.mnode.ical4j.serializer.jotn.ComponentListMapper;
import org.mnode.ical4j.serializer.jotn.VCardMapper;
import org.mnode.ical4j.serializer.jotn.VCardSerializer;
import org.mnode.ical4j.serializer.jotn.component.*;

/**
 * A mapper to serialize/deserialize objects to JSON.
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
//@Consumes({MediaType.APPLICATION_JSON})
public class JsonMapperProvider implements ContextResolver<ObjectMapper> {

    private final ObjectMapper mapper;

    public JsonMapperProvider() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(Calendar.class, new JCalSerializer(null));
//        module.addSerializer(VCard.class, new JCardSerializer(null));

        module.addSerializer(Available.class, new AvailableSerializer(null));
        module.addSerializer(VAlarm.class, new VAlarmSerializer(null));
        module.addSerializer(VAvailability.class, new VAvailabilitySerializer(null));
        module.addSerializer(VEvent.class, new VEventSerializer(null));
        module.addSerializer(VJournal.class, new VJournalSerializer(null));
        module.addSerializer(VLocation.class, new VLocationSerializer(null));
        module.addSerializer(VToDo.class, new VToDoSerializer(null));
        module.addSerializer(VCard.class, new VCardSerializer(null));

        module.addDeserializer(Calendar.class, new JCalMapper(null));
//        module.addDeserializer(VCard.class, new JCardMapper(null));
        
        module.addDeserializer(ComponentList.class, new ComponentListMapper());
        module.addDeserializer(Available.class, new AvailableMapper(null));
        module.addDeserializer(VAlarm.class, new VAlarmMapper(null));
        module.addDeserializer(VAvailability.class, new VAvailabilityMapper(null));
        module.addDeserializer(VEvent.class, new VEventMapper(null));
        module.addDeserializer(VJournal.class, new VJournalMapper(null));
        module.addDeserializer(VLocation.class, new VLocationMapper(null));
        module.addDeserializer(VToDo.class, new VToDoMapper(null));
        module.addDeserializer(VCard.class, new VCardMapper(null));

        mapper = new ObjectMapper();
        mapper.registerModule(module);
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return mapper;
    }
}
