package com.hz.nn.network.activation_functions;

import net.sf.javaml.core.Dataset;
import org.encog.mathutil.BoundMath;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * The type Activation gaussian.
 *
 * @author Dmytro_Hanzha.
 */
public class ActivationGaussian implements ActivationFunction {

    /**
     * The offset to the parameter that holds the width.
     */
    private static final int PARAM_GAUSSIAN_CENTER = 0;

    /**
     * The offset to the parameter that holds the peak.
     */
    private static final int PARAM_GAUSSIAN_PEAK = 1;

    /**
     * The offset to the parameter that holds the width.
     */
    private static final int PARAM_GAUSSIAN_WIDTH = 2;
    /**
     * The serial id.
     */
    private static final long serialVersionUID = -7166136514935838114L;
    /**
     * The parameters.
     */
    private double[] params;

    /**
     * Create a gaussian activation function.
     *
     * @param center The center of the curve.
     * @param peak   The peak of the curve.
     * @param width  The width of the curve.
     */
    public ActivationGaussian(final double center, final double peak,
                              final double width) {
        this.params = new double[3];
        this.params[ActivationGaussian.PARAM_GAUSSIAN_CENTER] = center;
        this.params[ActivationGaussian.PARAM_GAUSSIAN_PEAK] = peak;
        this.params[ActivationGaussian.PARAM_GAUSSIAN_WIDTH] = width;
    }

    /**
     * @return The object cloned.
     */
    @Override
    public ActivationFunction clone() {
        return new ActivationGaussian(this.getCenter(), this.getPeak(), this
                .getWidth());
    }

    /**
     * @return The width of the function.
     */
    private double getWidth() {
        return this.getParams()[ActivationGaussian.PARAM_GAUSSIAN_WIDTH];
    }

    /**
     * @return The center of the function.
     */
    private double getCenter() {
        return this.getParams()[ActivationGaussian.PARAM_GAUSSIAN_CENTER];
    }

    /**
     * @return The peak of the function.
     */
    private double getPeak() {
        return this.getParams()[ActivationGaussian.PARAM_GAUSSIAN_PEAK];
    }

    /**
     * @return Return true, gaussian has a derivative.
     */
    public boolean hasDerivative() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    public void activationFunction(final Double[] x, final int start,
                                   final int size) {
        for (int i = start; i < start + size; i++) {
            x[i] = params[ActivationGaussian.PARAM_GAUSSIAN_PEAK]
                    * BoundMath.exp(-Math.pow(x[i]
                    - params[ActivationGaussian.PARAM_GAUSSIAN_CENTER], 2)
                    / (2.0 * params[ActivationGaussian.PARAM_GAUSSIAN_WIDTH] * params[ActivationGaussian.PARAM_GAUSSIAN_WIDTH]));
        }

    }

    @Override
    public void doActivation(Dataset data, int start, int size) {
        data.forEach(i -> {
                    Double[] doubles = i.values().toArray(new Double[0]);
                    activationFunction(doubles, 0, data.noAttributes());
                    IntStream.range(0, doubles.length)
                            .forEach(index -> Arrays.stream(doubles).forEach(value -> i.put(index, value))
                            );
                }
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double derivativeFunction(final double x) {
        final double width = params[ActivationGaussian.PARAM_GAUSSIAN_WIDTH];
        final double peak = params[ActivationGaussian.PARAM_GAUSSIAN_PEAK];
        return Math.exp(-0.5 * width * width * x * x) * peak * width * width
                * (width * width * x * x - 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String[] getParamNames() {
        final String[] result = {"center", "peak", "width"};
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double[] getParams() {
        return params;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setParam(final int index, final double value) {
        this.params[index] = value;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getOpenCLExpression(final boolean derivative,
                                      final boolean allSlopeOne) {
        return null;
    }

}
