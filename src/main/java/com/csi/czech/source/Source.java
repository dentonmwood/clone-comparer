package com.csi.czech.source;

import java.util.Objects;

/**
 * Generic representation of a snippet of code within a file which is identified
 * as a clone with some other snippet.
 */
public abstract class Source implements Comparable<Source> {
    /** File where snippet was found */
    protected String filename;
    /** Start line of snippet */
    protected Long startLine;
    /** End line of snippet */
    protected Long endLine;

    /**
     * Wiggle factor for clone checking. Clones may not be exactly the same
     * lines, and we want to check for that.
     */
    private static final int EQUALITY_THRESHOLD = 3;

    /**
     * Constructor for the source class
     * @param filename name of the file
     * @param startLine starting line
     * @param endLine ending line
     */
    public Source(String filename, Long startLine, Long endLine) {
        if (filename == null) {
            throw new NullPointerException("filename cannot be null");
        }
        if (startLine <= 0) {
            throw new IllegalArgumentException("Start line must be at least 1");
        }
        if (endLine <= 0) {
            throw new IllegalArgumentException("End line must be at least 1");
        }

        this.filename = filename;
        this.startLine = startLine;
        this.endLine = endLine;
    }

    /**
     * Getter for the filename
     * @return the filename
     */
    public String getFilename() {
        return this.filename;
    }

    /**
     * Getter for the start line
     * @return the start line
     */
    public Long getStartLine() {
        return this.startLine;
    }

    /**
     * Getter for the end line
     * @return the end line
     */
    public Long getEndLine() {
        return this.endLine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null) {
            return false;
        }
        Source source = (Source) o;

        if (!source.getFilename().equals(this.filename)) {
            return false;
        }

        // Check if clones are close to each other
        if (Math.abs(source.getStartLine() - this.startLine) <= EQUALITY_THRESHOLD &&
                Math.abs(source.getEndLine() - this.endLine) <= EQUALITY_THRESHOLD) {
            return true;
        }

        // Check if clones are contained within one another
        if ((source.getStartLine() < this.startLine && source.getEndLine() > this.endLine) ||
                source.getStartLine() > this.startLine && source.getEndLine() < this.endLine) {
            return true;
        }

        return false;
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

    @Override
    public String toString() {
        return "Source: " + this.filename + ": " + this.startLine + "-" + this.endLine;
    }
}
