package com.csi.czech.clone;

import com.csi.czech.source.Source;

import java.util.*;

/**
 * Represents a generic detected clone. Consists of a number of sources
 * (file and line numbers of the code snippet with the clone)
 */
public abstract class Clone {
    /** The references to the files containing the clones */
    protected Queue<Source> sources;

    /**
     * Constructor for the Clone class
     */
    public Clone() {
        this.sources = new PriorityQueue<>();
    }

    /**
     * Adds a source to the queue
     *
     * @param source the source to add
     */
    public void addSource(Source source) {
        this.sources.add(source);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        Clone clone = (Clone) o;

        // I need to guarantee that the equals() method of Source gets called
        List sources1 = Arrays.asList(this.sources.toArray());
        List sources2 = Arrays.asList(clone.sources.toArray());

        return sources1.equals(sources2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sources);
    }
}
