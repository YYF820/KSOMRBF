package com.hz.nn;

import com.hz.nn.network.SomNN;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.tools.data.FileHandler;

import java.io.File;
import java.io.IOException;

/**
 * @author Dmytro_Hanzha.
 */
public class Application {
    public static void main(String[] args) throws IOException {
        ClassLoader classLoader = Application.class.getClassLoader();
        SomNN somNN = new SomNN();
        Dataset data = FileHandler.loadDataset(new File(classLoader.getResource("iris.data").getFile()), 4, ",");
        Dataset[] cluster = somNN.cluster(data);
        System.out.println(cluster.length);
    }
}
