package lab3;

import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
/*
        double[] examplePoints = new double[] {0.1, 0.5, 0.9, 1.3};
        Function<Double, Double> exampleFunction = Math::log;
        double exampleCheckPoint = 0.8;
*/

        double[] myPointsL = new double[] {-0.4, -0.1, 0.2, 0.5};
        double[] myPointsN = new double[] {-0.4, 0., 0.2, 0.5};
        Function<Double, Double> myFunction = Math::asin;
        double myCheckPoint = 0.1;
        Lab3.lab3_3_1(myPointsL, myPointsN, myFunction, myCheckPoint);

    }
}
