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

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.ext.ContextResolver;
import jakarta.ws.rs.ext.Provider;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.vcard.VCard;
import org.mnode.ical4j.serializer.XCalMapper;
import org.mnode.ical4j.serializer.XCalSerializer;
import org.mnode.ical4j.serializer.XCardMapper;
import org.mnode.ical4j.serializer.XCardSerializer;

/**
 * A mapper to serialize/deserialize objects to XML.
 */
@Provider
@Produces(MediaType.APPLICATION_XML)
public class XmlMapperProvider implements ContextResolver<XmlMapper> {

    private final XmlMapper mapper;

    public XmlMapperProvider() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(Calendar.class, new XCalSerializer(null));
        module.addDeserializer(Calendar.class, new XCalMapper(null));

        module.addSerializer(VCard.class, new XCardSerializer(null));
        module.addDeserializer(VCard.class, new XCardMapper(null));

        mapper = XmlMapper.builder().defaultUseWrapper(true).build();
        mapper.setConfig(mapper.getSerializationConfig().withRootName(
                        PropertyName.construct("icalendar", "urn:ietf:params:xml:ns:icalendar-2.0"))
                .with(MapperFeature.USE_WRAPPER_NAME_AS_PROPERTY_NAME));

        mapper.registerModule(module);
    }

    @Override
    public XmlMapper getContext(Class<?> type) {
        return mapper;
    }
}
