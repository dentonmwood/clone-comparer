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

    private static int MAX_NUM_SOURCES = 2;

    /**
     * Constructor for the Clone class
     */
    public Clone() {
        this.sources = new PriorityQueue<>(2,
                Comparator.comparing(Source::getFilename));
    }

    /**
     * Adds a source to the queue
     *
     * @param source the source to add
     */
    public void addSource(Source source) {
        if (this.sources.size() == MAX_NUM_SOURCES) {
            throw new IllegalStateException("Clone cannot have more than 2 "
                    + "sources");
        }
        this.sources.add(source);
    }

    /**
     * Gets the sources from the clone
     *
     * @return the queue of sources
     */
    public Queue<Source> getSources() {
        return new PriorityQueue<>(this.sources);
    }

    public Source getSource1() {
        if (this.sources.size() > 0) {
            return this.sources.element();
        }
        return null;
    }

    public Source getSource2() {
        if (this.sources.size() > 1) {
            Iterator<Source> iterator = this.sources.iterator();
            iterator.next();
            return iterator.next();
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        Clone clone = (Clone) o;

        Iterator<Source> iter = this.sources.iterator();
        Source mySource1 = iter.next();
        Source mySource2 = iter.next();

        iter = clone.getSources().iterator();
        Source yourSource1 = iter.next();
        Source yourSource2 = iter.next();

        return mySource1.equals(yourSource1) && mySource2.equals(yourSource2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sources);
    }
}
