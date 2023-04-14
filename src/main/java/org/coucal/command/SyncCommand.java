package org.coucal.command;

import picocli.CommandLine;

@CommandLine.Command(name = "sync", description = "Synchronize changes with configured integration channels",
        subcommands = {}, mixinStandardHelpOptions = true)
public class SyncCommand {

    @CommandLine.Command(name = "now", description = "Run synchronization jobs now",
            mixinStandardHelpOptions = true)
    public static class NowCommand {

    }
}
