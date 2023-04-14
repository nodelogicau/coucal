package org.coucal.command;

import picocli.CommandLine;

/**
 * A command used to move data between collections. This command supports all collection types,
 * including calendar, card and attachments.
 */
@CommandLine.Command(name = "move", aliases = {"mv"}, description = "Move data between collections",
        mixinStandardHelpOptions = true)
public class MoveCommand {

    @CommandLine.Parameters(index = "0")
    String source;

    @CommandLine.Parameters(index = "1")
    String destination;
}
