package com.hz.nn.network.util;

import com.hz.nn.network.model.InputVector;
import com.hz.nn.network.vectors.InputVectors;
import com.hz.nn.network.vectors.WeightVectors;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.distance.DistanceMeasure;

import java.util.Arrays;
import java.util.List;

/**
 * @author Dmytro_Hanzha.
 */
public class NeuralUtilities {

    private NeuralUtilities() {
        throw new AssertionError("Instantiate util class.");
    }

    public static InputVectors convertDataSetToInputVectors(Dataset data) {
        InputVectors inputVectors = new InputVectors();
        for (int i = 0; i < data.size(); i++) {
            Double[] values = data.instance(i).values().toArray(new Double[0]);
            InputVector inputVector = new InputVector("inputVector_" + i, values);
            inputVectors.add(inputVector);
        }
        return inputVectors;
    }

    /**
     * Calculates the distance between two vectors using the distance function
     * that was supplied during creation of the SOM. If no distance measure was
     * specified, the Euclidean Distance will be used by default.
     *
     * @param firstVector  - 1st vector.
     * @param secondVector - 2nd vector.
     * @return double - returns the distance between two vectors, x and y
     */
    public static double getDistance(double[] firstVector, double[] secondVector, DistanceMeasure distanceMeasure) {
        return distanceMeasure.measure(new DenseInstance(firstVector), new DenseInstance(secondVector));
    }

    /**
     * Finds the winning neuron for this input vector. Determines the winning
     * neuron by calculating the distance of two vectors.
     *
     * @param values - values of an input vector.
     * @return int - index of the winning neuron.
     */
    public static int resolveIndexOfWinningNeuron(double[] values, WeightVectors weightVectors, DistanceMeasure distanceMeasure) {
        double bestDistance = getDistance(values, weightVectors.getSomNeuronValuesAt(0), distanceMeasure);
        int index = 0;
        for (int i = 1; i < weightVectors.size(); i++) {
            double dist = getDistance(values, weightVectors.getSomNeuronValuesAt(i), distanceMeasure);
            if (dist < bestDistance) {
                index = i;
                bestDistance = dist;
            }
        }
        return index;
    }

    /**
     * Does the labeling phase.
     *
     * @return WeightVectors - Returns the labeled weight vectors.
     */
    public static WeightVectors doLabeling(WeightVectors weightVectors, InputVectors inputVectors, Dataset data, List<Dataset> clusters, DistanceMeasure distanceMeasure) {
        for (int i = 0; i < data.size(); i++) {
            int indexOfWinningNeuron = NeuralUtilities.resolveIndexOfWinningNeuron(inputVectors.getInputVectorValuesAt(i), weightVectors, distanceMeasure);
            clusters.get(indexOfWinningNeuron).add(data.instance(i));
            weightVectors.setNodeLabelAt(indexOfWinningNeuron, inputVectors.getInputVectorLabelAt(i));
        }
        return weightVectors;
    }
}
