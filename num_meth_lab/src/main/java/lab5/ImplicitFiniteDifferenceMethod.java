package lab5;

import lab1.TridiagonalAlgorithm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Function;

import static lab5.Lab5.printArray;

public class ImplicitFiniteDifferenceMethod extends ParabolicMethods{
    /*private static double a;
    private static double left;
    private static double right;
    private static Function<Double, Double> phi0; // function
    private static Function<Double, Double> phiN; // function
    //    private static double psi; // function
    private static Function<Double, Double> psi; // (x) -> x + Math.sin(Math.PI * x);
    private static double h;
    private static double tau;
    Lab5.Function2<Double, Double, Double> analyticSolution;*/// = (x) -> x + Math.exp(-Math.PI * Math.PI * a * t) * Math.exp(Math.PI * x);
    // b, c, f
    private static double[] previousSolution;

    ImplicitFiniteDifferenceMethod(double a, double left, double right, Function<Double, Double> phi0,
                                   Function<Double, Double> phiN, Function<Double, Double> psi,
                                   Lab5.Function2<Double, Double, Double> f, double h, double tau,
                                   Lab5.Function2<Double, Double, Double> analyticSolution) {
        super(a, left, right, phi0, phiN, psi, f, h, tau, analyticSolution);
    }

    double[] solving() throws FileNotFoundException {
        int points = (int)Math.ceil((right - left) / h);
        int times = (int)Math.ceil(1 / tau);
        System.out.println("h = " + h + ", tau = " + tau + ", delta = " + (right - left) + ", N = " + points + ", K = " + times);
        previousSolution = new double[points + 1];
        double[] currentSolution = new double[points + 1];

        // t = 0
        double time = 0;
        double coef = tau * a / (h * h);

        String filename = "tridiagonal_matrix_5_2.txt";
        String dir = "./src/main/java/lab5/";
        File file = new File(dir, filename);

        try (FileWriter fw = new FileWriter(file, false)) {
            fw.append("").append(String.valueOf(points - 1)).append("\n"); // without first and last points,
            // they are included into those statements
            // matrix itself
            fw.append("").append(String.valueOf(-(1 + 2 * coef))).append(" ");
            fw.append("").append(String.valueOf(coef)).append("\n");

            for (int i = 2; i < points - 1; i++) { // from 2 to N-2
                fw.append("").append(String.valueOf(coef)).append(" ");
                fw.append("").append(String.valueOf(-(1 + 2 * coef))).append(" ");
                fw.append("").append(String.valueOf(coef)).append("\n");
            }
            fw.append("").append(String.valueOf(coef)).append(" ");
            fw.append("").append(String.valueOf(-(1 + 2 * coef))).append("\n");

            // d_i
            fw.append("").append(String.valueOf(-(psi.apply(left + h) + coef * phi0.apply(time + 1)))).append(" "); // d_1
            for (int i = 2; i < points - 1; i++) { // N-2 statements
                fw.append("").append(String.valueOf(-psi.apply(left + h * i))).append(" ");
            }
            fw.append("").append(String.valueOf(-(psi.apply(left + h * (points - 1)) + coef * phiN.apply(time + 1)))).append(" "); // d_N-1
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        TridiagonalAlgorithm tridig_alg = new TridiagonalAlgorithm();
        tridig_alg.readData(dir.concat(filename));
        tridig_alg.algo();
        tridig_alg.getSolving(currentSolution, 1);
        currentSolution[0] = phi0.apply(time);
        currentSolution[points] = phiN.apply(time);
        System.out.println("Solution in time = 0: ");
        Lab5.printArray(currentSolution);

        double[] realSolution = new double[points + 1];
        for (int i = 0; i < points + 1; i++) {
            realSolution[i] = analyticSolution.apply(left + i * h, time * tau);
        }
        System.out.println("Real solution:");
        printArray(realSolution);

        for (time = 1; time < times; time++) {
            try (FileWriter fw = new FileWriter(file, false)) {
                fw.append("").append(String.valueOf(points - 1)).append("\n");
                fw.append("").append(String.valueOf(-(1 + 2 * coef))).append(" ");
                fw.append("").append(String.valueOf(coef)).append("\n");
                for (int i = 2; i < points - 1; i++) {
                    fw.append("").append(String.valueOf(coef)).append(" ");
                    fw.append("").append(String.valueOf(-(1 + 2 * coef))).append(" ");
                    fw.append("").append(String.valueOf(coef)).append("\n");
                }
                fw.append("").append(String.valueOf(coef)).append(" ");
                fw.append("").append(String.valueOf(-(1 + 2 * coef))).append("\n");

                // d_j
                fw.append("").append(String.valueOf(-(currentSolution[1] + coef * phi0.apply(time + 1)))).append(" "); // d_1 cs[0] is u_1
                for (int i = 2; i < points - 1; i++) {
                    fw.append("").append(String.valueOf(-currentSolution[i])).append(" ");
                }
                fw.append("").append(String.valueOf(-(currentSolution[points - 1] + coef * phiN.apply(time + 1)))).append("\n"); // d_N-1
                fw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.arraycopy(currentSolution, 0, previousSolution, 0, currentSolution.length);

            tridig_alg.readData(dir.concat(filename));
            tridig_alg.algo();
            tridig_alg.getSolving(currentSolution, 1);
            currentSolution[0] = phi0.apply(time);
            currentSolution[points] = phiN.apply(time);
            System.out.println("Solution in time = : " + time);
            Lab5.printArray(currentSolution);

            for (int i = 0; i < points; i++) {
                realSolution[i] = analyticSolution.apply(left + i * h, time * tau);
            }
            System.out.println("Real solution:");
            printArray(realSolution);
        }

        return currentSolution;
    }
}
