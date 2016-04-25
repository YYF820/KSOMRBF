package com.hz.nn.network.normalize;

import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;
import net.sf.javaml.core.SparseInstance;
import net.sf.javaml.core.exception.TrainingRequiredException;
import net.sf.javaml.filter.AbstractFilter;
import net.sf.javaml.filter.instance.ReplaceValueFilter;
import net.sf.javaml.tools.DatasetTools;

/**
 * Created by ganzh on 4/25/2016.
 */
public class Normalizer extends AbstractFilter {
    /**
     * A normalization filter to the interval [-1,1]
     *
     */
    public Normalizer() {
        this(0, 2);
    }

    private double normalMiddle;

    private double normalRange;

    public Normalizer(double middle, double range) {
        this.normalMiddle = middle;
        this.normalRange = range;

    }

    private Instance currentRange = null;

    private Instance currentMiddle = null;

    public void build(Dataset data) {
        // Calculate the proper range and midrange
        Instance max = DatasetTools.maxAttributes(data);
        Instance min = DatasetTools.minAttributes(data);
        currentRange = max.minus(min);
        currentMiddle = min.add(max).divide(2);

    }

    @Override
    public void filter(Instance instance) {
        if (currentRange == null || currentMiddle == null)
            throw new TrainingRequiredException();

        if (instance instanceof DenseInstance) {
            Instance tmp = instance.minus(currentMiddle).divide(currentRange).multiply(normalRange).add(normalMiddle);
            instance.clear();
            instance.putAll(tmp);

        }
        if (instance instanceof SparseInstance) {
            for (int index : instance.keySet()) {
                instance.put(index, ((instance.value(index) - currentMiddle.value(index)) / currentRange.value(index))
                        * normalRange + normalMiddle);
            }
        }
        new ReplaceValueFilter(Double.NEGATIVE_INFINITY, normalMiddle).filter(instance);
        new ReplaceValueFilter(Double.POSITIVE_INFINITY, normalMiddle).filter(instance);
        new ReplaceValueFilter(Double.NaN, normalMiddle).filter(instance);
    }

    public void filter(Dataset data) {
        if (currentRange == null || currentMiddle == null)
            build(data);
        for (Instance i : data)
            filter(i);
    }
}
