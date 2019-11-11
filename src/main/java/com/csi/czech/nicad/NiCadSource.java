package com.csi.czech.nicad;

import com.csi.czech.common.Source;

public class NiCadSource extends Source {
    private Long pcId;

    public NiCadSource(String filename, Long startLine, Long endLine, Long pcId) {
        super(filename, startLine, endLine);
        this.pcId = pcId;
    }

    public Long getPcId() {
        return pcId;
    }

    @Override
    public String toString() {
        return "Source: " + this.filename + ": " + this.startLine + "-" + this.endLine + ", ID=" + this.pcId;
    }
}
