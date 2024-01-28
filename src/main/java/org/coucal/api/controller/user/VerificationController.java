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

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.Response;
import net.fortuna.ical4j.model.Calendar;
import org.coucal.api.controller.ICalendarResponseVariants;
import org.coucal.core.action.ActionManager;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Email verification support.
 */
//@Path("/verify/{guid}")
public class VerificationController implements ICalendarResponseVariants {

    private final ActionManager actionManager;

    @Inject  public VerificationController(ActionManager actionManager) {
        this.actionManager = actionManager;
    }

    @GET
    public Response verifyEmail(@PathParam("guid") String guid, @Context Request req) {
        return ok(() -> {
            AtomicReference<List<Calendar>> result = new AtomicReference<>();
            return result.get();
        }, req);
    }
}
