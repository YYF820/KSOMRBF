package com.hz.nn.network.training;

import net.sf.javaml.distance.DistanceMeasure;

import static com.hz.nn.network.util.NeuralUtilities.getDistance;

/**
 * @author Dmytro_Hanzha.
 */
public class SomMath {
    private double[] cacheVector; // cache vector for temporary storage.
    private int sizeVector; // size of the cache vector.
    private double gaussianCache; // double cache for gaussian

    /**
     * Constructor.
     *
     * @param vectorSize - Size of a weight/input vector.
     */
    public SomMath(int vectorSize) {
        cacheVector = new double[vectorSize];
        sizeVector = cacheVector.length;
    }

    /**
     * Calculates the exponential learning-rate parameter value.
     *
     * @param n - current step (time).
     * @param a - initial value for learning-rate parameter (should be close to 0.1).
     * @param A - time constant (usually the number of iterations in the learning process).
     * @return double - exponential learning-rate parameter value.
     */
    public double expLRP(int n, double a, int A) {
        return (a * Math.exp(-1.0 * ((double) n) / ((double) A)));
    }

    /**
     * Calculates the linear learning-rate parameter value.
     *
     * @param n - current step (time).
     * @param a - initial value for learning-rate parameter (should be close to 0.1).
     * @param A - another constant (usually the number of iterations in the learning process).
     * @return double - linear learning-rate parameter value.
     */
    public double linLRP(int n, double a, int A) {
        return (a * (1 - ((double) n) / ((double) A)));
    }

    /**
     * Calculates the inverse time learning-rate parameter value.
     *
     * @param n - current step (time).
     * @param a - initial value for learning-rate parameter (should be close to 0.1).
     * @param A - another constant.
     * @param B - another constant.
     * @return double - inverse time learning-rate parameter value.
     */
    public double invLRP(int n, double a, double A, double B) {
        return (a * (A / (B + n)));
    }

    /**
     * Calculates the gaussian neighbourhood width value.
     *
     * @param g - initial width value of the neighbourhood.
     * @param n - current step (time).
     * @param t - time constant (usually the number of iterations in the learning process).
     * @return double - adapted gaussian neighbourhood function value.
     */
    public double gaussianWidth(double g, int n, int t) {
        return (g * Math.exp(-1.0 * ((double) n) / ((double) t)));
    }

    /**
     * Calculates the Gaussian neighbourhood value.
     *
     * @param i     - winning neuron location in the lattice.
     * @param j     - excited neuron location in the lattice.
     * @param width - width value of the neighbourhood.
     * @return double - Gaussian neighbourhood value.
     */
    private double gaussianNF(double[] i, double[] j, double width, DistanceMeasure distanceMeasure) {
        gaussianCache = getDistance(i, j, distanceMeasure);
        return (Math.exp(-1.0 * gaussianCache * gaussianCache / (2.0 * width * width)));
    }

    /**
     * Calculates whether the excited neuron is in the Bubble neighbourhood
     * set.
     *
     * @param i - winning neuron location in the lattice.
     * @param j - excited neuron location in the lattice.
     * @param g - width value of the neighbourhood.
     * @return boolean - true if located in the Bubble neighbourhood set.
     */
    private boolean bubbleNF(double[] i, double[] j, double g, DistanceMeasure distanceMeasure) {
        return getDistance(i, j, distanceMeasure) <= g;
    }

    /**
     * Calculates the new adapted values for a weight vector, based on
     * Bubble neighbourhood.
     *
     * @param x   - input vector.
     * @param w   - weight vector.
     * @param i   - winning neuron location in the lattice.
     * @param j   - excited neuron location in the lattice.
     * @param g   - adapted width value of the neighbourhood.
     * @param lrp - adapted learning-rate parameter value.
     * @return double[] - Returns the adapted neuron values.
     */
    public double[] bubbleAdaptation(double[] x, double[] w, double[] i, double[] j, double g, double lrp, DistanceMeasure distanceMeasure) {
        if (bubbleNF(i, j, g, distanceMeasure)) {
            for (int k = 0; k < sizeVector; k++) {
                cacheVector[k] = w[k] + lrp * (x[k] - w[k]);
            }
        } else {
            return w;
        }
        return cacheVector;
    }

    /**
     * Calculates the new adapted values for a weight vector, based on
     * Gaussian neighbourhood.
     *
     * @param x     - input vector.
     * @param w     - weight vector.
     * @param i     - winning neuron location in the lattice.
     * @param j     - excited neuron location in the lattice.
     * @param width - adapted width value of the neighbourhood.
     * @param lrp   - adapted learning-rate parameter value.
     * @return double[] - Returns the adapted neuron values.
     */
    public double[] gaussianAdaptation(double[] x, double[] w, double[] i, double[] j, double width, double lrp, DistanceMeasure distanceMeasure) {
        gaussianCache = gaussianNF(i, j, width, distanceMeasure);
        for (int k = 0; k < sizeVector; k++) {
            cacheVector[k] = w[k] + lrp * gaussianCache * (x[k] - w[k]);
        }
        return cacheVector;
    }
}
