package lab3;

import java.util.function.Function;

class Lab3 {
    static void lab3_3_1(double[] pointsL, double[] pointsN, Function<Double, Double> function, double checkPoint) {
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
}
