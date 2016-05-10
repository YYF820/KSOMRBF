package com.hz.nn;

import com.hz.nn.network.SomNN;
import com.hz.nn.network.enums.GridType;
import com.hz.nn.network.enums.LearningType;
import com.hz.nn.network.enums.NeighbourhoodFunction;
import net.sf.javaml.clustering.evaluation.ClusterEvaluation;
import net.sf.javaml.clustering.evaluation.SumOfSquaredErrors;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.distance.EuclideanDistance;
import net.sf.javaml.filter.normalize.NormalizeMidrange;
import net.sf.javaml.tools.data.FileHandler;

import java.io.File;
import java.io.IOException;

/**
 * @author Dmytro_Hanzha.
 */
public class Application {
    public static void main(String[] args) throws IOException {
        ClassLoader classLoader = Application.class.getClassLoader();
        Dataset data = FileHandler.loadDataset(new File(classLoader.getResource("iris.data").getFile()), 4, ",");

        NormalizeMidrange nmr = new NormalizeMidrange();
        nmr.filter(data);

        SomNN somNN = new SomNN(1, 3, GridType.HEXAGONAL, 100000, 0.05, 1, LearningType.LINEAR, NeighbourhoodFunction.GAUSSIAN, new EuclideanDistance());

        Dataset[] cluster = somNN.cluster(data);
        System.out.println(cluster.length);
        System.out.println("===============================");
        for (Dataset instances : cluster) {
            System.out.println(instances.size());
            for (Instance instance : instances) {
                System.out.print(instance.classValue() + " ");
            }
            System.out.println("===============================");

        }

        ClusterEvaluation sse= new SumOfSquaredErrors();
        System.out.println(sse.score(cluster));
    }
}
