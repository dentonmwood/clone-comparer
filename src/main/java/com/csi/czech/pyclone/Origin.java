package com.csi.czech.pyclone;

public class Origin {
    private String filename;
    private Long lineNumber;
    private Long columnNumber;

    public Origin(String filename, Long lineNumber, Long columnNumber) {
        this.filename = filename;
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
    }

    public String getFilename() {
        return filename;
    }

    public Long getLineNumber() {
        return lineNumber;
    }

    public Long getColumnNumber() {
        return columnNumber;
    }

    @Override
    public String toString() {
        return "Origin: " + filename + " @ " + lineNumber + ":" + columnNumber + "\n";
    }
}
