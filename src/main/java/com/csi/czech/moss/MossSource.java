package com.csi.czech.moss;

import com.csi.czech.common.Source;

public class MossSource extends Source {
    private Long endLine;

    public MossSource(String filename, Long startLine, Long endLine) {
        super(filename, startLine);
        this.endLine = endLine;
    }

    public Long getEndLine() {
        return this.endLine;
    }

    @Override
    public String toString() {
        return "Source: " + this.filename + ": " + this.startLine + "-" + this.endLine;
    }
}
