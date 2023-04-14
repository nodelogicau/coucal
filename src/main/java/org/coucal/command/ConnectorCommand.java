package org.coucal.command;

import org.ical4j.connector.command.AbstractCommand;
import org.coucal.Connector;
import picocli.CommandLine;

import java.util.List;

/**
 * Configure and access backend storage for calendar and card data.
 */
@CommandLine.Command(name = "connector", description = "Backend support for calendar and card data",
        subcommands = {ConnectorCommand.ListCommand.class}, mixinStandardHelpOptions = true)
public class ConnectorCommand {

    @CommandLine.Command(name = "list", description = "List available connector types",
            mixinStandardHelpOptions = true)
    public static class ListCommand extends AbstractCommand<List<Connector>> {

        @Override
        public void run() {

        }
    }

    @CommandLine.Command(name = "add", description = "Add a new connector",
            mixinStandardHelpOptions = true)
    public static class AddCommand {

    }

    @CommandLine.Command(name = "remove", aliases = {"rm"}, description = "Remove a connector",
            mixinStandardHelpOptions = true)
    public static class RemoveCommand {

    }

    @CommandLine.Command(name = "enable", description = "Enable a configured connector",
            mixinStandardHelpOptions = true)
    public static class EnableCommand {

    }

    @CommandLine.Command(name = "disable", description = "Disable a configured connector",
            mixinStandardHelpOptions = true)
    public static class DisableCommand {

    }
}
