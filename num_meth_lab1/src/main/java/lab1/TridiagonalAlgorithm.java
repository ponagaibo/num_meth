package lab1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class TridiagonalAlgorithm {
    private double[] a_terms;
    private double[] b_terms;
    private double[] c_terms;
    private double[] d_terms;

    private double[] p_coef;
    private double[] q_coef;

    private double[] solving;

    private int dim;

    private void print_matrix() {
        int a_cnt = 0;
        int b_cnt = 0;
        int c_cnt = 0;
        System.out.print("" + b_terms[b_cnt++] + " ");
        System.out.print("" + c_terms[c_cnt++] + " ");
        for (int j = 0; j < dim - 2; j++) {
            System.out.print("0.0 ");
        }
        System.out.println();
        for (int i = 0; i < dim - 2; i++) {
            for (int j = 0; j < i; j++) {
                System.out.print("0.0 ");
            }
            System.out.print("" + a_terms[a_cnt++] + " ");
            System.out.print("" + b_terms[b_cnt++] + " ");
            System.out.print("" + c_terms[c_cnt++] + " ");
            for (int j = i + 3; j < dim; j++) {
                System.out.print("0.0 ");
            }
            System.out.println();
        }
        for (int j = 0; j < dim - 2; j++) {
            System.out.print("0.0 ");
        }
        System.out.print("" + a_terms[a_cnt] + " ");
        System.out.print("" + b_terms[b_cnt] + " ");
        System.out.println();
        System.out.println("\nFree terms:");
        for (int i = 0; i < dim; i++) {
            System.out.print("" + d_terms[i] + " ");
        }
        System.out.println();
    }

    void readData(String inFile) throws FileNotFoundException {
        File inputFile = new File(inFile);
        Scanner sc = new Scanner(inputFile);
        if (!sc.hasNextInt()) return;
        dim = sc.nextInt();
        a_terms = new double[dim - 1];
        b_terms = new double[dim];
        c_terms = new double[dim - 1];
        d_terms = new double[dim];
        int a_cnt = 0;
        int b_cnt = 0;
        int c_cnt = 0;
        if (!sc.hasNextDouble()) return;
        b_terms[b_cnt++] = sc.nextDouble();
        if (!sc.hasNextDouble()) return;
        c_terms[c_cnt++] = sc.nextDouble();

        for (int i = 0; i < dim - 2; i++) {
            if (!sc.hasNextDouble()) return;
            a_terms[a_cnt++] = sc.nextDouble();
            if (!sc.hasNextDouble()) return;
            b_terms[b_cnt++] = sc.nextDouble();
            if (!sc.hasNextDouble()) return;
            c_terms[c_cnt++] = sc.nextDouble();

        }
        if (!sc.hasNextDouble()) return;
        a_terms[a_cnt] = sc.nextDouble();
        if (!sc.hasNextDouble()) return;
        b_terms[b_cnt] = sc.nextDouble();

        for (int i = 0; i < dim; i++) {
            if (!sc.hasNextDouble()) return;
            d_terms[i] = sc.nextDouble();
        }
    }

    private void algo() {
        p_coef = new double[dim];
        q_coef = new double[dim];
        p_coef[0] = -c_terms[0] / b_terms[0];
        q_coef[0] = d_terms[0] / b_terms[0];
        for (int i = 1; i < dim - 1; i++) {
            double denom = b_terms[i] + a_terms[i - 1] * p_coef[i - 1];
            p_coef[i] = -c_terms[i] / denom;
            q_coef[i] = (d_terms[i] - a_terms[i - 1] * q_coef[i - 1]) / denom;
        }
        p_coef[dim - 1] = 0;
        double denom = b_terms[dim - 1] + a_terms[dim - 2] * p_coef[dim - 2];
        q_coef[dim - 1] = (d_terms[dim - 1] - a_terms[dim - 2] * q_coef[dim - 2]) / denom;

        solving = new double[dim];
        solving[dim - 1] = q_coef[dim - 1];
        for (int i = dim - 1; i >= 1; i--) {
            solving[i - 1] = p_coef[i - 1] * solving[i] + q_coef[i - 1];
        }
    }

    void lab1_n8_1_2() {
        System.out.println("\n~~~ Tridiagonal algorithm ~~~");
        System.out.println("\nOriginal matrix:");
        print_matrix();
        algo();
        System.out.println("\nP:");
        for (int i = 0; i < dim; i++) {
            System.out.print("" + p_coef[i] + " ");
        }
        System.out.println("\n\nQ:");
        for (int i = 0; i < dim; i++) {
            System.out.print("" + q_coef[i] + " ");
        }
        System.out.println("\n\nSolving tridiagonal algorithm:");
        for (int i = 0; i < dim; i++) {
            System.out.print("" + solving[i] + " ");
        }
        System.out.println();
        System.out.println("\n~~~~~~~~~~~~~~~~~~");
    }
}
