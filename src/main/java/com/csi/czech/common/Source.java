package com.csi.czech.common;

public abstract class Source {
    protected String filename;
    protected Long startLine;

    public Source(String filename, Long startLine) {
        this.filename = filename;
        this.startLine = startLine;
    }

    public String getFilename() {
        return filename;
    }

    public Long getStartLine() {
        return startLine;
    }
}
