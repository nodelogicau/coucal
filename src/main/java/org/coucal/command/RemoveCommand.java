package org.coucal.command;

import picocli.CommandLine;

import java.util.List;

/**
 * A command used to remove data from collections. This command supports all collection types,
 * including calendar, card and attachments.
 */
@CommandLine.Command(name = "remove", aliases = {"rm"}, description = "Remove data from collections",
        mixinStandardHelpOptions = true)
public class RemoveCommand {

    @CommandLine.Parameters
    List<String> sources;
}
