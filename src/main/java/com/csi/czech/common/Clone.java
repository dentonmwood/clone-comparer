package com.csi.czech.common;

import java.util.Objects;
import java.util.Set;

public abstract class Clone {
    protected Set<Source> sources;

    public Clone(Set<Source> sources) {
        this.sources = sources;
    }

    public Set<Source> getSources() {
        return this.sources;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clone clone = (Clone) o;
        return Objects.equals(sources, clone.sources);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sources);
    }
}
