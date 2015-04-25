package com.company;

import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.KNearestNeighbors;
import net.sf.javaml.classification.evaluation.EvaluateDataset;
import net.sf.javaml.classification.evaluation.PerformanceMeasure;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.tools.DatasetTools;
import net.sf.javaml.tools.data.ARFFHandler;

import java.io.File;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws java.io.FileNotFoundException {
	// write your code here
        Dataset data1, data = new DefaultDataset();
        Dataset dataForTest1, dataForTest = new DefaultDataset();

        try {
            for (int i = 0; i < 150; i++) {
                data1 = ARFFHandler.loadARFF(new File("C:\\Repositories\\ISIDS-JavaML\\ARFF\\Clap\\" + i + ".arff"), 4);
                DatasetTools.merge(data, data1);
            }

            for (int i = 150; i < 194 ; i++ ) {
                dataForTest1 = ARFFHandler.loadARFF(new File("C:\\Repositories\\ISIDS-JavaML\\ARFF\\Clap\\" + i + ".arff"), 4);
                DatasetTools.merge(dataForTest, dataForTest1);
            }

        } catch (java.io.FileNotFoundException e) {
            System.err.println("error" + e);
        }

        Classifier knn = new KNearestNeighbors(5);
        knn.buildClassifier(data);

        int correct = 0, wrong = 0;

        for (Instance inst : dataForTest) {
            Object predictedClassValue = knn.classify(inst);
            Object realClassValue = inst.classValue();
            if (predictedClassValue.equals(realClassValue)) {
                correct++;
            } else {
                wrong++;
            }
        }

        System.out.print("C: " + correct + "   W: " + wrong);

//        Map<Object, PerformanceMeasure> pm = EvaluateDataset.testDataset(knn, dataForTest);
//        for(Object o:pm.keySet())
//            System.out.println(o+": "+pm.get(o).getAccuracy());

    }
}
