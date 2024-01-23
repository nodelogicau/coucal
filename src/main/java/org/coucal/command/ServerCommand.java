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

package org.coucal.command;

import io.swagger.v3.jaxrs2.integration.resources.AcceptHeaderOpenApiResource;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import org.coucal.api.ControllerBinder;
import org.coucal.api.provider.CorsFilter;
import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;

import java.net.URI;
import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "server", description = "Start server for receiving requests",
        subcommands = {CommandLine.HelpCommand.class})
public class ServerCommand implements Callable<Integer> {

    @CommandLine.Parameters(index = "0", defaultValue = "http://0.0.0.0:8000")
    private String serverUri;
    
    @CommandLine.Parameters(index = "1..*", defaultValue = "org.coucal.api.controller")
    private String[] resourcePackages;

    public ServerCommand() {
    }

    public ServerCommand(String serverUri, String...resourcePackages) {
        this.serverUri = serverUri;
        this.resourcePackages = resourcePackages;
    }

    @Override
    public Integer call() {
        ResourceConfig resourceConfig = new ResourceConfig()
                .packages("org.coucal.api.provider")
                .packages(resourcePackages)
                .register(new ControllerBinder())
                .register(new CorsFilter())
                .register(new OpenApiResource().resourcePackages(new HashSet<>(Arrays.asList(resourcePackages))))
                .register(new AcceptHeaderOpenApiResource().resourcePackages(
                        new HashSet<>(Arrays.asList(resourcePackages))));
//        resourceConfig.register(
//                new Pac4JSecurityFilterFeature(pac4jConfig, null, "isAuthenticated", null, "excludeUserSession", null));

        // Enable Tracing support.
//        resourceConfig.property(ServerProperties.TRACING, "ALL");

        Server server = JettyHttpContainerFactory.createServer(URI.create(serverUri), resourceConfig);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                System.out.println("Shutting down the application...");
                server.stop();
                System.out.println("Done, exit.");
            } catch (Exception e) {
                LoggerFactory.getLogger(ServerCommand.class.getName()).error("Unexpected error", e);
            }
        }));

        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }

        // exit normally..
        return 0;
    }
}
