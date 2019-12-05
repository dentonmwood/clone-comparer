package com.csi.czech.cmd;

public class CloneOptions {
    private boolean dualMode;
    private String pycloneOxygenFile;
    private String pycloneChlorineFile;
    private String pycloneIodineFile;
    private String nicadBlocksFile;
    private String nicadFunctionsFile;
    private String mossFile;

    public boolean isDualMode() {
        return dualMode;
    }

    public void setDualMode(boolean dualMode) {
        this.dualMode = dualMode;
    }

    public String getPycloneOxygenFile() {
        return pycloneOxygenFile;
    }

    public void setPycloneOxygenFile(String pycloneOxygenFile) {
        this.pycloneOxygenFile = pycloneOxygenFile;
    }

    public String getPycloneChlorineFile() {
        return pycloneChlorineFile;
    }

    public void setPycloneChlorineFile(String pycloneChlorineFile) {
        this.pycloneChlorineFile = pycloneChlorineFile;
    }

    public String getPycloneIodineFile() {
        return pycloneIodineFile;
    }

    public void setPycloneIodineFile(String pycloneIodineFile) {
        this.pycloneIodineFile = pycloneIodineFile;
    }

    public String getNicadBlocksFile() {
        return nicadBlocksFile;
    }

    public void setNicadBlocksFile(String nicadBlocksFile) {
        this.nicadBlocksFile = nicadBlocksFile;
    }

    public String getNicadFunctionsFile() {
        return nicadFunctionsFile;
    }

    public void setNicadFunctionsFile(String nicadFunctionsFile) {
        this.nicadFunctionsFile = nicadFunctionsFile;
    }

    public String getMossFile() {
        return mossFile;
    }

    public void setMossFile(String mossFile) {
        this.mossFile = mossFile;
    }
}
