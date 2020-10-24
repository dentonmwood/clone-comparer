package com.csi.czech.clone;

import com.csi.czech.source.Source;

import java.util.*;

/**
 * Represents a generic detected clone. Consists of a number of sources
 * (file and line numbers of the code snippet with the clone).
 *
 * The sources are implemented as a queue to ensure an ordering based on
 * filename while still allowing us to specify a (small) initial value to
 * reduce memory usage. This helps us eliminate duplicates.
 */
public abstract class Clone {
    /** The references to the files containing the clones */
    protected Queue<Source> sources;

    private static final int MAX_NUM_SOURCES = 2;

    /**
     * Constructor for the Clone class
     */
    public Clone() {
        this.sources = new PriorityQueue<>(2, (o1, o2) -> {
            int filename = o1.getFilename().compareTo(o2.getFilename());
            if (filename != 0) {
                return filename;
            }

            int startLine = o1.getStartLine().compareTo(o2.getStartLine());
            if (startLine != 0) {
                return startLine;
            }

            return o1.getEndLine().compareTo(o2.getEndLine());
        });

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

    /**
     * Returns the first source.
     *
     * @return the source
     */
    public Source getSource1() {
        if (!this.sources.isEmpty()) {
            return this.sources.element();
        }
        return null;
    }

    /**
     * Returns the second source
     *
     * @return the source
     */
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
        if (this == o) {
            return true;
        }

        if (o == null) {
            return false;
        }

        if (!(o instanceof Clone)) {
            return false;
        }

        Clone clone = (Clone) o;

        Iterator<Source> iter = this.sources.iterator();
        Source mySource1 = iter.next();
        Source mySource2 = iter.next();

        return mySource1.equals(clone.getSource1())
                && mySource2.equals(clone.getSource2());
    }

    @Override
    public int hashCode() {
        return Objects.hash(sources);
    }
}
