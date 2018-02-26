package lab1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class tridiagonal_algorithm {
    private static double[] a_terms;
    private static double[] b_terms;
    private static double[] c_terms;
    private static double[] d_terms;

    static int dim;

    public static void readData(String inFile) throws FileNotFoundException {
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
        a_terms[a_cnt++] = sc.nextDouble();
        if (!sc.hasNextDouble()) return;
        b_terms[b_cnt++] = sc.nextDouble();

        for (int i = 0; i < dim; i++) {
            if (!sc.hasNextDouble()) return;
            d_terms[i] = sc.nextDouble();
        }

        // print
        System.out.println("Matrix:");
        a_cnt = b_cnt = c_cnt = 0;
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
        System.out.print("" + a_terms[a_cnt++] + " ");
        System.out.print("" + b_terms[b_cnt++] + " ");
        System.out.println();
        System.out.println("\nFree terms:");
        for (int i = 0; i < dim; i++) {
            System.out.print("" + d_terms[i] + " ");
        }
    }
}
