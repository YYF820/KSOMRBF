package com.hz.nn.network.activation_functions;

import net.sf.javaml.core.Dataset;

import java.io.Serializable;

/**
 * @author Dmytro_Hanzha.
 */
public interface ActivationFunction extends Serializable, Cloneable {

    /**
     * Implements the activation function. The array is modified according to
     * the activation function being used. See the class description for more
     * specific information on this type of activation function.
     *
     * @param d     The input array to the activation function.
     * @param start The starting index.
     * @param size  The number of values to calculate.
     */
    void activationFunction(Double[] d, int start, int size);

    void doActivation(Dataset data, int start, int size);

    /**
     * Calculate the derivative of the activation. It is assumed that the value
     * d, which is passed to this method, was the output from this activation.
     * This prevents this method from having to recalculate the activation, just
     * to recalculate the derivative.
     * <p>
     * The array is modified according derivative of the activation function
     * being used. See the class description for more specific information on
     * this type of activation function. Propagation training requires the
     * derivative. Some activation functions do not support a derivative and
     * will throw an error.
     *
     * @param d The input array to the activation function.
     * @return The derivative.
     */
    double derivativeFunction(double d);

    /**
     * @return Return true if this function has a derivative.
     */
    boolean hasDerivative();

    /**
     * @return The params for this activation function.
     */
    double[] getParams();

    /**
     * Set one of the params for this activation function.
     *
     * @param index The index of the param to set.
     * @param value The value to set.
     */
    void setParam(int index, double value);

    /**
     * @return The names of the parameters.
     */
    String[] getParamNames();

    ActivationFunction clone();

    /**
     * Returns the OpenCL expression for this activation function.
     *
     * @param allSlopeOne True if all activation functions have a slope of 1.
     * @param derivative  True if we want the derivative, false otherwise.
     * @return The OpenCL expression for this activation function.
     */
    String getOpenCLExpression(final boolean derivative,
                               final boolean allSlopeOne);
}