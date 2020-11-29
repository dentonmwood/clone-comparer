package com.csi.czech.cmd;

import java.io.IOException;

/**
 * Represents a set of files to be processed and compared.
 * Files are divided based on algorithm, but we're comparing
 * Cyclone files to Moss and NiCad files.
 */
public class CloneFileOptions implements CloneOptions {
    /**
     * Enum for whether the repos were compared in single-mode
     * (against themselves) or in dual-mode (against another
     * repository)
     */
    public enum CloneMode {
        SINGLE,
        DOUBLE
    }

    /** Mode in which the files were generated */
    private CloneMode mode;
    /** Whether to only show counts instead of percentages and times */
    private Boolean onlyShowCounts = false;
    /** File for Oxygen */
    private String cycloneOxygenFile;
    /** File for Chlorine */
    private String cycloneChlorineFile;
    /** File for Iodine */
    private String cycloneIodineFile;
    /** File for NiCad - blocks algorithm */
    private String nicadBlocksFile;
    /** File for NiCad - functions algorithm */
    private String nicadFunctionsFile;
    /** File for Moss (just contains URL */
    private String mossFile;

    /**
     * Returns the mode option
     * @return the mode option
     */
    public CloneMode getMode() {
        return mode;
    }

    /**
     * Sets the mode option
     * @param mode the mode to set
     */
    public void setMode(CloneMode mode) {
        this.mode = mode;
    }

    /**
     * Returns whether to only show counts
     * @return the counts option
     */
    public Boolean getOnlyShowCounts() {
        return onlyShowCounts;
    }

    /**
     * Sets the option to only show counts
     * @param onlyShowCounts the option to set
     */
    public void setOnlyShowCounts(Boolean onlyShowCounts) {
        this.onlyShowCounts = onlyShowCounts;
    }

    /**
     * Returns the Oxygen file
     * @return the filename
     */
    public String getCycloneOxygenFile() {
        return cycloneOxygenFile;
    }

    /**
     * Sets the Oxygen file
     * @param cycloneOxygenFile the filename to set
     */
    public void setCycloneOxygenFile(String cycloneOxygenFile) {
        this.cycloneOxygenFile = cycloneOxygenFile;
    }

    /**
     * Returns the Chlorine file
     * @return the filename
     */
    public String getCycloneChlorineFile() {
        return cycloneChlorineFile;
    }

    /**
     * Sets the Chlorine file
     * @param cycloneChlorineFile the filename to set
     */
    public void setCycloneChlorineFile(String cycloneChlorineFile) {
        this.cycloneChlorineFile = cycloneChlorineFile;
    }

    /**
     * Returns the Iodine file
     * @return the filename
     */
    public String getCycloneIodineFile() {
        return cycloneIodineFile;
    }

    /**
     * Sets the Iodine file
     * @param cycloneIodineFile the filename to set
     */
    public void setCycloneIodineFile(String cycloneIodineFile) {
        this.cycloneIodineFile = cycloneIodineFile;
    }

    /**
     * Returns the NiCad blocks file
     * @return the filename
     */
    public String getNicadBlocksFile() {
        return nicadBlocksFile;
    }

    /**
     * Sets the NiCad blocks file
     * @param nicadBlocksFile the filename to set
     */
    public void setNicadBlocksFile(String nicadBlocksFile) {
        this.nicadBlocksFile = nicadBlocksFile;
    }

    /**
     * Returns the NiCad functions file
     * @return the filename
     */
    public String getNicadFunctionsFile() {
        return nicadFunctionsFile;
    }

    /**
     * Sets the NiCad functions file
     * @param nicadFunctionsFile the filename
     */
    public void setNicadFunctionsFile(String nicadFunctionsFile) {
        this.nicadFunctionsFile = nicadFunctionsFile;
    }

    /**
     * Returns the file containing the Moss URL
     * @return the filename
     */
    public String getMossFile() {
        return mossFile;
    }

    /**
     * Sets the file containing the Moss URL
     * @param mossFile the filename to set
     */
    public void setMossFile(String mossFile) {
        this.mossFile = mossFile;
    }

    /**
     * Visitor method - calls the handler with itself
     * @param handler the visitor handler
     * @throws IOException if the handler method fails
     */
    public void accept(CLIHandler handler) throws IOException {
        handler.visit(this);
    }
}
