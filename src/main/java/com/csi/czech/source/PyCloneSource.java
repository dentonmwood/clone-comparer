package com.csi.czech.source;

import java.util.Objects;

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

    // Don't want to take tool-specific parameters into account
    @Override
    public boolean equals(Object o1) {
        return super.equals(o1);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getWeight());
    }

    @Override
    public String toString() {
        return "PyCloneSource{" +
                "weight=" + weight +
                ", filename='" + filename + '\'' +
                ", startLine=" + startLine +
                ", endLine=" + endLine +
                '}';
    }
}
