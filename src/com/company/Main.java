package com.company;

import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.KNearestNeighbors;
import net.sf.javaml.classification.bayes.NaiveBayesClassifier;
import net.sf.javaml.classification.evaluation.EvaluateDataset;
import net.sf.javaml.classification.evaluation.PerformanceMeasure;
import net.sf.javaml.classification.tree.RandomForest;
import net.sf.javaml.classification.tree.RandomTree;
import net.sf.javaml.clustering.Clusterer;
import net.sf.javaml.clustering.KMeans;
import net.sf.javaml.clustering.evaluation.ClusterEvaluation;
import net.sf.javaml.clustering.evaluation.SumOfSquaredErrors;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.tools.DatasetTools;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        Reader reader = new Reader();
        Dataset data = new DefaultDataset(), testData = new DefaultDataset();
        Evaluator evaluator = new Evaluator();

        // Read the arff data sets to form training and testing data
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

        System.out.println("Started KNN");

        Classifier knn = new KNearestNeighbors(5);
        knn.buildClassifier(data);

        evaluator.evaluateClassifier1(knn, testData);
        evaluator.evaluateClassifier2(knn, testData);

        System.out.println("Finished KNN");

        // -----------------------------------------

        System.out.println("Started KMeans");

        Clusterer kmeans = new KMeans();
        Dataset[] kmeansClusters = kmeans.cluster(data);

        ClusterEvaluation sse= new SumOfSquaredErrors();
        double score=sse.score(kmeansClusters);
        System.out.println(score);

        System.out.println("Finished KMeans");

        // -----------------------------------------

        System.out.println("Started NaiveBayes");

        Classifier naiveBayes = new NaiveBayesClassifier(true, true, true);
        naiveBayes.buildClassifier(data);

        evaluator.evaluateClassifier1(naiveBayes, testData);
        evaluator.evaluateClassifier2(naiveBayes, testData);

        System.out.println("Finished NaiveBayes");

        // ------------------------------------------

        System.out.println("Started RandomForest");

        Classifier randomForest = new RandomForest(10);
        randomForest.buildClassifier(data);

        evaluator.evaluateClassifier1(randomForest, testData);
        evaluator.evaluateClassifier2(randomForest, testData);

        System.out.println("Finished RandomForest");

        // -------------------------------------------

        System.out.println("Started RandomTree");

        Random rd = new Random();
        Classifier randomTree = new RandomTree(10, rd);
        randomTree.buildClassifier(data);

        evaluator.evaluateClassifier1(randomTree, testData);
        evaluator.evaluateClassifier2(randomTree, testData);

        System.out.println("Finished RandomTree");
    }
}



