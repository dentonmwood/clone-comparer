package com.csi.czech.source;

/**
 * File source read from PyClone. Implementation makes debugging easier.
 */
public class PyCloneSource extends Source {
    public PyCloneSource(String filename, Long startLine, Long endLine) {
        super(filename, startLine, endLine);
    }
}
