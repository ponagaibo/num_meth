package lab3;

import java.io.FileNotFoundException;
import java.util.function.Function;

class Lab3 {
    static void lab3_1(double[] pointsL, double[] pointsN, Function<Double, Double> function, double checkPoint) {
        System.out.println("\n~~~ Lagrange interpolation ~~~");
        double lx = new LagrangeInterpolation(pointsL, function).interpolation(checkPoint);
        System.out.println("L(x): " + lx);
        System.out.println("f(x): " + function.apply(checkPoint));
        System.out.println("Accuracy: " + Math.abs(lx - function.apply(checkPoint)));
        System.out.println("~~~~~~~~~~~~~~~");

        System.out.println("\n~~~ Newton's interpolation ~~~");
        double nx = new NewtonsInterpolation(pointsN, function).interpolation(checkPoint);
        System.out.println("P(x): " + nx);
        System.out.println("f(x): " + function.apply(checkPoint));
        System.out.println("Accuracy: " + Math.abs(nx - function.apply(checkPoint)));
        System.out.println("~~~~~~~~~~~~~~~");
    }

    static void lab3_2(double[] my_x, double[] my_f, double myCheckPoint) throws FileNotFoundException {
        System.out.println("\n~~~ Cubic Spline ~~~");
        new CubicSpline(my_x, my_f);
        CubicSpline.buildTriMatrix();
        CubicSpline.findIndices();
        System.out.println("\nValue in " + myCheckPoint + ": " + CubicSpline.compute(myCheckPoint));
        System.out.println("~~~~~~~~~~~~~~~");
    }

    static void lab3_3() throws FileNotFoundException {
        OrdinaryLeastSquares ols = new OrdinaryLeastSquares();
        ols.readData("./src/main/java/lab3/l3.3.txt");
    }
}
