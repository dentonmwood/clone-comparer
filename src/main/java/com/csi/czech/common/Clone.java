package com.csi.czech.common;

import java.util.List;
import java.util.Objects;

public abstract class Clone {
    protected List<Source> sources;

    public Clone(List<Source> sources) {
        this.sources = sources;
    }

    public List<Source> getSources() {
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
