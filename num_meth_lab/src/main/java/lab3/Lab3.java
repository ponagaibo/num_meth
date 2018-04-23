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
        System.out.println("Right f'(" + cp + "): " + NumericalDifferentiation.right_dfunc1(cp));
        System.out.println("Left f'(" + cp + "): " + NumericalDifferentiation.left_dfunc1(cp));
        System.out.println("2 degree f'(" + cp + "): " + NumericalDifferentiation.dfun2(cp));
        System.out.println("f''(" + cp + "): " + NumericalDifferentiation.ddfun2(cp));
        System.out.println("~~~~~~~~~~~~~~~");
    }

    static void lab3_5(Function<Double, Double> f, double b, double e, double h1, double h2) {
        System.out.println("\n~~~ Numerical Integration ~~~");
        new NumericalIntegration(f, b, e);
        double r1 = NumericalIntegration.rectangle_method(h1);
        double r2 = NumericalIntegration.rectangle_method(h2);
        double t1 = NumericalIntegration.trapeze_method(h1);
        double t2 = NumericalIntegration.trapeze_method(h2);
        double s1 = NumericalIntegration.simpsons_rule(h1);
        double s2 = NumericalIntegration.simpsons_rule(h2);
        System.out.println("Rectangle method with h = " + h1 + ": " + r1);
        System.out.println("Trapeze method with h = " + h1 + ": " + t1);
        System.out.println("Simpson's rule with h = " + h1 + ": " + s1);
        System.out.println("Rectangle method with h = " + h2 + ": " + r2);
        System.out.println("Trapeze method with h = " + h2 + ": " + t2);
        System.out.println("Simpson's rule with h = " + h2 + ": " + s2);
        double real_answer = 0.7854;
        System.out.println("Accurate answer: " + real_answer);
        double improved_r = NumericalIntegration.runge_method(h1, h2, r1, r2, 2);
        double improved_t = NumericalIntegration.runge_method(h1, h2, t1, t2, 2);
        double improved_s = NumericalIntegration.runge_method(h1, h2, s1, s2, 2);
        double error_r = Math.abs(real_answer - improved_r);
        double error_t = Math.abs(real_answer - improved_t);
        double error_s = Math.abs(real_answer - improved_s);
        System.out.println("Improved rectangle method: " + improved_r);
        System.out.println("Error of rectangle method: " + error_r);
        System.out.println("Improved trapeze method: " + improved_t);
        System.out.println("Error of trapeze method: " + error_t);
        System.out.println("Improved Simpson's rule: " + improved_s);
        System.out.println("Error of Simpsom's rule: " + error_s);
        System.out.println("~~~~~~~~~~~~~~~");
    }
}
