package com.csi.czech.cmd;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

/**
 * Represents a call to the help dialog for the
 * application. Handles the call.
 */
public class CloneHelpOptions implements CloneOptions {
    /** The CLI settings for the application */
    private Options options;
    /** Formatter for the help dialog */
    private HelpFormatter helpFormatter;

    /**
     * Constructor for the help options
     * @param options the CLI settings
     * @param helpFormatter the formatter for the help dialog
     */
    public CloneHelpOptions(Options options, HelpFormatter helpFormatter) {
        this.options = options;
        this.helpFormatter = helpFormatter;
    }

    /**
     * Returns the CLI settings
     * @return the options object
     */
    public Options getOptions() {
        return options;
    }

    /**
     * Returns the help formatter
     * @return the formatter
     */
    public HelpFormatter getHelpFormatter() {
        return helpFormatter;
    }

    /**
     * Implementation of the visitor method. Calls
     * the handler's method.
     * @param handler the handler for the pattern
     */
    public void accept(CLIHandler handler) {
        handler.visit(this);
    }
}
