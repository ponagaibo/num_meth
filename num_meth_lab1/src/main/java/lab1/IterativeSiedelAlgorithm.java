package lab1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class IterativeSiedelAlgorithm {
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

    static int dim;

    static double precision;
    static double[] free_terms;
    static double[] beta_iter;

    static double[] solving_iter;
    static double[] solving_siedel;

    static Element[] coefs;
    static Element[] alpha_iter;

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
        free_terms = new double[dim];
        for (int i = 0; i < dim; i++) {
            if (!sc.hasNextDouble()) return;
            free_terms[i] = sc.nextDouble();
        }
        if (!sc.hasNextDouble()) return;
        precision = sc.nextDouble();
    }

    public static void print_matrix(Element[] coefs) {
        int cnt = 0;
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
        System.out.println();
    }

    public static double matrix_norm(Element[] matrix) {
        double[] total_in_str = new double[dim];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[j].str == i) {
                    total_in_str[i] += Math.abs(matrix[j].value);
                }
            }
        }
        double max = 0;
        for (int i = 0; i < dim; i++) {
            if (total_in_str[i] > max) {
                max = total_in_str[i];
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

    public static void to_equivalent() {
        beta_iter = new double[dim];
        alpha_iter = new Element[0];
        int cnt = 0;
        double tmp;
        double[] diag = new double[dim];
        for (int i = 0; i < coefs.length; i++) {
            if (coefs[i].str == coefs[i].cl) {
                diag[cnt++] = coefs[i].value;
            }
        }
        cnt = 0;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (i == j) {
                    beta_iter[i] = free_terms[i] / diag[i];
                }
                if (cnt < coefs.length && coefs[cnt].str == i && coefs[cnt].cl == j) {
                    tmp = coefs[cnt++].value / diag[i];
                    if (i != j && tmp != 0.0) alpha_iter = append(alpha_iter, -tmp, i, j);
                }
            }
        }
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

    public static int solve_iterative() {
        solving_iter = new double[dim];
        System.arraycopy(beta_iter, 0, solving_iter,0, dim);
        double eps = precision + 1;
        int cnt_iter = 0;
        while (eps > precision) {
            double[] tmp = multiply(alpha_iter, solving_iter);
            for (int i = 0; i < dim; i++) {
                tmp[i] += beta_iter[i];
            }
            double[] diff = new double[dim];
            for (int i = 0; i < dim; i++) {
                diff[i] = tmp[i] - solving_iter[i];
            }
            eps = (vector_norm(diff) * matrix_norm(alpha_iter)) / (1 - matrix_norm(alpha_iter));
            System.arraycopy(tmp, 0, solving_iter,0, dim);
            cnt_iter++;
        }
        return cnt_iter;
    }

    public static int siedel() {
        solving_siedel = new double[dim];
        double[] prev = new double[dim];
        System.arraycopy(beta_iter, 0, prev, 0, dim);
        double[] cur = new double[dim];
        int cnt;
        double eps = vector_norm(prev);
        int cnt_siedel = 0;
        while (eps > precision) {
            cnt = 0;
            for (int i = 0; i < dim; i++) {
                cur[i] = beta_iter[i];
                for (int j = 0; j < dim; j++) {
                    double mult = 0.0;
                    if (cnt < alpha_iter.length && alpha_iter[cnt].str == i && alpha_iter[cnt].cl == j) {
                        mult = alpha_iter[cnt++].value;
                    }
                    if (mult == 0.0) {
                        continue;
                    }
                    if (j < i) {
                        cur[i] += mult * cur[j];
                    } else {
                        cur[i] += mult * prev[j];
                    }
                }
            }
            double[] diff = new double[dim];
            double norm = 0;
            for (int i = 0; i < dim; i++) {
                diff[i] = cur[i] - prev[i];
                norm += diff[i] * diff[i];
            }
            eps = Math.sqrt(norm);
            System.arraycopy(cur, 0, prev, 0, dim);
            cnt_siedel++;
        }
        System.arraycopy(prev, 0, solving_siedel, 0, dim);
        return cnt_siedel;
    }

    public static void lab1_n8_1_3() {
        System.out.println("Matrix:");
        print_matrix(coefs);
        System.out.println("Free terms:");
        for (int i = 0; i < dim; i++) {
            System.out.print("" + free_terms[i] + " ");
        }
        System.out.println();
        to_equivalent();
        System.out.println("\nAlpha:");
        print_matrix(alpha_iter);
        System.out.println("Beta:");
        for (int i = 0; i < dim; i++) {
            System.out.print("" + beta_iter[i] + " ");
        }
        System.out.println();
        int iter_cnt  = solve_iterative();
        System.out.println("\nSolving iterative:");
        for (int i = 0; i < dim; i++) {
            System.out.print("" + solving_iter[i] + " ");
        }
        System.out.println("\nIterations: " + iter_cnt);
        int siedel_cnt = siedel();
        System.out.println("\nSolving Siedel:");
        for (int i = 0; i < dim; i++) {
            System.out.print("" + solving_siedel[i] + " ");
        }
        System.out.println("\nIterations: " + siedel_cnt);
    }
}