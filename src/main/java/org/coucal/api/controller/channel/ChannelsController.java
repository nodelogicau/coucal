package org.coucal.api.controller.channel;

import jakarta.ws.rs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/channels")
public class ChannelsController implements ChannelOperations {

    private static final String CHANNELS_COLLECTION = "channels";

    private static final String CHANNELS_WORKSPACE = "system";

    private static final Logger LOGGER = LoggerFactory.getLogger(ChannelsController.class);
}
