package com.csi.czech.cmd;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents header options to print.
 */
public class CloneHeaderOptions implements CloneOptions {
    /** Indicator for Oxygen */
    private boolean pycloneOxygen;
    /** Indicator for Chlorine */
    private boolean pycloneChlorine;
    /** Indicator for Iodine */
    private boolean pycloneIodine;
    /** Indicator for NiCad Blocks */
    private boolean nicadBlocks;
    /** Indicator for NiCad Functions */
    private boolean nicadFunctions;
    /** Indicator for Moss */
    private boolean moss;

    /**
     * Enum class representing the PyClone algorithms
     */
    public enum PyCloneTool {
        OXYGEN,
        CHLORINE,
        IODINE
    }

    /**
     * Enum class representing the tools to benchmark against PyClone
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
    public boolean isPycloneOxygen() {
        return pycloneOxygen;
    }

    /**
     * Sets the Oxygen flag
     * @param pycloneOxygen the flag to set
     */
    public void setPycloneOxygen(boolean pycloneOxygen) {
        this.pycloneOxygen = pycloneOxygen;
    }

    /**
     * Gets the Chlorine flag
     * @return the flag
     */
    public boolean isPycloneChlorine() {
        return pycloneChlorine;
    }

    /**
     * Sets the Chlorine flag
     * @param pycloneChlorine the flag to set
     */
    public void setPycloneChlorine(boolean pycloneChlorine) {
        this.pycloneChlorine = pycloneChlorine;
    }

    /**
     * Gets the Iodine flag
     * @return the flag
     */
    public boolean isPycloneIodine() {
        return pycloneIodine;
    }

    /**
     * Sets the Iodine flag
     * @param pycloneIodine the flag to set
     */
    public void setPycloneIodine(boolean pycloneIodine) {
        this.pycloneIodine = pycloneIodine;
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
     * Gets the list of PyClone algorithms to print
     * @return the PyClone algorithms
     */
    public List<PyCloneTool> getPyCloneTools() {
        List<PyCloneTool> tools = new ArrayList<>();
        if (this.pycloneOxygen) {
            tools.add(PyCloneTool.OXYGEN);
        }
        if (this.pycloneChlorine) {
            tools.add(PyCloneTool.CHLORINE);
        }
        if (this.pycloneIodine) {
            tools.add(PyCloneTool.IODINE);
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
