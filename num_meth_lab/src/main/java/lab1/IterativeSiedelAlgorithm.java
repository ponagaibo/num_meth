package lab1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

public class IterativeSiedelAlgorithm {
    private class Element {
        double value;
        int str;
        int cl;
        Element(double v, int s, int c) {
            value = v;
            str = s;
            cl = c;
        }
    }

    private int dim;
    private double precision;
    private double[] free_terms;
    private double[] beta;
    private double[] solving_iter;
    private double[] solving_siedel;

    private Element[] coefs;
    private Element[] alpha;

    public int getDim() {
        return dim;
    }

    public double[] getSolvingSiedel(double[] solving) {
        System.arraycopy(solving_siedel, 0, solving, 0, solving_siedel.length);
        return solving_siedel;
    }

    public double[] getSolvingSiedel() {
        return solving_siedel;
    }

    private Element[] append(Element[] coefs, double v, int s, int c) {
        int prev_size = coefs.length;
        Element[] res = new Element[prev_size + 1];
        System.arraycopy(coefs, 0, res, 0, prev_size);
        res[prev_size] = new Element(v, s, c);
        return res;
    }

    public void readData(String inFile) throws FileNotFoundException {
        File inputFile = new File(inFile);
        Scanner sc = new Scanner(inputFile).useLocale(Locale.US);
        if (!sc.hasNextInt()) {
            System.out.println("Alarm!! There is no dimension number");
            return;
        }
        dim = sc.nextInt();
//        System.out.println("dim: " + dim);
        coefs = new Element[0];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (!sc.hasNextDouble()) {
                    System.out.println("Alarm!! There is no matrix coef");
                    return;
                }
                double tmp = sc.nextDouble();
                if (tmp != 0.0) {
                    coefs = append(coefs, tmp, i, j);
                }
            }
        }
//        System.out.println("Get matrix: ");
//        print_matrix(coefs);

        free_terms = new double[dim];
        for (int i = 0; i < dim; i++) {
            if (!sc.hasNextDouble()) {
                System.out.println("Alarm!! There is no right part");
                return;
            }
            free_terms[i] = sc.nextDouble();
        }
//        System.out.println("Right part: ");
//        for (int i = 0; i < free_terms.length; i++) {
//            System.out.print("" + free_terms[i] + " ");
//        }
//        System.out.println();
        if (!sc.hasNextDouble()) {
            System.out.println("Alarm!! There is no precision");
            return;
        }
        precision = sc.nextDouble();
//        System.out.println("eps: " + precision);
//        System.out.println("Data is read successfully");
    }

    public void getData(int dimension, double[][] m, double[] d, double eps) {
        dim = dimension;
        coefs = new Element[0];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (m[i][j] != 0.0) {
                    coefs = append(coefs, m[i][j], i, j);
                }
            }
        }
        free_terms = new double[dim];
        for (int i = 0; i < dim; i++) {
            free_terms[i] = d[i];
        }
        precision = eps;

        System.out.println("Get dim: " + dim);
        System.out.println("Get matr: ");
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                System.out.print(m[i][j] + " ");
            }
            System.out.print("\n");
        }
        System.out.println("Get right part: ");
        for (int i = 0; i < m.length; i++) {
            System.out.print(d[i] + " ");
        }
        System.out.println("\n");
        System.out.println("Get eps: " + eps);
    }

    private void print_matrix(Element[] coefs) {
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

    private double matrix_norm(Element[] matrix) {
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

    private double matrix_norm_c(Element[] matrix) {
        double[] total_in_col = new double[dim];
        for (int i = 0; i < dim; i++) {
            for (Element aMatrix : matrix) {
                if (aMatrix.cl == i) {
                    total_in_col[i] += Math.abs(aMatrix.value);
                }
            }
        }
        double max = 0;
        for (int i = 0; i < dim; i++) {
            if (total_in_col[i] > max) {
                max = total_in_col[i];
            }
        }
        return max;
    }

    private double vector_norm(double[] vect) {
        double max = 0;
        for (int i = 0; i < dim; i++) {
            if (Math.abs(vect[i]) > max) {
                max = Math.abs(vect[i]);
            }
        }
        return max;
    }

    private void to_equivalent() {
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

    private double[] multiply(Element[] coefs, double[] vect) {
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

    public int solve_iterative() {
        to_equivalent();
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

    public int siedel() {
        to_equivalent();
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

    public double[] solveSiedel() {
        to_equivalent();
        int siedel_cnt = siedel();
//        System.out.println("\nIterations: " + siedel_cnt);
        return getSolvingSiedel();
    }

    public double[] solveIterative() {
        System.out.println("1");
        to_equivalent();
        solving_iter = new double[dim];
        System.arraycopy(beta, 0, solving_iter,0, dim);
        double eps = precision + 1;
        int cnt_iter = 0;
        System.out.println("Eps = " + eps + ", prec = " + precision);
        int cnt = 5;
        while (eps > precision && cnt_iter < cnt) {
            System.out.println("2");

            double[] tmp = multiply(alpha, solving_iter);
            System.out.println("3");
            for (int i = 0; i < dim; i++) {
                tmp[i] += beta[i];
            }
            double[] diff = new double[dim];
            for (int i = 0; i < dim; i++) {
                diff[i] = tmp[i] - solving_iter[i];
            }
            System.out.println("Vector norm: " + vector_norm(diff));
            System.out.println("Matrix norm: " + matrix_norm_c(alpha));
            eps = (vector_norm(diff) * matrix_norm_c(alpha)) / (1 - matrix_norm_c(alpha));
            System.out.println("4");
            System.out.println("New eps = " + eps);

            System.arraycopy(tmp, 0, solving_iter,0, dim);
            cnt_iter++;
        }
        System.out.println("5");
        return solving_iter;
    }

    void lab1_n8_1_3() {
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