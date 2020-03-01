package com.company;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        KNN knn = new KNN(9);

        double[] newPoint = new double[] { 57, 170, 200 };

        System.out.println(knn.classify(newPoint, 3));
}
}
