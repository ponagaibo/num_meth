package lab1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class RotationAlgorithm {
    private static int dim;
    private static double precision;
    private static double[][] original;
    private static double[][] eigenvalues;
    private static double[][] eigenvectors;

    public static void readData(String inFile) throws FileNotFoundException {
        File inputFile = new File(inFile);
        Scanner sc = new Scanner(inputFile);
        if (!sc.hasNextInt()) return;
        dim = sc.nextInt();
        original = new double[dim][dim];

        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (!sc.hasNextDouble()) return;
                original[i][j] = sc.nextDouble();
            }
        }

        if (!sc.hasNextDouble()) return;
        precision = sc.nextDouble();

        System.out.println("\nOriginal matrix:");
        print_matrix(original);
    }

    public static void print_matrix(double[][] matr) {
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                System.out.print("" + matr[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static double[][] buildRotationMatrix(int pos_i, int pos_j, double phi) {
        double[][] rotationMatrix = identityMatrix();
        rotationMatrix[pos_i][pos_i] = Math.cos(phi);
        rotationMatrix[pos_j][pos_j] = Math.cos(phi);
        rotationMatrix[pos_i][pos_j] = -Math.sin(phi);
        rotationMatrix[pos_j][pos_i] = Math.sin(phi);
        return rotationMatrix;
    }

    private static double[][] identityMatrix() {
        double[][] identity = new double[dim][dim];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (i == j) {
                    identity[i][j] = 1;
                } else {
                    identity[i][j] = 0;
                }
            }
        }
//        System.out.println("\nIdentity matrix:");
//        print_matrix(identity);
        return identity;
    }

    private static double[][] multiply(double[][] a, double[][] b) {
        double[][] res = new double[dim][dim];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                res[i][j] = 0.0;
            }
        }
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                for (int k = 0; k < dim; k++) {
                    res[i][j] += a[i][k] * b[k][j];
                }

/*                double tmp = res[i][j] * 100000;
                int tmp1 = (int)tmp;
                res[i][j] = tmp1 / 100000.0;*/

            }
        }
        return res;
    }

    private static double[][] transpose(double[][] m) {
        double[][] res = new double[dim][dim];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                res[j][i] = m[i][j];
            }
        }
        return res;
    }

    public static void calculate() {
        double[][] a_matr = new double[dim][dim];
        double[][] rot;
        eigenvectors = identityMatrix();
        eigenvalues = new double[dim][dim];
        for (int i = 0; i < dim; i++) {
            System.arraycopy(original[i], 0, a_matr[i], 0, dim);
        }

        double eps;
        double sum = 0;
        for (int i = 0; i < dim - 1; i++) {
            for (int j = i + 1; j < dim; j++) {
                sum += a_matr[i][j] * a_matr[i][j];
            }
        }
        eps = Math.sqrt(sum);
//        System.out.println("\neps: " + eps);
        double phi;
        while (eps > precision) {
            int pos_i = 0;
            int pos_j = dim - 1;
            double max = a_matr[pos_i][pos_j];
            for (int i = 0; i < dim - 1; i++) {
                for (int j = i + 1; j < dim; j++) {
                    if (a_matr[i][j] > max) {
                        max = a_matr[i][j];
                        pos_i = i;
                        pos_j = j;
                    }
                }
            }
//            System.out.println("\nMax: " + max);

            if (a_matr[pos_i][pos_i] != a_matr[pos_j][pos_j]) {
                phi = Math.atan((2 * max) / (a_matr[pos_i][pos_i] - a_matr[pos_j][pos_j])) / 2;
//                System.out.println("\na[i][i]: " + a_matr[pos_i][pos_i] + "  a[j][j]: " + a_matr[pos_j][pos_j]);
            } else {
                phi = Math.PI / 4;
            }
//            System.out.println("\nPhi: " + phi);
            rot = buildRotationMatrix(pos_i, pos_j, phi);
//            System.out.println("\nRotation matrix:");
//            print_matrix(rot);
            eigenvectors = multiply(eigenvectors, rot);

            double[][] transp = transpose(rot);
//            System.out.println("\nTransposed rotation matrix:");
//            print_matrix(transp);

//            System.out.println("\nA matrix:");
//            print_matrix(a_matr);

            double[][] mult = multiply(transp, a_matr);
//            System.out.println("\nMult matrix:");
//            print_matrix(mult);

            a_matr = multiply(mult, rot);


            sum = 0;
            for (int i = 0; i < dim - 1; i++) {
                for (int j = i + 1; j < dim; j++) {
                    sum += a_matr[i][j] * a_matr[i][j];
                }
            }

            for (int i = 0; i < dim; i++) {
                System.arraycopy(a_matr[i], 0, eigenvalues[i], 0, dim);
            }

            eps = Math.sqrt(sum);
        }
        System.out.println("\nlambda matrix:");
        print_matrix(eigenvalues);

        System.out.println("\nU matrix:");
        print_matrix(eigenvectors);
    }
}
