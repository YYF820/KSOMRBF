package com.hz.nn.network;

/**
 * @author Dmytro_Hanzha.
 */

import com.hz.nn.network.enums.GridType;
import com.hz.nn.network.enums.LearningType;
import com.hz.nn.network.enums.NeighbourhoodFunction;
import com.hz.nn.network.training.SomTraining;
import com.hz.nn.network.vectors.InputVectors;
import com.hz.nn.network.vectors.WeightVectors;
import net.sf.javaml.clustering.Clusterer;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.distance.DistanceMeasure;
import net.sf.javaml.distance.EuclideanDistance;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.hz.nn.network.util.NeuralUtilities.convertDataSetToInputVectors;
import static com.hz.nn.network.util.NeuralUtilities.doLabeling;

/**
 * @author Dmytro Hanzha
 */
public class SomNN implements Clusterer {
    private GridType gridType;
    private LearningType learningType;
    private NeighbourhoodFunction neighbourhoodFunction;
    private int xDimension, yDimension, iterations, initialRadius;
    private double learningRate;
    private DistanceMeasure distanceMeasure;

    /**
     * Create a 2 by 2 Self-organizing map, using a hexagonal grid, 1000
     * iteration, 0.1 learning rate, 8 initial radius, linear learning, a
     * step-wise neighborhood function and the Euclidean distance as distance
     * measure.
     */
    public SomNN() {
        this(2, 2, GridType.HEXAGONAL, 1000, 0.1, 8, LearningType.LINEAR, NeighbourhoodFunction.STEP);
    }

    /**
     * Create a new self-organizing map with the provided parameters and the
     * Euclidean distance as distance metric.
     *
     * @param xDimension    number of dimension on x-axis
     * @param yDimension    number of dimension on y-axis
     * @param grid          type of grid
     * @param iterations    number of iterations
     * @param learningRate  learning rate of the algorithm
     * @param initialRadius initial radius
     * @param learning      type of learning to use
     * @param nbf           neighborhood function
     */
    public SomNN(int xDimension, int yDimension, GridType grid, int iterations, double learningRate, int initialRadius,
                 LearningType learning, NeighbourhoodFunction nbf) {
        this(xDimension, yDimension, grid, iterations, learningRate, initialRadius, learning, nbf, new EuclideanDistance());
    }

    /**
     * Create a new self-organizing map with the provided parameters.
     *
     * @param xDimension      number of dimension on x-axis
     * @param yDimension      number of dimension on y-axis
     * @param grid            type of grid
     * @param iterations      number of iterations
     * @param learningRate    learning rate of the algorithm
     * @param initialRadius   initial radius
     * @param learning        type of learning to use
     * @param nbf             neighborhood function
     * @param distanceMeasure distance measure to use
     */
    public SomNN(int xDimension, int yDimension, GridType grid, int iterations, double learningRate, int initialRadius,
                 LearningType learning, NeighbourhoodFunction nbf, DistanceMeasure distanceMeasure) {
        this.gridType = grid;
        this.learningType = learning;
        this.neighbourhoodFunction = nbf;
        this.xDimension = xDimension;
        this.yDimension = yDimension;
        this.iterations = iterations;
        this.learningRate = learningRate;
        this.initialRadius = initialRadius;
        this.distanceMeasure = distanceMeasure;
    }

    @Override
    public Dataset[] cluster(Dataset data) {
        // hexa || rect
        WeightVectors weightVectors = new WeightVectors(xDimension, yDimension, data.noAttributes(), gridType.toString());
        InputVectors inputVectors = convertDataSetToInputVectors(data);
        // exponential || inverse || linear
        SomTraining somTraining = new SomTraining(inputVectors, weightVectors);
        // gaussian || step
        somTraining.setTrainingInstructions(iterations, learningRate, initialRadius, learningType.toString(),
                neighbourhoodFunction.toString());
        somTraining.doTraining(distanceMeasure);

        List<Dataset> allClusters = new ArrayList<>();
        for (int i = 0; i < weightVectors.size(); i++) {
            allClusters.add(new DefaultDataset());
        }

        doLabeling(weightVectors, inputVectors, data, allClusters, distanceMeasure);

        // Filter empty clusters out;
        List<Dataset> resultClusters = allClusters.stream()
                .filter(cluster -> cluster.size() > 0)
                .collect(Collectors.toList());

        return resultClusters.toArray(new Dataset[resultClusters.size()]);
    }
}
