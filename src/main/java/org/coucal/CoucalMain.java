package org.coucal;

import org.coucal.command.*;
import picocli.CommandLine;

@CommandLine.Command(name = "coucal", description = "Coucou! Let's collaborate.",
        subcommands = {ListCommand.class, NewCommand.class, QueryCommand.class, ConnectorCommand.class,
                ChannelCommand.class, MoveCommand.class, RemoveCommand.class, SyncCommand.class, ServerCommand.class},
        mixinStandardHelpOptions = true, versionProvider = VersionProvider.class)
public class CoucalMain {

    public static void main(String[] args) {
        int exitCode = new CommandLine(new CoucalMain()).execute(args);
        System.exit(exitCode);
    }
}
