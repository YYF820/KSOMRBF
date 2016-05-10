package com.hz.nn.network.vectors;

import com.google.common.base.MoreObjects;
import com.hz.nn.network.model.InputVector;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Dmytro_Hanzha.
 */
public class InputVectors implements Iterable<InputVector> {

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

    public int numberOfAttributes() {
        return inputVectors.get(0).getValues().length;
    }

    @Override
    public Iterator<InputVector> iterator() {
        return inputVectors.iterator();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("inputVectors", inputVectors)
                .toString();
    }
}


