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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PyCloneSource that = (PyCloneSource) o;
        return Objects.equals(getWeight(), that.getWeight());
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
