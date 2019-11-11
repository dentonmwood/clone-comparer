package com.csi.czech.pyclone;

import com.csi.czech.common.Source;

public class PyCloneSource extends Source {
    private Long columnNumber;

    public PyCloneSource(String filename, Long startLine, Long columnNumber) {
        super(filename, startLine, 0L);
        this.columnNumber = columnNumber;
    }

    public Long getColumnNumber() {
        return columnNumber;
    }

    @Override
    public String toString() {
        return "Source: " + filename + " @ " + startLine + ":" + columnNumber + "\n";
    }
}
