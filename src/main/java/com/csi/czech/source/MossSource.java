package com.csi.czech.source;

/**
 * File source read from Moss. Implementation makes debugging easier.
 */
public class MossSource extends Source {
    public MossSource(String filename, Long startLine, Long endLine) {
        super(filename, startLine, endLine);
    }
}
