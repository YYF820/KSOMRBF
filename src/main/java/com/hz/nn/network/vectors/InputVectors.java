package com.hz.nn.network.vectors;

import com.hz.nn.network.model.InputVector;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dmytro_Hanzha.
 */
public class InputVectors {

    private final List<InputVector> inputVectors;

    public InputVectors() {
        inputVectors = new ArrayList<>(1000);
    }

    /**
     * Main constructor for this map. Used to contain all the input vectors.
     *
     * @param capacity Number of input vectors.
     */
    public InputVectors(int capacity) {
        inputVectors = new ArrayList<>(capacity);
    }

    /**
     * Returns a Node values of a specific input vector from the specified
     * index.
     *
     * @param index The index of SomNode.
     * @return double[] - returns the Node values from the specified index.
     */
    public double[] getInputVectorValuesAt(int index) {
        InputVector cache = inputVectors.get(index);
        return (cache.getValues());
    }

    /**
     * Returns a Node label of a specific input vector from the specified
     * index.
     *
     * @param index The index of SomNode.
     * @return String - returns the Node label from the specified index.
     */
    public String getInputVectorLabelAt(int index) {
        InputVector inputVector = inputVectors.get(index);
        return (inputVector.getLabel());
    }

    /**
     * Returns the number of input vectors.
     *
     * @return int - returns the number of input vectors.
     */
    public int getCount() {
        return inputVectors.size();
    }

    public void add(InputVector inputVector) {
        inputVectors.add(inputVector);
    }
}
