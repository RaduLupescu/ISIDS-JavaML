package com.company;

import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.evaluation.EvaluateDataset;
import net.sf.javaml.classification.evaluation.PerformanceMeasure;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;

import java.util.Map;

public class Evaluator {
    public Evaluator () {}

    /**
     * Method which evaluates classifiers using the PerformanceMeasure class
     * @param classifier The classifier to evaluate
     * @param testingData The data to test against the classifier
     */
    public void evaluateClassifier1 (Classifier classifier, Dataset testingData) {
        Map<Object, PerformanceMeasure> pm = EvaluateDataset.testDataset(classifier, testingData);
        for(Object o:pm.keySet()) {
            System.out.println(o + ": " + pm.get(o).getTotal());
        }
    }

    /**
     * Method which evaluates classifiers by comparing the predicted label with the actual one
     * @param classifier The classifier to evaluate
     * @param testingData The data to test against the classifier
     */
    public void evaluateClassifier2 (Classifier classifier, Dataset testingData) {
        int correct = 0, wrong = 0;
        for (Instance inst : testingData) {
            Object predictedClassValue = classifier.classify(inst);
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
