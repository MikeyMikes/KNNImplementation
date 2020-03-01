package com.company;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

public class KNN {
    private KNNUtil knnUtil = new KNNUtil();
    private int numOfDataPoints;

    public KNN(int numOfDataPoints) {
        this.numOfDataPoints = numOfDataPoints;
    }

    public String classify(double[] newPoint) throws FileNotFoundException {
        knnUtil.initfeaturesFromDataFile(numOfDataPoints);
        List<Double> euclidianDistances = Arrays.asList(knnUtil.getListOfEuclidianDistances(newPoint, knnUtil.getFeatures()));
        List<Double> lowestValues = knnUtil.findKLowestValues(euclidianDistances, knnUtil.getDefaultK());
        return knnUtil.classify(lowestValues);
    }

    public String classify(double[] newPoint, int k) throws FileNotFoundException {
        knnUtil.initfeaturesFromDataFile(numOfDataPoints);
        List<Double> euclidianDistances = Arrays.asList(knnUtil.getListOfEuclidianDistances(newPoint, knnUtil.getFeatures()));
        List<Double> lowestValues = knnUtil.findKLowestValues(euclidianDistances, k);
        return knnUtil.classify(lowestValues);
    }

}
