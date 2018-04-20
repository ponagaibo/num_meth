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

/* // my L and N
        double[] myPointsL = new double[] {-0.4, -0.1, 0.2, 0.5};
        double[] myPointsN = new double[] {-0.4, 0., 0.2, 0.5};
        Function<Double, Double> myFunction = Math::asin;
        double myCheckPointI = 0.1;
        Lab3.lab3_1(myPointsL, myPointsN, myFunction, myCheckPointI);
*/

/* // for example of spline
        double[] ex_x = new double[] {0, 1, 2, 3, 4};
        double[] ex_f = new double[] {0, 1.8415, 2.9093, 3.1411, 3.2432};
        double exCheckPoint = 1.5;
*/

/* // my spline
        double[] xSpline = new double[] {-0.4, -0.1, 0.2, 0.5, 0.8};
        double[] fSpline = new double[] {-0.41152, -0.10017, 0.20136, 0.52360, 0.92730};
        double checkPointSpline = 0.1;
        Lab3.lab3_2(xSpline, fSpline, checkPointSpline);
*/

        /* // my ols       Lab3.lab3_3();*/


/*  // for example of differentiation
        double[] exX = new double[]{0.0, 0.1, 0.2, 0.3, 0.4};
        double[] exF = new double[]{1.0, 1.1052, 1.2214, 1.3499, 1.4918};
        double exCP = 0.2;
*/

/*
        double[] xDif = new double[]{-1.0, 0.0, 1.0, 2.0, 3.0};
        double[] fDif = new double[]{-0.7854, 0.0, 0.78540, 1.1071, 1.249};
        double checkPointDif = 1;
        Lab3.lab3_4(xDif, fDif, checkPointDif);
*/


    }
}
