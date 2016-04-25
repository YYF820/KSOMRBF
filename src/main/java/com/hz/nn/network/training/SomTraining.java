package com.hz.nn.network.training;

import com.hz.nn.network.vectors.InputVectors;
import com.hz.nn.network.vectors.WeightVectors;
import net.sf.javaml.distance.DistanceMeasure;

import java.util.Random;

import static com.hz.nn.network.util.NeuralUtilities.resolveIndexOfWinningNeuron;

/**
 * @author Dmytro_Hanzha.
 */
public class SomTraining {
    private final WeightVectors weightVectors;
    private int index; // caching

    private SomMath somMath;
    private InputVectors inputVectors;

    // step(bubble) | gaussian
    private String neigh; // the neighborhood function type used ::
    private int steps; // running length (number of steps) in training
    private double learningRate; // initial learning rate parameter value

    // exponential | linear | inverse
    private String learningRateType; // learning rate parameter type ::
    private double width; // initial "width" of training area
    private Random generator;
    private int weightVectorSize; // the number of weight vectors
    private int inputVectorSize; // the number of input vectors

    /**
     * Constructor.
     *
     * @param inputVectors - input vectors.
     */
    public SomTraining(InputVectors inputVectors, WeightVectors weightVectors) {
        this.weightVectors = weightVectors;
        this.inputVectors = inputVectors;
        somMath = new SomMath(weightVectors.getDimensionalityOfNodes());
        generator = new Random();
    }

    /**
     * Sets the ordering instructions for the ordering process.
     *
     * @param steps     - number of steps in this ordering phase.
     * @param lrate     - initial value for learning rate (usually near
     *                  0.1).
     * @param radius    - initial radius of neighbors.
     * @param lrateType - states which learning-rate parameter function
     *                  is used :: exponential | linear | inverse
     * @param neigh     - the neighborhood function type used ::
     *                  step(bubble) | gaussian
     */
    public void setTrainingInstructions(int steps, double lrate, int radius, String lrateType, String neigh) {
        this.steps = steps;
        this.learningRate = lrate;
        this.learningRateType = lrateType;
        this.neigh = neigh;
        width = radius;
    }

    /**
     * Does the training phase.
     *
     * @return WeightVectors - Returns the trained weight vectors.
     */
    public WeightVectors doTraining(DistanceMeasure distanceMeasure) {
        inputVectorSize = inputVectors.getCount();
        weightVectorSize = weightVectors.getCount();
        if (learningRateType.equals("exponential") && neigh.equals("step")) {
            doBubbleExpAdaptation(distanceMeasure);
        } else if (learningRateType.equals("linear") && neigh.equals("step")) {
            doBubbleLinAdaptation(distanceMeasure);
        } else if (learningRateType.equals("inverse") && neigh.equals("step")) {
            doBubbleInvAdaptation(distanceMeasure);
        } else if (learningRateType.equals("exponential") && neigh.equals("gaussian")) {
            doGaussianExpAdaptation(distanceMeasure);
        } else if (learningRateType.equals("linear") && neigh.equals("gaussian")) {
            doGaussianLinAdaptation(distanceMeasure);
        } else {
            // inverse and gaussian
            doGaussianInvAdaptation(distanceMeasure);
        }
        return weightVectors;
    }

    /*
     * Does the Bubble Exponential Adaptation to the Weight Vectors.
     */
    private void doBubbleExpAdaptation(DistanceMeasure distanceMeasure) {
        double[] input;
        double[] wLocation; // location of a winner node
        double s = (double) steps;
        double wCache; // width cache
        double exp;
        for (int n = 0; n < steps; n++) {
            wCache = Math.ceil(width * (1 - (n / s))); // adapts the width function as it is a function of time.
            exp = somMath.expLRP(n, learningRate, steps);
            input = inputVectors.getInputVectorValuesAt(generator.nextInt(inputVectorSize));
            index = resolveIndexOfWinningNeuron(input, weightVectors, distanceMeasure);
            wLocation = weightVectors.getSomNeuronLocationAt(index);
            for (int h = 0; h < weightVectorSize; h++) {
                weightVectors.setSomNeuronValuesAt(h,
                        somMath.bubbleAdaptation(input, weightVectors.getSomNeuronValuesAt(h), wLocation,
                                weightVectors.getSomNeuronLocationAt(h), wCache, exp, distanceMeasure));
            }
        }
    }

    /*
     * Does the Bubble Linear Adaptation to the Weight Vectors.
     */
    private void doBubbleLinAdaptation(DistanceMeasure distanceMeasure) {
        double[] input;
        double[] wLocation; // location of a winner node
        double s = (double) steps;
        double wCache; // width cache
        double lin;
        for (int n = 0; n < steps; n++) {
            wCache = Math.ceil(width * (1 - (n / s))); // adapts the width function as it is a function of time.
            lin = somMath.linLRP(n, learningRate, steps);
            input = inputVectors.getInputVectorValuesAt(generator.nextInt(inputVectorSize));
            index = resolveIndexOfWinningNeuron(input, weightVectors, distanceMeasure);
            wLocation = weightVectors.getSomNeuronLocationAt(index);
            for (int h = 0; h < weightVectorSize; h++) {
                weightVectors.setSomNeuronValuesAt(h,
                        somMath.bubbleAdaptation(input, weightVectors.getSomNeuronValuesAt(h), wLocation,
                                weightVectors.getSomNeuronLocationAt(h), wCache, lin, distanceMeasure)
                );
            }
        }
    }

