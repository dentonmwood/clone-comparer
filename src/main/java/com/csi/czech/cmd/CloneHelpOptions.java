package com.csi.czech.cmd;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

public class CloneHelpOptions implements CloneOptions {
    private Options options;
    private HelpFormatter helpFormatter;

    public CloneHelpOptions(Options options, HelpFormatter helpFormatter) {
        this.options = options;
        this.helpFormatter = helpFormatter;
    }

    public Options getOptions() {
        return options;
    }

    public HelpFormatter getHelpFormatter() {
        return helpFormatter;
    }

    public void accept(CLIHandler handler) {
        handler.visit(this);
    }
}
