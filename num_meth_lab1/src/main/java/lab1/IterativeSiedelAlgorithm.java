package lab1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class IterativeSiedelAlgorithm {
    static int dim;
    static double precision;

    static double[] free_terms;
    static double[] beta;
    static double[] solving;

    private static class Element {
        double value;
        int str;
        int cl;
        public Element (double v, int s, int c) {
            value = v;
            str = s;
            cl = c;
        }

    }

    static Element[] coefs;
    static Element[] alpha;

    public static Element[] append(Element[] coefs, double v, int s, int c) {
        int prev_size = coefs.length;
        Element[] res = new Element[prev_size + 1];
        for (int i = 0; i < prev_size; i++) {
            res[i] = coefs[i];
        }
        res[prev_size] = new Element(v, s, c);
        return res;
    }

    public static void readData(String inFile) throws FileNotFoundException {
        File inputFile = new File(inFile);
        Scanner sc = new Scanner(inputFile);
        if (!sc.hasNextInt()) return;
        dim = sc.nextInt();
        coefs = new Element[0];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (!sc.hasNextDouble()) return;
                double tmp = sc.nextDouble();
                if (tmp != 0.0) {
                    coefs = append(coefs, tmp, i, j);
                }
            }
        }

        /*System.out.println("Coefs:");
        for (int i = 0; i < size; i++) {
            System.out.println("v: " + coefs[i].value + "  s: " + coefs[i].str + "  c: " + coefs[i].cl);
        }*/
        free_terms = new double[dim];
        for (int i = 0; i < dim; i++) {
            if (!sc.hasNextDouble()) return;
            free_terms[i] = sc.nextDouble();
        }

        /*System.out.println("Free terms:");
        for (int i = 0; i < dim; i++) {
            System.out.println("" + free_terms[i] + " ");
        }*/

        if (!sc.hasNextDouble()) return;
        precision = sc.nextDouble();
        System.out.println("Matrix:");
        print_matrix(coefs);
//        System.out.println("\nNorm of matrix: " + matrix_norm(coefs));
//        System.out.println("\nNorm of vector: " + vector_norm(free_terms));
        equivalent();
        solve_iterative();
    }

    public static void print_matrix(Element[] coefs) {
        int cnt = 0;
        //System.out.println("In print");
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (cnt < coefs.length && coefs[cnt].str == i && coefs[cnt].cl == j) {
                    System.out.print("" + coefs[cnt++].value + " ");
                } else {
                    System.out.print("0.0 ");
                }
            }
            System.out.println();
        }
    }

    public static double matrix_norm(Element[] matrix) {
        double[] total_in_cl = new double[dim];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[j].cl == i) {
                    total_in_cl[i] += Math.abs(matrix[j].value);
                }
            }
        }
        double max = 0;
        for (int i = 0; i < dim; i++) {
            if (total_in_cl[i] > max) {
                max = total_in_cl[i];
            }
        }
        return max;
    }

    public static double vector_norm(double[] vect) {
        double max = 0;
        for (int i = 0; i < dim; i++) {
            if (Math.abs(vect[i]) > max) {
                max = Math.abs(vect[i]);
            }
        }
        return max;
    }

    public static void equivalent() {
        beta = new double[dim];
        alpha = new Element[0];
        int cnt = 0;
        double tmp;
        double[] diag = new double[dim];
        for (int i = 0; i < coefs.length; i++) {
            if (coefs[i].str == coefs[i].cl) {
                diag[cnt++] = coefs[i].value;
            }
        }
/*        System.out.println("\nDiagonals:");
        for (int i = 0; i < dim; i++) {
            System.out.print("" + diag[i] + " ");
        }*/

        cnt = 0;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (i == j) {
                    beta[i] = free_terms[i] / diag[i];
                }
                if (cnt < coefs.length && coefs[cnt].str == i && coefs[cnt].cl == j) {
                    tmp = coefs[cnt++].value / diag[i];
                    if (i != j && tmp != 0.0) alpha = append(alpha, -tmp, i, j);
                }
            }
        }
        System.out.println("\nBeta:");
        for (int i = 0; i < dim; i++) {
            System.out.print("" + beta[i] + " ");
        }
        System.out.println("\n\nAlpha:");
        print_matrix(alpha);
    }

    public static double[] multiply(Element[] coefs, double[] vect) {
        double[] res = new double[dim];
        int cnt = 0;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (cnt < coefs.length && coefs[cnt].str == i && coefs[cnt].cl == j) {
                    res[i] += coefs[cnt++].value * vect[j];
                }
            }
        }
        return res;
    }

    public static void solve_iterative() {
        solving = new double[dim];
        System.arraycopy(beta, 0, solving,0, dim);
        double eps = (vector_norm(solving) * matrix_norm(alpha)) / (1 - matrix_norm(alpha));
        while (eps > precision) {
            double[] tmp = multiply(alpha, solving);
            for (int i = 0; i < dim; i++) {
                tmp[i] += beta[i];
            }
            double[] diff = new double[dim];
            for (int i = 0; i < dim; i++) {
                diff[i] = tmp[i] - solving[i];
            }
            /*System.out.println("diff:");
            for (int i = 0; i < dim; i++) {
                System.out.print("" + diff[i] + " ");
            }*/
            eps = (vector_norm(diff) * matrix_norm(alpha)) / (1 - matrix_norm(alpha));
            System.arraycopy(tmp, 0, solving,0, dim);
            //System.out.println("eps diff: " + eps);
        }

        System.out.println("\nSolving iterative:");
        for (int i = 0; i < dim; i++) {
            System.out.print("" + solving[i] + " ");
        }
    }
}
