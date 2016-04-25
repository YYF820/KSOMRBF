package com.hz.nn.network.vectors;

import com.google.common.base.MoreObjects;
import com.hz.nn.network.model.SomNeuron;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Dmytro_Hanzha.
 */
public class WeightVectors {
    private static final double Y_VALUE = 0.866;
    private final List<SomNeuron> somNeurons;
    private int dimension;

    /**
     * */
    public WeightVectors(int xDimension, int yDimension, int dimension, String type) {
        somNeurons = new ArrayList<>(xDimension * yDimension);
        int size = xDimension * yDimension; // number of neurons
        this.dimension = dimension; //
        double[] values = new double[dimension];
        double[] location = new double[2];
        Random generator = new Random();
        int yCounter = 0;
        int xCounter = 0;
        double xValue = 0;
        double yValue = 0;
        boolean evenRow = false; // for hexagonal lattice, checking if the current row number is even or odd
        if (type.equals("rect")) { // rectangular lattice
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < dimension; j++) {
                    values[j] = generator.nextDouble();
                }
                if (xCounter < xDimension) {
                    location[0] = xCounter;
                    location[1] = yCounter;
                    xCounter++;
                } else {
                    xCounter = 0;
                    yCounter++;
                    location[0] = xCounter;
                    location[1] = yCounter;
                    xCounter++;
                }
                somNeurons.add(new SomNeuron(values, location));
            }
        } else { // hexagonal lattice
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < dimension; j++) {
                    values[j] = generator.nextDouble();
                }
                if (xCounter < xDimension) {
                    location[0] = xValue;
                    location[1] = yValue;
                    xValue += 1.0;
                    xCounter++;
                } else {
                    xCounter = 0;
                    yValue += Y_VALUE;
                    if (evenRow) {
                        xValue = 0.0;
                        evenRow = false;
                    } else {
                        xValue = 0.5;
                        evenRow = true;
                    }
                    location[0] = xValue;
                    location[1] = yValue;
                    xValue += 1.0;
                    xCounter++;
                }
                somNeurons.add(new SomNeuron(values, location));
            }
        }
    }

    /**
     * Returns the Som Neuron values at a specific som neuron.
     *
     * @param index Index of the Som Neuron
     * @return double[] - Returns the Som Neuron values from the specified index.
     */
    public double[] getSomNeuronValuesAt(int index) {
        SomNeuron somNeuron = somNeurons.get(index);
        return (somNeuron.getValues());
    }

    /**
     * Sets the Som Neuron values at a specific Som Neuron.
     *
     * @param index  Index of the SomNode
     * @param values Values of the SomNode
     */
    public void setSomNeuronValuesAt(int index, double[] values) {
        SomNeuron somNeuron = somNeurons.get(index);
        somNeuron.setValues(values);
        somNeurons.set(index, somNeuron);
    }

    /**
     * Returns the Some Neuron location from the specified index
     *
     * @param index Index of the SomNode
     * @return double[] - Returns the Node location from the specified
     * index.
     */
    public double[] getSomNeuronLocationAt(int index) {
        SomNeuron somNeuron = somNeurons.get(index);
        return (somNeuron.getLocation());
    }

    /**
     * Returns the dimensionality of a node (it is the same for all of
     * them).
     *
     * @return int - Dimensionality of nodes.
     */
    public int getDimensionalityOfNodes() {
        return dimension;
    }

    /**
     * Returns the number of weight vectors(Som Neurons).
     *
     * @return int - Returns the number of weight vectors (Som Neurons).
     */
    public int getCount() {
        return somNeurons.size();
    }

    public int size() {
        return getCount();
    }

    /**
     * Sets the label of a specific weight vector at the specified index.
     *
     * @param index The index of SomNode.
     * @param label The new label for this SomNode.
     * @return String - Returns the Node label from the specified index.
     */
    public void setNodeLabelAt(int index, String label) {
        SomNeuron somNeuron = somNeurons.get(index);
        if (somNeuron.isLabeled()) {
            somNeuron.addLabel(label);
        } else {
            somNeuron.setLabel(label);
        }
        somNeurons.set(index, somNeuron);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("somNeurons", somNeurons)
                .add("dimension", dimension)
                .toString();
    }
}
