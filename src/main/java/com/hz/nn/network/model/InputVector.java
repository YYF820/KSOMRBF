package com.hz.nn.network.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.util.Arrays;

/**
 * @author Dmytro_Hanzha.
 */
public class InputVector {
    private String label;
    private double[] values;

    public InputVector(String label, Double[] values) {
        this.label = label;
        this.values = new double[values.length];
        for (int i = 0; i < values.length; i++)
            this.values[i] = values[i];
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double[] getValues() {
        return values;
    }

    public void setValues(double[] values) {
        this.values = values;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InputVector that = (InputVector) o;
        return Objects.equal(label, that.label) &&
                Objects.equal(values, that.values);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(label, values);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("label", label)
                .add("values", values)
                .toString();
    }
}
