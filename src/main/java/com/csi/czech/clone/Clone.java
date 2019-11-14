package com.csi.czech.clone;

import com.csi.czech.source.Source;

import java.util.*;

public abstract class Clone {
    protected Queue<Source> sources;

    public Clone() {
        this.sources = new PriorityQueue<>();
    }

    public void addSource(Source source) {
        this.sources.add(source);
    }

    public Queue<Source> getSources() {
        return this.sources;
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
