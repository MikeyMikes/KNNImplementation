package com.company;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        KNN knn = new KNN(699);

        double[] newPoint = new double[] { 1000025, 5, 1, 1, 1, 2, 1, 3, 1, 1 };

        System.out.println(knn.classify(newPoint, 3));
}
}
