package com.hz.nn;

import com.hz.nn.network.SomNN;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.tools.data.FileHandler;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author Dmytro_Hanzha.
 */
public class Application {
    public static void main(String[] args) throws IOException {
        SomNN somNN = new SomNN();
        Dataset data = FileHandler.loadDataset(new File("D:\\Education\\diploma\\KSOMRBF\\build\\resources\\main\\iris.data"), 4, ",");
        Dataset[] cluster = somNN.cluster(data);
    }
}
