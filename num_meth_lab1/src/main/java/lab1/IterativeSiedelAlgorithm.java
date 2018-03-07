package lab1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class IterativeSiedelAlgorithm {
    private static class Element {
        double value;
        int str;
        int cl;
        Element(double v, int s, int c) {
            value = v;
            str = s;
            cl = c;
        }
    }

    private static int dim;
    private static double precision;
    private static double[] free_terms;
    private static double[] beta;
    private static double[] solving_iter;
    private static double[] solving_siedel;

    private static Element[] coefs;
    private static Element[] alpha;

    private static Element[] append(Element[] coefs, double v, int s, int c) {
        int prev_size = coefs.length;
        Element[] res = new Element[prev_size + 1];
        System.arraycopy(coefs, 0, res, 0, prev_size);
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

    private static void print_matrix(Element[] coefs) {
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
    }

    private static double matrix_norm(Element[] matrix) {
        double[] total_in_str = new double[dim];
        for (int i = 0; i < dim; i++) {
            for (Element aMatrix : matrix) {
                if (aMatrix.str == i) {
                    total_in_str[i] += Math.abs(aMatrix.value);
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

    private static double vector_norm(double[] vect) {
        double max = 0;
        for (int i = 0; i < dim; i++) {
            if (Math.abs(vect[i]) > max) {
                max = Math.abs(vect[i]);
            }
        }
        return max;
    }

    private static void to_equivalent() {
        beta = new double[dim];
        alpha = new Element[0];
        int cnt = 0;
        double tmp;
        double[] diag = new double[dim];
        for (Element coef : coefs) {
            if (coef.str == coef.cl) {
                diag[cnt++] = coef.value;
            }
        }
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
    }

    private static double[] multiply(Element[] coefs, double[] vect) {
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

    private static int solve_iterative() {
        solving_iter = new double[dim];
        System.arraycopy(beta, 0, solving_iter,0, dim);
        double eps = precision + 1;
        int cnt_iter = 0;
        while (eps > precision) {
            double[] tmp = multiply(alpha, solving_iter);
            for (int i = 0; i < dim; i++) {
                tmp[i] += beta[i];
            }
            double[] diff = new double[dim];
            for (int i = 0; i < dim; i++) {
                diff[i] = tmp[i] - solving_iter[i];
            }
            eps = (vector_norm(diff) * matrix_norm(alpha)) / (1 - matrix_norm(alpha));
            System.arraycopy(tmp, 0, solving_iter,0, dim);
            cnt_iter++;
        }
        return cnt_iter;
    }

    private static int siedel() {
        solving_siedel = new double[dim];
        double[] prev = new double[dim];
        System.arraycopy(beta, 0, prev, 0, dim);
        double[] cur = new double[dim];
        int cnt;
        double eps = vector_norm(prev);
        int cnt_siedel = 0;
        //double[][] C_matrix = new double[dim][dim];
        Element[] C_matrix = new Element[0];
        int cnt_c = 0;
        for (int i = 0; i < dim - 1; i++) {
            for (int j = i + 1; j < dim; j++) {
                while (alpha[cnt_c].str < i) {
                    cnt_c++;
                }
                while (alpha[cnt_c].cl < j) {
                    cnt_c++;
                }
                if (alpha[cnt_c].str == i && alpha[cnt_c].cl == j) {
                    C_matrix = append(C_matrix, alpha[cnt_c].value, i, j);
                    cnt_c++;
                }
            }
        }
        while (eps > precision) {
            cnt = 0;
            for (int i = 0; i < dim; i++) {
                cur[i] = beta[i];
                for (int j = 0; j < dim; j++) {
                    double mult = 0.0;
                    if (cnt < alpha.length && alpha[cnt].str == i && alpha[cnt].cl == j) {
                        mult = alpha[cnt++].value;
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
            for (int i = 0; i < dim; i++) {
                diff[i] = cur[i] - prev[i];
            }
            eps = (matrix_norm(C_matrix) * vector_norm(diff)) / (1 - matrix_norm(alpha));
            System.arraycopy(cur, 0, prev, 0, dim);
            cnt_siedel++;
        }
        System.arraycopy(prev, 0, solving_siedel, 0, dim);
        return cnt_siedel;
    }

    public static void lab1_n8_1_3() {
        System.out.println("\n~~~ Siedel algorithm ~~~");
        System.out.println("\nOriginal matrix:");
        print_matrix(coefs);
        System.out.println("\nFree terms:");
        for (int i = 0; i < dim; i++) {
            System.out.print("" + free_terms[i] + " ");
        }
        System.out.println();
        to_equivalent();
        System.out.println("\nAlpha:");
        print_matrix(alpha);
        System.out.println("\nBeta:");
        for (int i = 0; i < dim; i++) {
            System.out.print("" + beta[i] + " ");
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
        System.out.println("\n~~~~~~~~~~~~~~~~~~");
    }
}