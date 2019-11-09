package com.csi.czech.nicad;

import com.csi.czech.common.Source;

public class NiCadSource extends Source {
    private Long endLine;
    private Long pcId;

    public NiCadSource(String filename, Long startLine, Long endLine, Long pcId) {
        super(filename, startLine);
        this.endLine = endLine;
        this.pcId = pcId;
    }

    public Long getEndLine() {
        return endLine;
    }

    public Long getPcId() {
        return pcId;
    }

    @Override
    public String toString() {
        return "Origin: " + this.filename + ": " + this.startLine + "-" + this.endLine + ", ID=" + this.pcId;
    }
}
