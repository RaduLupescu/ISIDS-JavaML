package com.company;

import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.evaluation.EvaluateDataset;
import net.sf.javaml.classification.evaluation.PerformanceMeasure;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;

import java.util.Map;

public class Evaluator {
    public Evaluator () {}

    public void evaluateClassifier1 (Classifier knn, Dataset data) {
        Map<Object, PerformanceMeasure> pm = EvaluateDataset.testDataset(knn, data);
        for(Object o:pm.keySet()) {
            System.out.println(o + ": " + pm.get(o).getAccuracy());
        }
    }

    public void evaluateClassifier2 (Classifier knn, Dataset data) {
        int correct = 0, wrong = 0;

        for (Instance inst : data) {
            Object predictedClassValue = knn.classify(inst);
            Object realClassValue = inst.classValue();
            if (predictedClassValue.equals(realClassValue)) {
                correct++;
            } else {
                wrong++;
            }
        }

        System.out.println("C: " + correct + "   W: " + wrong);
    }
}