    /*
     * Does the Bubble Inverse-time Adaptation to the Weight Vectors.
     */
    private void doBubbleInvAdaptation(DistanceMeasure distanceMeasure) {
        double[] input;
        double[] wLocation; // location of a winner node
        double A; // constants A and B which are considered equal
        double s = (double) steps;
        double wCache; // width cache
        double inv;
        A = steps / 100.0;
        for (int n = 0; n < steps; n++) {
            wCache = Math.ceil(width * (1 - (n / s))); // adapts the width function as it is a function of time.
            inv = somMath.invLRP(n, learningRate, A, A);
            input = inputVectors.getInputVectorValuesAt(generator.nextInt(inputVectorSize));
            index = resolveIndexOfWinningNeuron(input, weightVectors, distanceMeasure);
            wLocation = weightVectors.getSomNeuronLocationAt(index);
            for (int h = 0; h < weightVectorSize; h++) {
                weightVectors.setSomNeuronValuesAt(h,
                        somMath.bubbleAdaptation(input, weightVectors.getSomNeuronValuesAt(h), wLocation,
                                weightVectors.getSomNeuronLocationAt(h), wCache, inv, distanceMeasure)
                );
            }
        }
    }

    /*
     * Does the Gaussian Exponential Adaptation to the Weight Vectors.
     */
    private void doGaussianExpAdaptation(DistanceMeasure distanceMeasure) {
        // update the weight vectors with random instances.
        // the steps variable is the number of times this update is done
        for (int n = 0; n < steps; n++) {
            double wCache = somMath.gaussianWidth(width, n, steps);
            double exp = somMath.expLRP(n, learningRate, steps);
            double[] input = inputVectors.getInputVectorValuesAt(generator.nextInt(inputVectorSize));
            index = resolveIndexOfWinningNeuron(input, weightVectors, distanceMeasure); // winning node
            double[] wLocation = weightVectors.getSomNeuronLocationAt(index);
            for (int h = 0; h < weightVectorSize; h++) {
                weightVectors.setSomNeuronValuesAt(h,
                        somMath.gaussianAdaptation(input, weightVectors.getSomNeuronValuesAt(h), wLocation,
                                weightVectors.getSomNeuronLocationAt(h), wCache, exp, distanceMeasure)
                );
            }
        }
    }

    /*
     * Does the Gaussian Linear Adaptation to the Weight Vectors.
     */
    private void doGaussianLinAdaptation(DistanceMeasure distanceMeasure) {
        double[] input;
        double[] wLocation; // location of a winner node
        double wCache; // width cache
        double lin;
        for (int n = 0; n < steps; n++) {
            wCache = somMath.gaussianWidth(width, n, steps);
            lin = somMath.linLRP(n, learningRate, steps);
            input = inputVectors.getInputVectorValuesAt(generator.nextInt(inputVectorSize));
            index = resolveIndexOfWinningNeuron(input, weightVectors, distanceMeasure);
            wLocation = weightVectors.getSomNeuronLocationAt(index);
            for (int h = 0; h < weightVectorSize; h++) {
                weightVectors.setSomNeuronValuesAt(h,
                        somMath.gaussianAdaptation(input, weightVectors.getSomNeuronValuesAt(h), wLocation,
                                weightVectors.getSomNeuronLocationAt(h), wCache, lin, distanceMeasure));
            }
        }
    }

    /*
     * Does the Gaussian Inverse-time Adaptation to the Weight Vectors.
     */
    private void doGaussianInvAdaptation(DistanceMeasure distanceMeasure) {
        double[] input;
        double[] wLocation; // location of a winner node
        double A; // constants A and B which are considered equal
        double wCache; // width cache
        double inv;
        A = steps / 100.0;
        for (int n = 0; n < steps; n++) {
            wCache = somMath.gaussianWidth(width, n, steps);
            inv = somMath.invLRP(n, learningRate, A, A);
            input = inputVectors.getInputVectorValuesAt(generator.nextInt(inputVectorSize));
            index = resolveIndexOfWinningNeuron(input, weightVectors, distanceMeasure);
            wLocation = weightVectors.getSomNeuronLocationAt(index);
            for (int h = 0; h < weightVectorSize; h++) {
                weightVectors.setSomNeuronValuesAt(h,
                        somMath.gaussianAdaptation(input, weightVectors.getSomNeuronValuesAt(h), wLocation,
                                weightVectors.getSomNeuronLocationAt(h), wCache, inv, distanceMeasure));
            }
        }
    }
}
