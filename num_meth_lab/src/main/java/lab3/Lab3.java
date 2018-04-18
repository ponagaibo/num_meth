package lab3;

import java.io.FileNotFoundException;
import java.util.function.Function;

class Lab3 {
    static void lab3_1(double[] pointsL, double[] pointsN, Function<Double, Double> function, double checkPoint) {
        System.out.println("\n~~~ Lagrange interpolation ~~~");
        double lx = new LagrangeInterpolation(pointsL, function).interpolation(checkPoint);
        System.out.println("L(x_points): " + lx);
        System.out.println("f(x_points): " + function.apply(checkPoint));
        System.out.println("Accuracy: " + Math.abs(lx - function.apply(checkPoint)));
        System.out.println("~~~~~~~~~~~~~~~");

        System.out.println("\n~~~ Newton's interpolation ~~~");
        double nx = new NewtonsInterpolation(pointsL, function).interpolation(checkPoint);
        System.out.println("P(x_points): " + nx);
        System.out.println("f(x_points): " + function.apply(checkPoint));
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
        System.out.println("\n~~~ Ordinary Least Squares ~~~");
        OrdinaryLeastSquares ols = new OrdinaryLeastSquares();
        ols.readData("./src/main/java/lab3/l3.3.txt");
        System.out.println("Error1: " + OrdinaryLeastSquares.approximator(1));
        System.out.println("Error2: " + OrdinaryLeastSquares.approximator(2));
        System.out.println("~~~~~~~~~~~~~~~");
    }

    static void lab3_4(double[] newX, double[] newY, double cp) {
        System.out.println("\n~~~ Numerical Differentiation ~~~");
        new NumericalDifferentiation(newX, newY);
        System.out.println("df_right: " + NumericalDifferentiation.right_dfunc(cp));
        System.out.println("df_left: " + NumericalDifferentiation.left_dfunc(cp));
        System.out.println("~~~~~~~~~~~~~~~");
    }
}
