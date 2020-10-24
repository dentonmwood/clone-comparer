package com.csi.czech.source;

/**
 * File source read from Moss. Implementation makes debugging easier.
 */
public class MossSource extends Source {
    private final Double percentMatch;

    public MossSource(String filename, Long startLine, Long endLine,
                      Double percentMatch) {
        super(filename, startLine, endLine);

        if (percentMatch < 0.0 || percentMatch > 1.0) {
            throw new IllegalArgumentException("Percent match must be a "
                    + "percentage");
        }
        this.percentMatch = percentMatch;
    }

    public Double getPercentMatch() {
        return percentMatch;
    }

    // Don't want to take tool-specific parameters into account
    @Override
    public boolean equals(Object o1) {
        return super.equals(o1);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
