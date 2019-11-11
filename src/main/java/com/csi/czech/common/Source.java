package com.csi.czech.common;

import java.util.Objects;

public abstract class Source {
    protected String filename;
    protected Long startLine;
    protected Long endLine;

    public Source(String filename, Long startLine, Long endLine) {
        this.filename = filename;
        this.startLine = startLine;
        this.endLine = endLine;
    }

    public String getFilename() {
        return this.filename;
    }

    public Long getStartLine() {
        return this.startLine;
    }

    public Long getEndLine() {
        return this.endLine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Source source = (Source) o;
        return Objects.equals(filename, source.filename) &&
                Objects.equals(startLine, source.startLine) &&
                Objects.equals(endLine, source.endLine);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filename, startLine, endLine);
    }
}
