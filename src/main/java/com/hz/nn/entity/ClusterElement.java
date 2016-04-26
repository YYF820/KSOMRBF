package com.hz.nn.entity;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.util.List;

/**
 * @author Dmytro_Hanzha.
 */
public class ClusterElement {

    private final int numberOfCluster;
    private final List<Double> values;
    private final String label;
    private final String className;

    public ClusterElement(int numberOfCluster, List<Double> values, String label, String className) {
        this.numberOfCluster = numberOfCluster;
        this.values = values;
        this.label = label;
        this.className = className;
    }

    public int getNumberOfCluster() {
        return numberOfCluster;
    }

    public List<Double> getValues() {
        return values;
    }

    public String getLabel() {
        return label;
    }

    public String getClassName() {
        return className;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClusterElement that = (ClusterElement) o;
        return numberOfCluster == that.numberOfCluster &&
                Objects.equal(values, that.values) &&
                Objects.equal(label, that.label) &&
                Objects.equal(className, that.className);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(numberOfCluster, values, label, className);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("numberOfCluster", numberOfCluster)
                .add("values", values)
                .add("label", label)
                .add("className", className)
                .toString();
    }
}
