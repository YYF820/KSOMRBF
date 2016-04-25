package com.hz.nn;

import com.hz.nn.network.SomNN;
import com.hz.nn.network.enums.GridType;
import com.hz.nn.network.enums.LearningType;
import com.hz.nn.network.enums.NeighbourhoodFunction;
import net.sf.javaml.core.Dataset;
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
        NormalizeMidrange nmr = new NormalizeMidrange();
        SomNN somNN = new SomNN(1, 3, GridType.RECTANGLES, 1000, 0.05, 8, LearningType.LINEAR, NeighbourhoodFunction.STEP);
        Dataset data = FileHandler.loadDataset(new File(classLoader.getResource("iris.data").getFile()), 4, ",");
        nmr.filter(data);
        Dataset[] cluster = somNN.cluster(data);
        System.out.println(cluster.length);
        System.out.println(cluster[0]);
    }
}
