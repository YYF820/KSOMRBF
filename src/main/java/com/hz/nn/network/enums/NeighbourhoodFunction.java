package com.hz.nn.network.enums;

/**
 * @author Dmytro_Hanzha.
 */
public enum NeighbourhoodFunction {
    GAUSSIAN("gaussian"), STEP("step");
    private String tag;

    private NeighbourhoodFunction(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return tag;
    }
}
