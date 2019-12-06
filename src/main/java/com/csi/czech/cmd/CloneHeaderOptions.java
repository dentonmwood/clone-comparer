package com.csi.czech.cmd;

import java.util.ArrayList;
import java.util.List;

public class CloneHeaderOptions implements CloneOptions {
    private boolean pycloneOxygen;
    private boolean pycloneChlorine;
    private boolean pycloneIodine;
    private boolean nicadBlocks;
    private boolean nicadFunctions;
    private boolean moss;

    public enum PyCloneTool {
        OXYGEN,
        CHLORINE,
        IODINE
    }

    public enum BenchmarkTool {
        NICAD_BLOCKS,
        NICAD_FUNCTIONS,
        MOSS
    }

    public boolean isPycloneOxygen() {
        return pycloneOxygen;
    }

    public void setPycloneOxygen(boolean pycloneOxygen) {
        this.pycloneOxygen = pycloneOxygen;
    }

    public boolean isPycloneChlorine() {
        return pycloneChlorine;
    }

    public void setPycloneChlorine(boolean pycloneChlorine) {
        this.pycloneChlorine = pycloneChlorine;
    }

    public boolean isPycloneIodine() {
        return pycloneIodine;
    }

    public void setPycloneIodine(boolean pycloneIodine) {
        this.pycloneIodine = pycloneIodine;
    }

    public boolean isNicadBlocks() {
        return nicadBlocks;
    }

    public void setNicadBlocks(boolean nicadBlocks) {
        this.nicadBlocks = nicadBlocks;
    }

    public boolean isNicadFunctions() {
        return nicadFunctions;
    }

    public void setNicadFunctions(boolean nicadFunctions) {
        this.nicadFunctions = nicadFunctions;
    }

    public boolean isMoss() {
        return moss;
    }

    public void setMoss(boolean moss) {
        this.moss = moss;
    }

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

    public void accept(CLIHandler handler) {
        handler.visit(this);
    }
}
