package lab4;

import lab1.TridiagonalAlgorithm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class FiniteDifferenceMethod {
    double h;
    double a;
    double b;
    double y_a;
    double y_b;

    Function<Double, Double> p_x;
    Function<Double, Double> q_x;
    Function<Double, Double> f_x;
    Function<Double, Double> real;

    FiniteDifferenceMethod(double h, double a, double b, double y_a, double y_b,
                           Function<Double, Double> p, Function<Double, Double> q, Function<Double, Double> f,
                           Function<Double, Double> r) {
        this.h = h;
        this.a = a;
        this.b = b;
        this.y_a = y_a;
        this.y_b = y_b;

        p_x = p;
        q_x = q;
        f_x = f;
        real = r;
    }

    void writeTriMatrix(String filename, String dir) {
        File file = new File(dir, filename);
        try (FileWriter fw = new FileWriter(file, false)) {
            int times = (int)Math.ceil((b - a) / h) + 1;
            fw.append("").append(String.valueOf(times)).append("\n");
            double step = a + h;

            double a0 = -1./h;
            double b0 = 1./h;
            List<Double> d = new ArrayList<>();
            d.add(y_a);
            fw.append("").append(String.valueOf(a0)).append(" ");
            fw.append("").append(String.valueOf(b0)).append(" ");
            fw.append("\n");

            for (int i = 1; i < times - 1; i++) {
                double shift = h / 8.;
                boolean changed = false;
                if (((int)(step * 100)) / 100. == -0.5) {
                    step += shift;
                    changed = true;
                }
                double cur_a = 1. - p_x.apply(step) * h / 2.;
                double cur_b = -2. + q_x.apply(step) * h * h;
                double cur_c = 1. + p_x.apply(step) * h / 2.;
                d.add(h * h * f_x.apply(step));
                fw.append("").append(String.valueOf(cur_a)).append(" ");
                fw.append("").append(String.valueOf(cur_b)).append(" ");
                fw.append("").append(String.valueOf(cur_c)).append(" ");
                fw.append("\n");
                if (changed) {
                    step -= shift;
                    changed = false;
                }
                step = a + h * (i + 1);
            }

            double an = 0.;
            double bn = 1.;
            d.add(y_b);

            fw.append("").append(String.valueOf(an)).append(" ");
            fw.append("").append(String.valueOf(bn)).append(" ");
            fw.append("\n");

            for (int i = 0; i < d.size(); i++) {
                fw.append("").append(String.valueOf(d.get(i))).append(" ");
            }

            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void finiteDifferenceMethod() throws FileNotFoundException {
        String filename = "tridiagonal_matrix_4_2.txt";
        String dir = "./src/main/java/lab4/";
        writeTriMatrix(filename, dir);

        TridiagonalAlgorithm alg_tridig = new TridiagonalAlgorithm();
        alg_tridig.readDataFromFile(dir.concat(filename));
        alg_tridig.algo();
        double[] roots = new double[alg_tridig.getDim()];
        alg_tridig.getSolving(roots);

        double step = a;
        int cnt = 0;
        while (((int)(step * 100)) / 100. <= b) {
            double r = real.apply(step);
            double my = roots[cnt];
            System.out.println("x: " + step + ", my y: " + my + ", real: " + r + ", eps: " + Math.abs(my - r));
            step += h;
            cnt++;
        }
    }
}
