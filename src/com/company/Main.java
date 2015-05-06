package com.company;

import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.KNearestNeighbors;
import net.sf.javaml.classification.bayes.NaiveBayesClassifier;
import net.sf.javaml.classification.tree.RandomForest;
import net.sf.javaml.classification.tree.RandomTree;
import net.sf.javaml.clustering.Clusterer;
import net.sf.javaml.clustering.KMeans;
import net.sf.javaml.clustering.evaluation.ClusterEvaluation;
import net.sf.javaml.clustering.evaluation.SumOfSquaredErrors;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.tools.DatasetTools;

import java.util.Random;

public class Main {

    public static void main(String[] args) {

        Reader reader = new Reader();
        Dataset trainingData = new DefaultDataset(), testData = new DefaultDataset();
        Evaluator evaluator = new Evaluator();

        // Read the arff data sets to form training and testing data
        Dataset claps = reader.readARFF("Clap", 0, 130, 0),
                hats = reader.readARFF("Hat", 0, 80, 1),
                kicks = reader.readARFF("Kick", 0, 120, 2),
                snares = reader.readARFF("Snare", 0, 110, 3);

        Dataset testClaps = reader.readARFF("Clap", 130, 140, 0),
                testHats = reader.readARFF("Hat", 80, 90, 1),
                testKicks = reader.readARFF("Kick", 120, 130, 2),
                testSnares = reader.readARFF("Snare", 110, 120, 3);

        // Merge all the datasets into training and testing data
        DatasetTools.merge(trainingData, claps, hats, kicks, snares);
        DatasetTools.merge(testData, testClaps, testHats, testKicks, testSnares);

        // -----------------------------------------

        System.out.println("Started KNN");
        // Create new KNN instance
        Classifier knn = new KNearestNeighbors(5);
        // Train KNN with the training data
        knn.buildClassifier(trainingData);
        // Evaluate KNN
        evaluator.evaluateClassifier1(knn, testData);
        evaluator.evaluateClassifier2(knn, testData);
        System.out.println("Finished KNN");

        // -----------------------------------------

        System.out.println("Started KMeans");
        // Create new KMeans clusterer instance
        Clusterer kmeans = new KMeans();
        // Train the clusterer with the training data
        Dataset[] kmeansClusters = kmeans.cluster(trainingData);
        // Evaluate KMeans
        ClusterEvaluation sse = new SumOfSquaredErrors();
        double score = sse.score(kmeansClusters);
        System.out.println(score);
        System.out.println("Finished KMeans");

        // -----------------------------------------

        System.out.println("Started NaiveBayes");
        // Create new NaiveBayes instance
        Classifier naiveBayes = new NaiveBayesClassifier(true, true, true);
        // Train the classifier with the training data
        naiveBayes.buildClassifier(trainingData);
        // Evaluate NaiveBayes
        evaluator.evaluateClassifier1(naiveBayes, testData);
        evaluator.evaluateClassifier2(naiveBayes, testData);
        System.out.println("Finished NaiveBayes");

        // ------------------------------------------

        System.out.println("Started RandomForest");
        // Create new Random Forest instance
        Classifier randomForest = new RandomForest(10);
        // Train the classifier with the training data
        randomForest.buildClassifier(trainingData);
        // Evaluate Random Forest
        evaluator.evaluateClassifier1(randomForest, testData);
        evaluator.evaluateClassifier2(randomForest, testData);
        System.out.println("Finished RandomForest");

        // -------------------------------------------

        System.out.println("Started RandomTree");
        // Create new Random Instance, used by the Random Tree classifier
        Random rd = new Random();
        // Create new Random Tree classifier
        Classifier randomTree = new RandomTree(10, rd);
        // Train the classifier with the training data
        randomTree.buildClassifier(trainingData);
        // Evaluate Random Tree
        evaluator.evaluateClassifier1(randomTree, testData);
        evaluator.evaluateClassifier2(randomTree, testData);
        System.out.println("Finished RandomTree");
    }
}



