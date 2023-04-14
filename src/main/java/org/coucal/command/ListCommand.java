package org.coucal.command;

import picocli.CommandLine;

/**
 * A command used the list the contents of a collection. This command supports all collection types,
 * including calendar, card and attachments.
 */
@CommandLine.Command(name = "list", aliases = {"ls"}, description = "List collection contents",
        mixinStandardHelpOptions = true)
public class ListCommand {

    @CommandLine.Parameters(index = "0")
    String path;
}
