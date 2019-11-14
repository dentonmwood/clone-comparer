package com.csi.czech.source;

import java.util.Objects;

public abstract class Source implements Comparable<Source> {
    protected String filename;
    protected Long startLine;
    protected Long endLine;

    private static final int EQUALITY_THRESHOLD = 3;

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
        if (o == null) return false;
        Source source = (Source) o;

        if (!source.getFilename().equals(this.filename)) {
            return false;
        }

        return Math.abs(source.getStartLine() - this.startLine) < EQUALITY_THRESHOLD;
    }

    @Override
    public int hashCode() {
        return Objects.hash(filename, startLine, endLine);
    }

    @Override
    public int compareTo(Source source) {
        int fileCompare = this.filename.compareTo(source.getFilename());
        if (fileCompare != 0) {
            return fileCompare;
        }
        int startLineCompare = this.startLine.compareTo(source.getStartLine());
        if (startLineCompare != 0) {
            return startLineCompare;
        }
        return this.endLine.compareTo(source.getEndLine());
    }
}
