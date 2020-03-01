package com.company;

import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class KNNUtil {
    private static Double[][] features;
    private static List classes = new ArrayList<String>();
    private static Map<Double, String> distancesToClasses = new HashMap<>();

    private double getEuclidianDistance(double[] existingPoint, double[] newPoint) {
        double sum = 0.0;

        for(int i=0; i < newPoint.length; i++) {
            sum += Math.pow(newPoint[i] - existingPoint[i], 2);
        }

        return round(Math.sqrt(sum));
    }

    public Double[] getListOfEuclidianDistances(double[] newPoint, Double[][] features) {
        Double[] listOfDistances = new Double[features[0].length];

        for(int i=0; i < features[0].length; i++) {
            double[] elements = new double[features.length];
            for(int j=0; j < features.length; j++) {
                elements[j] = features[j][i];
            }
            listOfDistances[i] = getEuclidianDistance(elements, newPoint);
            mapDistanceToClassification(listOfDistances[i], i);
        }

        return listOfDistances;
    }

    public List<Double> findKLowestValues(List<Double> distances, int k) {
        double[] lowestValues = new double[k];

        Collections.sort(distances);

        for(int i = k-1; i > -1; i--) {
            lowestValues[i] = distances.get(i);
        }

        Double[] doubleArray = ArrayUtils.toObject(lowestValues);
        return Arrays.asList(doubleArray);
    }

    public void initfeaturesFromDataFile(int numOfDataPoints) throws FileNotFoundException {
        File file = new File("data");

        Scanner scanNumOfLines = new Scanner(file);

        features = new Double[scanNumOfLines.nextLine().split(",").length - 1][numOfDataPoints];

        int lineIndex = 0;
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String[] params = scanner.nextLine().split(",");

            for(int i=0; i < params.length - 1; i++) {
                features[i][lineIndex] = (Double.parseDouble(params[i]));
            }

            classes.add(params[params.length - 1]);
            lineIndex++;
        }
    }

    private void mapDistanceToClassification(Double key, int index) {
        distancesToClasses.put(key, classes.get(index).toString());
    }

    public static String classify(List<Double> lowestValues) {
        List<String> selectedClasses = new ArrayList<>();

        lowestValues.forEach(key -> {
            selectedClasses.add(distancesToClasses.get(key));
        });

        Map<String, Long> occurences = selectedClasses.stream().collect(Collectors.groupingBy(w -> w, Collectors.counting()));

        long largest = 0;
        String mostFrequentClass = "";
        for(String classification : selectedClasses) {
            if (occurences.get(classification) > largest) {
                largest = occurences.get(classification);
                mostFrequentClass = classification;
            }
        }

        return mostFrequentClass;
    }

    public int getDefaultK() {
        return (Math.ceil(Math.sqrt(getFeatures().length)) % 2 == 0) ? (int) Math.ceil(Math.sqrt(getFeatures().length)) + 1 :
                (int) Math.ceil(Math.sqrt(getFeatures().length));
    }

    private double round(double value) {
        return Math.round(value * 10.0) / 10.0;
    }

    public static Double[][] getFeatures() {
        return features;
    }

}
