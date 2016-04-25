package com.hz.nn.network.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * @author Dmytro_Hanzha.
 */
public class SomNeuron {
    private String label;
    private double[] values;
    private double[] location;

    /**
     * Main constructor.
     */
    public SomNeuron() {
        values = new double[1];
        location = new double[1];
    }

    /**
     * Main constructor (for input vectors).
     *
     * @param label  - Name of this node.
     * @param values - All the values of this node.
     */
    public SomNeuron(String label, Double[] values) {
        this.label = label;
        this.values = new double[values.length];
        for (int i = 0; i < values.length; i++)
            this.values[i] = values[i];
        location = new double[1];
    }

    /**
     * Main constructor (for weight vectors).
     *
     * @param values   - All the values of this node.
     * @param location - The location of this node.
     */
    public SomNeuron(double[] values, double[] location) {
        label = "";
        this.values = values.clone();
        this.location = location.clone();
    }

    /**
     * Returns all the values of this som neuron.
     *
     * @return double[] - Returns the numerical presentation of this node.
     */
    public double[] getValues() {
        return values.clone();
    }

    /**
     * Sets values for every dimension in this som neuron.
     *
     * @param values - Sets all the values for som neuron.
     */
    public void setValues(double[] values) {
        this.values = values.clone();
    }

    /**
     * Set the secondary label(s) for this som neuron.
     *
     * @param label - Another label of this som neuron.
     */
    public void addLabel(String label) {
        this.label += ", " + label;
    }

    /**
     * Returns the label of this som neuron.
     *
     * @return String - Returns the label of this som neuron if any.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Set the label name for this som neuron.
     *
     * @param label - Label of this som neuron.
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Returns the location of this som neuron.
     *
     * @return double[] - Returns the location of this node if any.
     */
    public double[] getLocation() {
        return location.clone();
    }

    /**
     * Returns the information about wether labeling has been done.
     *
     * @return boolean - Returns true if this node has been labeled
     * otherwise false.
     */
    public boolean isLabeled() {
        return label.length() > 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SomNeuron somNeuron = (SomNeuron) o;
        return Objects.equal(label, somNeuron.label) &&
                Objects.equal(values, somNeuron.values) &&
                Objects.equal(location, somNeuron.location);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(label, values, location);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("label", label)
                .add("values", values)
                .add("location", location)
                .toString();
    }
}
