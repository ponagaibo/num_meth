package lab4;

import lab1.TridiagonalAlgorithm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Function;

public class FiniteDiffirenceMethod {
    double h;
    double a;
    double b;
    double alpha;
    double beta;
    double y_a;
    double y_b;

    Function<Double, Double> p_x;
    Function<Double, Double> q_x;
    Function<Double, Double> f_x;
    Function<Double, Double> real;

    FiniteDiffirenceMethod(double h, double a, double b, double alpha, double beta, double y_a, double y_b,
                           Function<Double, Double> p, Function<Double, Double> q, Function<Double, Double> f,
                           Function<Double, Double> r) {
        this.h = h;
        this.a = a;
        this.b = b;
        this.alpha = alpha;
        this.beta = beta;
        this.y_a = y_a;
        this.y_b = y_b;

        p_x = p;
        q_x = q;
        f_x = f;
        real = r;
    }

    void buildtriMatrix() throws FileNotFoundException {
        String filename = "tridiagonal_matrix_4_2.txt";
        String dir = "./src/main/java/lab4/";
        File file = new File(dir, filename);
        try (FileWriter fw = new FileWriter(file, false)) {
            int times = (int)Math.ceil((b - a) / h) + 1;
            System.out.println("times: " + times);
            fw.append("").append(String.valueOf(times)).append("\n");
            double cur_b = -2 / (h * (2 - p_x.apply(a))) + q_x.apply(a) * h / (2 - p_x.apply(a) * h) + alpha;
            double cur_c = 2 / (h * (2 - p_x.apply(a) * h));
            double step = a + h;
            fw.append("").append(String.valueOf(cur_b)).append(" ");
            fw.append("").append(String.valueOf(cur_c)).append("\n");
            System.out.println("step in a: " + a);
            for (int i = 0; i < times - 2; i++) {
                System.out.println("step in write: " + step);
                double cur_a = 1 - p_x.apply(step) / (2 * h);
                cur_b = - 2 + q_x.apply(step);
                cur_c = 1 + p_x.apply(step) / (2 * h);
                fw.append("").append(String.valueOf(cur_a)).append(" ");
                fw.append("").append(String.valueOf(cur_b)).append(" ");
                fw.append("").append(String.valueOf(cur_c)).append("\n");
                step = a + h * (i + 2);
            }
            System.out.println("step in b: " + step);
            double cur_a = - 2 / (h * (2 + p_x.apply(b) * h));
            cur_b = 2 / (h * (2 + p_x.apply(b) * h)) - q_x.apply(b) * h / (2 + p_x.apply(b) * h) + beta;
            fw.append("").append(String.valueOf(cur_a)).append(" ");
            fw.append("").append(String.valueOf(cur_b)).append("\n");

            fw.append("").append(String.valueOf(y_a)).append(" ");
            for (int i = 0; i < times - 2; i++) {
                fw.append("").append(String.valueOf(0)).append(" ");
            }
            fw.append("").append(String.valueOf(y_b)).append(" ");
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }


        TridiagonalAlgorithm alg_tridig = new TridiagonalAlgorithm();
        alg_tridig.readData(dir.concat(filename));
        alg_tridig.algo();
        double[] roots = new double[alg_tridig.getDim()];
        alg_tridig.getSolving(roots);
        System.out.println("roots: ");
        for (double root : roots) {
            System.out.print("" + root + " ");
        }
        System.out.println();
        double step = a;
        System.out.println("real: ");
        while (((int)(step * 100)) / 100. <= b) {
            double r = real.apply(step);
            System.out.println("" + r);
            System.out.println("step in root: " + step);
            step += h;
        }
    }
}
