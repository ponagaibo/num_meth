package lab5;

import lab1.TridiagonalAlgorithm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Function;

import static lab5.Lab5.printArray;

public class CrankNicolsonMethod extends ParabolicMethods {
    /*private static double a;
    private static double left;
    private static double right;
    private static Function<Double, Double> phi0; // function
    private static Function<Double, Double> phiN; // function
    private static Function<Double, Double> psi; // (x) -> x + Math.sin(Math.PI * x);
    Lab5.Function2<Double, Double, Double> f;
    private static double h;
    private static double tau;
    Lab5.Function2<Double, Double, Double> analyticSolution;*/// = (x) -> x + Math.exp(-Math.PI * Math.PI * a * t) * Math.exp(Math.PI * x);
    // b, c, f
    private static double[] previousSolution;



    CrankNicolsonMethod(double a, double left, double right, Function<Double, Double> phi0, Function<Double, Double> phiN,
                        Function<Double, Double> psi, Lab5.Function2<Double, Double, Double> f, double h, double tau,
                        Lab5.Function2<Double, Double, Double> analyticSolution) {
        super(a, left, right, phi0, phiN, psi, f, h, tau, analyticSolution);
//        this.equation = new ParabolicMethods(a, left, right, phi0, phiN, psi, f, h, tau, analyticSolution);
    }

    double[] solving() throws FileNotFoundException {
        int points = (int)Math.ceil((right - left) / h);
        int times = (int)Math.ceil(1 / tau);
        previousSolution = new double[points + 1];
        double[] currentSolution = new double[points + 1];

        // t = 0
        double time = 0;
        double coef = tau * a / (2 * h * h);
        System.out.println("h = " + h + ", tau = " + tau + ", delta = " + (right - left) + ", N = " + points + ", K = " + times + ", coef = " + coef);
        // init curSol in t=0
/*        for (int i = 0; i < points + 1; i++) {
            currentSolution[i] = psi.apply(i * h);
            System.out.println("i: " + i + ", " + i * h);
        }
        System.out.println("\nt = 0:");
        printArray(currentSolution);*/

        double[] realSolution = new double[points + 1];
/*        for (int i = 0; i < points + 1; i++) {
            realSolution[i] = analyticSolution.apply(i * h, time * tau);
        }
        System.out.println("Real solution:");
        printArray(realSolution);*/

        String filename = "tridiagonal_matrix_5_3.txt";
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
            fw.append("").append(String.valueOf(-coef * psi.apply(left + 0 * h) - (1 - 2 * coef) * psi.apply(left + h)
            - coef * psi.apply(left + 2 * h) - coef * phi0.apply(time + 1))).append(" "); // d_1
            System.out.println("-s*psi(0): " + (-coef * psi.apply(left + 0 * h)) + ", (1-2s)*psi(0.2): " + ((1 - 2 * coef) * psi.apply(left + h))
            + ", s * psi(0.4): " + (coef * psi.apply(left + 2 * h)) + ", s*phi0(t): " + (coef * phi0.apply(time + 1)));
            for (int i = 2; i < points - 1; i++) { // N-2 statements
                fw.append("").append(String.valueOf(-coef * psi.apply(left + h * (i - 1)) - (1 - 2 * coef) * psi.apply(left + h * i)
                - coef * psi.apply(left + h * (i + 1)))).append(" ");

                System.out.println("-s*psi(" + ((i - 1) * h) + "): " + (-coef * psi.apply(left + h * (i - 1))) +
                        ", (1-2s)*psi(" + (h * i) + "): " + ((1 - 2 * coef) * psi.apply(left + h * i))
                        + ", s * psi(" + (h * (i + 1)) + "): " + (coef * psi.apply(left + h * (i + 1))));
            }
            fw.append("").append(String.valueOf(-coef * psi.apply(left + h * (points - 2))
                    - (1 - 2 * coef) * psi.apply(left + h * (points - 1))
                    - coef * psi.apply(left + h * points)
                    - coef * phiN.apply(time + 1))).append(" "); // d_N-1
            System.out.println("-s*psi(0,6): " + (-coef * psi.apply(left + h * (points - 2)))
                    + ", (1-2s)*psi(0.8): " + ((1 - 2 * coef) * psi.apply(left + h * (points - 1)))
                    + ", s * psi(1): " + (coef * psi.apply(left + h * points))
                    + ", s*phiN(t): " + (coef * phiN.apply(time + 1)));
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

        realSolution = new double[points + 1];
        for (int i = 0; i < points + 1; i++) {
            realSolution[i] = analyticSolution.apply(left + i * h, time * tau);
        }
        System.out.println("Real solution:");
        printArray(realSolution);


        // for t > 0
        if (1==1) return currentSolution;

        for (time = 1; time < times; time++) {
            System.arraycopy(currentSolution, 0, previousSolution, 0, currentSolution.length);
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
                fw.append("").append(String.valueOf(-coef * previousSolution[0] - (1 - 2 * coef) * previousSolution[1]
                        - coef * previousSolution[2] - coef * phi0.apply(time + 1))).append(" "); // d_1
//                System.out.println("d_1 = " + (coef * previousSolution[0] + 2 *(1 - coef) * previousSolution[1]
//                        + coef * previousSolution[2] + coef * phi0.apply(time + 1)));
                for (int i = 2; i < points - 1; i++) { // N-2 statements
                    fw.append("").append(String.valueOf(-coef * previousSolution[i - 1] - (1 - 2 * coef) * previousSolution[i]
                            - coef * previousSolution[i + 1])).append(" ");
                }
                fw.append("").append(String.valueOf(-coef * previousSolution[points - 2] - (1 - 2 * coef) * previousSolution[points - 1]
                        - coef * previousSolution[points] - coef * phiN.apply(time + 1))).append(" "); // d_N-1
                fw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

            tridig_alg = new TridiagonalAlgorithm();
            tridig_alg.readData(dir.concat(filename));
            tridig_alg.algo();
            tridig_alg.getSolving(currentSolution, 1);
            currentSolution[0] = phi0.apply(time);
            currentSolution[points] = phiN.apply(time);
            System.out.println("\nSolution in time = : " + time);
            Lab5.printArray(currentSolution);

            for (int i = 0; i < points; i++) {
                realSolution[i] = analyticSolution.apply(left + i * h, time * tau);
            }
            System.out.println("Real solution:");
            printArray(realSolution);
            time = times;
        }




        return currentSolution;
    }
}
