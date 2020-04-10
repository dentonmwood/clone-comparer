package com.csi.czech.source;

/**
 * File source read from PyClone. Implementation makes debugging easier.
 */
public class PyCloneSource extends Source {
    private Double weight;

    public PyCloneSource(String filename, Long startLine, Long endLine,
                         Double weight) {
        super(filename, startLine, endLine);
        this.weight = weight;
    }

    public Double getWeight() {
        return weight;
    }
}
