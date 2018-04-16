package lab3;

import java.io.FileNotFoundException;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
/* // for example of interpolation
        double[] examplePoints = new double[] {0.1, 0.5, 0.9, 1.3};
        Function<Double, Double> exampleFunction = Math::log;
        double exampleCheckPoint = 0.8;
*/

/*
        double[] myPointsL = new double[] {-0.4, -0.1, 0.2, 0.5};
        double[] myPointsN = new double[] {-0.4, 0., 0.2, 0.5};
        Function<Double, Double> myFunction = Math::asin;
        double myCheckPoint = 0.1;
        Lab3.lab3_1(myPointsL, myPointsN, myFunction, myCheckPoint);
*/

/* // for example of spline
        double[] ex_x = new double[] {0, 1, 2, 3, 4};
        double[] ex_f = new double[] {0, 1.8415, 2.9093, 3.1411, 3.2432};
        double exCheckPoint = 1.5;
*/

/*
        double[] my_x = new double[] {-0.4, -0.1, 0.2, 0.5, 0.8};
        double[] my_f = new double[] {-0.41152, -0.10017, 0.20136, 0.52360, 0.92730};
        double myCheckPoint = 0.1;

        Lab3.lab3_2(my_x, my_f, myCheckPoint);
*/
        Lab3.lab3_3();
    }
}
