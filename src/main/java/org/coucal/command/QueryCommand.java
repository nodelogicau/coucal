package org.coucal.command;

import org.ical4j.connector.command.AbstractCommand;
import org.mnode.coucou.Query;
import picocli.CommandLine;

import java.util.List;

@CommandLine.Command(name = "query", description = "Filter queries for retrieving calendar and card data",
        subcommands = {QueryCommand.ListCommand.class}, mixinStandardHelpOptions = true)
public class QueryCommand {

    @CommandLine.Command(name = "list", description = "List configured filter queries",
            mixinStandardHelpOptions = true)
    public static class ListCommand extends AbstractCommand<List<Query>> {

        @Override
        public void run() {

        }
    }

    @CommandLine.Command(name = "add", description = "Add a new filter query",
            mixinStandardHelpOptions = true)
    public static class AddCommand {

    }
}
