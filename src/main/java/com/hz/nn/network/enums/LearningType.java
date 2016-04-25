package com.hz.nn.network.enums;

/**
 * @author Dmytro_Hanzha.
 */
public enum LearningType {
    EXPONENTIAL("exponential"), INVERSE("inverse"), LINEAR("linear");

    private String tag;

    private LearningType(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return tag;
    }
}
