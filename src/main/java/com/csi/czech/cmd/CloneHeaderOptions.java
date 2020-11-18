package com.csi.czech.cmd;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents header options to print.
 */
public class CloneHeaderOptions implements CloneOptions {
    /** Indicator for Oxygen */
    private boolean cycloneOxygen;
    /** Indicator for Chlorine */
    private boolean cycloneChlorine;
    /** Indicator for Iodine */
    private boolean cycloneIodine;
    /** Indicator for NiCad Blocks */
    private boolean nicadBlocks;
    /** Indicator for NiCad Functions */
    private boolean nicadFunctions;
    /** Indicator for Moss */
    private boolean moss;

    /**
     * Enum class representing the Cyclone algorithms
     */
    public enum CycloneTool {
        OXYGEN,
        CHLORINE,
        IODINE
    }

    /**
     * Enum class representing the tools to benchmark against Cyclone
     */
    public enum BenchmarkTool {
        NICAD_BLOCKS,
        NICAD_FUNCTIONS,
        MOSS
    }

    /**
     * Gets the Oxygen flag
     * @return the flag
     */
    public boolean isCycloneOxygen() {
        return cycloneOxygen;
    }

    /**
     * Sets the Oxygen flag
     * @param cycloneOxygen the flag to set
     */
    public void setCycloneOxygen(boolean cycloneOxygen) {
        this.cycloneOxygen = cycloneOxygen;
    }

    /**
     * Gets the Chlorine flag
     * @return the flag
     */
    public boolean isCycloneChlorine() {
        return cycloneChlorine;
    }

    /**
     * Sets the Chlorine flag
     * @param cycloneChlorine the flag to set
     */
    public void setCycloneChlorine(boolean cycloneChlorine) {
        this.cycloneChlorine = cycloneChlorine;
    }

    /**
     * Gets the Iodine flag
     * @return the flag
     */
    public boolean isCycloneIodine() {
        return cycloneIodine;
    }

    /**
     * Sets the Iodine flag
     * @param cycloneIodine the flag to set
     */
    public void setCycloneIodine(boolean cycloneIodine) {
        this.cycloneIodine = cycloneIodine;
    }

    /**
     * Gets the NiCad blocks flag
     * @return the flag
     */
    public boolean isNicadBlocks() {
        return nicadBlocks;
    }

    /**
     * Sets the NiCad blocks flag
     * @param nicadBlocks the flag to set
     */
    public void setNicadBlocks(boolean nicadBlocks) {
        this.nicadBlocks = nicadBlocks;
    }

    /**
     * Gets the NiCad functions flag
     * @return the flag
     */
    public boolean isNicadFunctions() {
        return nicadFunctions;
    }

    /**
     * Sets the NiCad functions flag
     * @param nicadFunctions the flag to set
     */
    public void setNicadFunctions(boolean nicadFunctions) {
        this.nicadFunctions = nicadFunctions;
    }

    /**
     * Gets the Moss flag
     * @return the flag
     */
    public boolean isMoss() {
        return moss;
    }

    /**
     * Sets the Moss flag
     * @param moss the flag to set
     */
    public void setMoss(boolean moss) {
        this.moss = moss;
    }

    /**
     * Gets the list of Cyclone algorithms to print
     * @return the Cyclone algorithms
     */
    public List<CycloneTool> getCycloneTools() {
        List<CycloneTool> tools = new ArrayList<>();
        if (this.cycloneOxygen) {
            tools.add(CycloneTool.OXYGEN);
        }
        if (this.cycloneChlorine) {
            tools.add(CycloneTool.CHLORINE);
        }
        if (this.cycloneIodine) {
            tools.add(CycloneTool.IODINE);
        }
        return tools;
    }

    /**
     * Gets the list of benchmark algorithms to print
     * @return the benchmark algorithms
     */
    public List<BenchmarkTool> getBenchmarkTools() {
        List<BenchmarkTool> tools = new ArrayList<>();
        if (this.nicadBlocks) {
            tools.add(BenchmarkTool.NICAD_BLOCKS);
        }
        if (this.nicadFunctions) {
            tools.add(BenchmarkTool.NICAD_FUNCTIONS);
        }
        if (this.moss) {
            tools.add(BenchmarkTool.MOSS);
        }
        return tools;
    }

    /**
     * Implementation of visitor method. Calls the handler's method.
     * @param handler the handler to call
     */
    public void accept(CLIHandler handler) {
        handler.visit(this);
    }
}
