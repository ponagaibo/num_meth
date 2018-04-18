package lab3;

import lab1.TridiagonalAlgorithm;

import java.io.*;

class CubicSpline {
    private static double[] x_i;
    private static double[] f_i;

    private static double[] a_i;
    private static double[] b_i;
    private static double[] c_i;
    private static double[] d_i;

    private static double[] h;

    CubicSpline(double[] x, double[] f) {
        x_i = x;
        f_i = f;
    }

    private static class TriM extends TridiagonalAlgorithm {
        @Override
        public void getSolving(double[] root) {
            System.arraycopy(solving, 0, root, 2, solving.length);
        }
    }

    static void buildTriMatrix() throws FileNotFoundException {
        h = new double[x_i.length];
        for (int i = 1; i < x_i.length; i++) {
            h[i] = x_i[i] - x_i[i - 1];
        }
        double[][] m = new double[x_i.length - 2][x_i.length - 2];
        for (int i = 2; i < x_i.length; i++) {
            m[i - 2][i - 2] = 2 * (h[i - 1] + h[i]);
            if (i != x_i.length - 1) m[i - 2][i - 1] = h[i];
            if (i != 2) m[i- 2][i - 3] = h[i - 1];
        }

        double[] b = new double[x_i.length - 2];
        for (int i = 0; i < x_i.length - 2; i++) {
            b[i] = 3 * (((f_i[i + 2] - f_i[i + 1]) / h[i + 2]) - (f_i[i + 1] - f_i[i]) / h[i + 1]);
        }

        String filename = "tridiagonal_matrix_3_2.txt";
        String dir = "./src/main/java/lab3/";
        File file = new File(dir, filename);
        try (FileWriter fw = new FileWriter(file, false)) {
            fw.append("").append(String.valueOf(x_i.length - 2)).append("\n");
            for (int i = 0; i < x_i.length - 2; i++) {
                for (int j = i - 1; j <= i + 1; j++) {
                    if (j >= 0 && j < x_i.length - 2) {
                        fw.append("").append(String.valueOf(m[i][j])).append(" ");
                    }
                }
                fw.append("\n");
            }
            for (int i = 0; i < x_i.length - 2; i++) {
                fw.append("").append(String.valueOf(b[i])).append(" ");
            }
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        TriM alg_tridig = new TriM();
        alg_tridig.readData(dir.concat(filename));
        alg_tridig.algo();
        c_i = new double[x_i.length];
        alg_tridig.getSolving(c_i);
    }

    static void findIndices() {
        a_i = new double[x_i.length];
        System.arraycopy(f_i, 0, a_i, 1, x_i.length - 1);

        b_i = new double[x_i.length];
        d_i = new double[x_i.length];

        for (int i = 1; i < x_i.length - 1; i++) {
            b_i[i] = (f_i[i] - f_i[i - 1]) / h[i] - 1. / 3. * (c_i[i + 1] + 2 * c_i[i]) * h[i];
            d_i[i] = (c_i[i + 1] - c_i[i]) / (3 * h[i]);
        }
        b_i[x_i.length - 1] = (f_i[x_i.length - 1] - f_i[x_i.length - 2]) / h[x_i.length - 1]
                - 2. / 3. * c_i[x_i.length - 1] * h[x_i.length - 1];

        d_i[x_i.length - 1] = -c_i[x_i.length - 1] / (3 * h[x_i.length - 1]);


        System.out.println("a_i:");
        for (double anA_i : a_i) {
            System.out.print("" + anA_i + " ");
        }
        System.out.println("\nb_i:");
        for (double aB_i : b_i) {
            System.out.print("" + aB_i + " ");
        }
        System.out.println("\nc_i:");
        for (double aC_i : c_i) {
            System.out.print("" + aC_i + " ");
        }
        System.out.println("\nd_i:");
        for (double aD_i : d_i) {
            System.out.print("" + aD_i + " ");
        }
    }

    static double compute(double x) {
        int bound = 0;
        for (int i = 0; i < x_i.length; i++) {
            if (x_i[i] > x) {
                bound = i;
                break;
            }
        }
        double dif = x - x_i[bound - 1];
        return a_i[bound] + b_i[bound] * dif + c_i[bound] * dif * dif + d_i[bound] * dif * dif * dif;
    }
}
