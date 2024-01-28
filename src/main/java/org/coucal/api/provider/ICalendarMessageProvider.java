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

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.MessageBodyReader;
import jakarta.ws.rs.ext.MessageBodyWriter;
import jakarta.ws.rs.ext.Provider;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * Produce or consume iCalendar object data.
 */
@Produces({"application/icalendar"})
@Consumes({"application/icalendar"})
//@Singleton
@Provider
public class ICalendarMessageProvider implements MessageBodyReader<Calendar>, MessageBodyWriter<Calendar> {

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return type == Calendar.class;
    }

    @Override
    public Calendar readFrom(Class<Calendar> type, Type genericType, Annotation[] annotations, MediaType mediaType,
                             MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
            throws IOException, WebApplicationException {

        if (type.isAssignableFrom(Calendar.class)) {
            CalendarBuilder builder = new CalendarBuilder();
            try {
                return builder.build(entityStream);
            } catch (ParserException e) {
                throw new WebApplicationException(e);
            }
        }
        throw new IllegalArgumentException("Unsupported type");
    }

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return type == Calendar.class;
    }

    @Override
    public void writeTo(Calendar s, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
                        MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
            throws IOException, WebApplicationException {

        CalendarOutputter outputter = new CalendarOutputter();
        if (type.isAssignableFrom(Calendar.class)) {
            outputter.output(s, entityStream);
        }
        throw new IllegalArgumentException("Unsupported type");
    }
}
