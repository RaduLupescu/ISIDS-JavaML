package com.company;

import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.KNearestNeighbors;
import net.sf.javaml.classification.evaluation.EvaluateDataset;
import net.sf.javaml.classification.evaluation.PerformanceMeasure;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.tools.DatasetTools;

import java.io.FileNotFoundException;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        Reader reader = new Reader();
        Dataset data = new DefaultDataset(), testData = new DefaultDataset();

        Dataset claps = reader.readARFF("Clap", 0, 130, 0),
                hats = reader.readARFF("Hat", 0, 80, 1),
                kicks = reader.readARFF("Kick", 0, 120, 2),
                snares = reader.readARFF("Snare", 0, 110, 3);

        Dataset testClaps = reader.readARFF("Clap", 130, 196, 0),
                testHats = reader.readARFF("Hat", 80, 125, 1),
                testKicks = reader.readARFF("Kick", 120, 181, 2),
                testSnares = reader.readARFF("Snare", 110, 156, 3);

        DatasetTools.merge(data, claps, hats, kicks, snares);
        DatasetTools.merge(testData, testClaps, testHats, testKicks, testSnares);

        Classifier knn = new KNearestNeighbors(5);
        knn.buildClassifier(data);

        int correct = 0, wrong = 0;

        for (Instance inst : testData) {
            Object predictedClassValue = knn.classify(inst);
            Object realClassValue = inst.classValue();
            if (predictedClassValue.equals(realClassValue)) {
                correct++;
            } else {
                wrong++;
            }
        }

        System.out.println("C: " + correct + "   W: " + wrong);


        Map<Object, PerformanceMeasure> pm = EvaluateDataset.testDataset(knn, data);
        for(Object o:pm.keySet()) {
            System.out.println(o + ": " + pm.get(o).getAccuracy());
        }
    }
}



