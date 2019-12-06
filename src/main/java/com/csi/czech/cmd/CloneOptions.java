package com.csi.czech.cmd;

import java.io.IOException;

/**
 * Interface for the visitor pattern
 */
public interface CloneOptions {
    /**
     * Abstract visitor method - calls the handler method
     * @param h the handler to call
     * @throws IOException if the handler method fails
     */
    void accept(CLIHandler h) throws IOException;
}
