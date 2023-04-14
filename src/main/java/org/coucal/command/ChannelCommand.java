package org.coucal.command;

import org.ical4j.connector.command.AbstractCommand;
import org.coucal.Integration;
import picocli.CommandLine;

import java.util.List;

/**
 * Configure and access backend storage for calendar and card data.
 */
@CommandLine.Command(name = "channel", description = "Integration channels for sharing calendar and card data",
        subcommands = {ChannelCommand.ListCommand.class, ChannelCommand.AddCommand.class,
        ChannelCommand.RemoveCommand.class, ChannelCommand.EnableCommand.class,
        ChannelCommand.DisableCommand.class, ChannelCommand.SetDefaultCommand.class},
        mixinStandardHelpOptions = true)
public class ChannelCommand {

    @CommandLine.Command(name = "list", description = "List available channel types",
            mixinStandardHelpOptions = true)
    public static class ListCommand extends AbstractCommand<List<Integration>> {

        @Override
        public void run() {

        }
    }

    @CommandLine.Command(name = "add", description = "Add a new channel",
            mixinStandardHelpOptions = true)
    public static class AddCommand {

    }

    @CommandLine.Command(name = "remove", aliases = {"rm"}, description = "Remove a channel",
            mixinStandardHelpOptions = true)
    public static class RemoveCommand {

    }

    @CommandLine.Command(name = "enable", description = "Enable a configured channel",
            mixinStandardHelpOptions = true)
    public static class EnableCommand {

    }

    @CommandLine.Command(name = "disable", description = "Disable a configured channel",
            mixinStandardHelpOptions = true)
    public static class DisableCommand {

    }

    @CommandLine.Command(name = "set-default", description = "Set the default channel",
            mixinStandardHelpOptions = true)
    public static class SetDefaultCommand {

    }
}
