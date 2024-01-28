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

package org.coucal.api.controller.user;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.Response;
import net.fortuna.ical4j.model.Calendar;
import org.coucal.api.controller.ICalendarResponseVariants;
//import org.pac4j.jax.rs.annotations.Pac4JLogout;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Authentication of users.
 */
//@Path("/auth")
public class AuthController implements ICalendarResponseVariants {

    @POST
    public Response authenticate(@Context Request req) {
        return ok(() -> {
            AtomicReference<List<Calendar>> result = new AtomicReference<>();
            return result.get();
        }, req);
    }

//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    @Pac4JSecurity(authorizers = "isAuthenticated")
//    public UserData getUserData(@Pac4JProfile CommonProfile profile) {
//        LOG.debug("Returning infos for {}", profile.getId());
//
//        return new UserData(profile.getId(), profile.getDisplayName());
//    }

//    @GET
//    @Pac4JCallback(skipResponse = true)
//    public UserData loginCB(@Pac4JProfile Optional<CommonProfile> profile) {
//        if (profile.isPresent()) {
//            return new UserData(profile.get().getId(), profile.get().getDisplayName());
//        } else {
//            throw new WebApplicationException(401);
//        }
//    }

//    @DELETE
//    @Path("/session")
//    @Pac4JLogout(skipResponse = true)
//    public void logout() {
//        // do nothing
//    }
}
