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

package org.coucal.api.controller;

import jakarta.ws.rs.core.*;

import java.net.URI;
import java.util.List;
import java.util.function.Supplier;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static jakarta.ws.rs.core.MediaType.APPLICATION_XML_TYPE;

/**
 * Support for response building using a supplied result.
 */
public interface ICalendarResponseVariants {

    MediaType APPLICATION_ICALENDAR_TYPE = MediaType.valueOf("application/icalendar");

    List<Variant> VARIANT_LIST = Variant.mediaTypes(APPLICATION_JSON_TYPE, APPLICATION_XML_TYPE,
            APPLICATION_ICALENDAR_TYPE).build();

    /**
     * Build a successful response by serializing the supplied object based on request headers.
     * @param result
     * @param request
     * @return
     */
    default Response ok(Supplier<Object> result, Request request, NewCookie... cookie) {
        Variant variant = request.selectVariant(VARIANT_LIST);
        if (variant != null) {
            Response.ResponseBuilder builder;
            if (variant.getMediaType().isCompatible(APPLICATION_ICALENDAR_TYPE)) {
                builder = Response.ok(result.get(), APPLICATION_ICALENDAR_TYPE);
            } else if (variant.getMediaType().isCompatible(APPLICATION_XML_TYPE)) {
                builder = Response.ok(result.get(), APPLICATION_XML_TYPE);
            } else if (variant.getMediaType().isCompatible(APPLICATION_JSON_TYPE)) {
                builder = Response.ok(result.get(), APPLICATION_JSON_TYPE);
            } else {
                return Response.notAcceptable(VARIANT_LIST).build();
            }
            return builder.cookie(cookie).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    default Response created(Supplier<URI> location, Request request) {
        return Response.created(location.get()).build();
    }

    default Response notFound() {
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    default Response serverError() {
        return Response.serverError().build();
    }
}
