package com.hz.nn.network.enums;

/**
 * Enumeration of all grid types that are supported in a self organizing
 * map.
 *
 * @author Dmytro_Hanzha.
 */
public enum GridType {
    /**
     * Hexagonal grid
     */
    HEXAGONAL("hexa"),
    /**
     * Rectangular grid
     */
    RECTANGLES("rect");

    private String tag;

    private GridType(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return tag;
    }
}
