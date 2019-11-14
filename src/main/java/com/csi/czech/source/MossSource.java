package com.csi.czech.source;

public class MossSource extends Source {
    public MossSource(String filename, Long startLine, Long endLine) {
        super(filename, startLine, endLine);
    }

    @Override
    public String toString() {
        return "Source: " + this.filename + ": " + this.startLine + "-" + this.endLine;
    }
}
