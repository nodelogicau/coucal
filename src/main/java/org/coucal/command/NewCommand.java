package org.coucal.command;

import picocli.CommandLine;

import java.util.concurrent.Callable;

/**
 * A command used to create new collections and data. This command supports all collection types,
 * including calendar, card and attachments.
 *
 * Example: <code>coucal new meeting /local/calendars</code>
 */
@CommandLine.Command(name = "new", description = "Create new collections and data",
        mixinStandardHelpOptions = true)
public class NewCommand implements Callable<Integer> {

    @CommandLine.Parameters(index = "0")
    String template;

    @CommandLine.Parameters(index = "1", defaultValue = "")
    String path;

    @Override
    public Integer call() throws Exception {
        return 0;
    }
}
