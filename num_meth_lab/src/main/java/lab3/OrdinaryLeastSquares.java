package lab3;

import lab1.LU_Algorithm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

class OrdinaryLeastSquares {
    private static double[] x;
    private static double[] y;
    private static int amount; // N + 1

    void readData(String inFile) throws FileNotFoundException {
        File inputFile = new File(inFile);
        Scanner sc = new Scanner(inputFile).useLocale(Locale.UK);
        if (!sc.hasNextInt()) return;
        amount = sc.nextInt();
        x = new double[amount];
        y = new double[amount];
        for (int i = 0; i < amount; i++) {
            if (!sc.hasNextDouble()) return;
            x[i] = sc.nextDouble();
        }
        for (int i = 0; i < amount; i++) {
            if (!sc.hasNextDouble()) return;
            y[i] = sc.nextDouble();
        }
    }

    private static double sum(int degree) {
        if (degree == 0) return amount;
        double res = 0;
        for (int i = 0; i < amount; i++) {
            res += Math.pow(x[i], degree);
        }
        return res;
    }

    static double approximator(int degree) throws FileNotFoundException {
        String filename = "system_3_3_".concat(String.valueOf(degree)).concat(".txt");
        String dir = "./src/main/java/lab3/";
        File file = new File(dir, filename);
        double[] b = new double[degree + 1];
        try (FileWriter fw = new FileWriter(file, false)) {
            fw.append("").append(String.valueOf(degree + 1)).append("\n");

            for (int k = 0; k <= degree; k++) {
                for (int i = 0; i <= degree; i++) {
                    double coef = sum(k + i);
                    fw.append("").append(String.valueOf(coef)).append(" ");
                }
                fw.append("\n");
            }

            for (int k = 0; k <= degree; k++) {
                double coef = 0;
                for (int j = 0; j < amount; j++) {
                    if (k == 0) {
                        coef += y[j];
                    } else {
                        coef += y[j] * Math.pow(x[j], k);
                    }
                }
                b[k] = coef;
                fw.append("").append(String.valueOf(coef)).append(" ");
            }
            fw.append("\n");
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        LU_Algorithm alg_lu = new LU_Algorithm();
        alg_lu.readData(dir.concat(filename));
        alg_lu.decompose();
        alg_lu.getB(b);
        double[] coefs = alg_lu.solve(b);
        /*
        System.out.println("Indices: ");
        for (int i = 0; i <= degree; i++) {
            System.out.println("" + coefs[i] + " ");
        }
        */
        double[] f = new double[amount];
        for (int i = 0; i < amount; i++) {
            f[i] = fun(coefs, degree, x[i]);
        }

        filename = "approximators_3_3_".concat(String.valueOf(degree)).concat(".txt");
        dir = "./src/main/java/lab3/";
        file = new File(dir, filename);

        try (FileWriter fw = new FileWriter(file, false)) {
            StringBuffer func = new StringBuffer();
            func.append("").append(String.valueOf(coefs[0]));
            for (int i = 1; i <= degree; i++) {
                func.append("+").append(String.valueOf(coefs[i])).append("*x^").append(String.valueOf(i));
                fw.append(func);
            }
            System.out.println("Approx" + degree + ":\n" + func);
            fw.append("\n");
            for (int i = 0; i < amount; i++) {
                fw.append("").append(String.valueOf(x[i])).append(" ").append(String.valueOf(f[i])).append("\n");
            }
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return error(f);
    }

    private static double fun(double[] coefs, double degree, double x) {
        double res = 0;
        for (int i = 0; i <= degree; i++) {
            res += coefs[i] * Math.pow(x, i);
        }
        return res;
    }

    private static double error(double[] f) {
        double res = 0;
        for (int i = 0; i < amount; i++) {
            double tmp = (y[i] - f[i]) * (y[i] - f[i]);
            res += tmp;
        }
/*
        System.out.println("f(x[i]): ");
        for (int i = 0; i < amount; i++) {
            System.out.println("" + x[i] + " : " + f[i]);
        }
*/
        return res;
    }
}
