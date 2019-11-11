package com.csi.czech.moss;

import com.csi.czech.common.Source;

public class MossSource extends Source {
    public MossSource(String filename, Long startLine, Long endLine) {
        super(filename, startLine, endLine);
    }

    @Override
    public String toString() {
        return "Source: " + this.filename + ": " + this.startLine + "-" + this.endLine;
    }
}
