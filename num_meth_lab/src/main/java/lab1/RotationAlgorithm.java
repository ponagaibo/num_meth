package lab1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class RotationAlgorithm {
    private int dim;
    private double precision;
    private double[][] original;
    private double[] eigenvalues;
    private double[][] eigenvectors;

    void readData(String inFile) throws FileNotFoundException {
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
    }

    private double[][] buildRotationMatrix(int pos_i, int pos_j, double phi) {
        double[][] rotationMatrix = MatrixOperations.identityMatrix(dim);
        rotationMatrix[pos_i][pos_i] = Math.cos(phi);
        rotationMatrix[pos_j][pos_j] = Math.cos(phi);
        rotationMatrix[pos_i][pos_j] = -Math.sin(phi);
        rotationMatrix[pos_j][pos_i] = Math.sin(phi);
        return rotationMatrix;
    }

    private void calculate() {
        double[][] a_matr = new double[dim][dim];
        double[][] rot;
        eigenvectors = MatrixOperations.identityMatrix(dim);
        eigenvalues = new double[dim];
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
        double phi;
        while (eps > precision) {
            int pos_i = 0;
            int pos_j = dim - 1;
            double max = a_matr[pos_i][pos_j];
            for (int i = 0; i < dim - 1; i++) {
                for (int j = i + 1; j < dim; j++) {
                    if (Math.abs(a_matr[i][j]) > Math.abs(max)) {
                        max = a_matr[i][j];
                        pos_i = i;
                        pos_j = j;
                    }
                }
            }
            if (a_matr[pos_i][pos_i] != a_matr[pos_j][pos_j]) {
                phi = Math.atan((2 * max) / (a_matr[pos_i][pos_i] - a_matr[pos_j][pos_j])) / 2;
            } else {
                phi = Math.PI / 4;
            }
            rot = buildRotationMatrix(pos_i, pos_j, phi);
            eigenvectors = MatrixOperations.multiply(eigenvectors, rot);

            double[][] transp = MatrixOperations.transpose(rot);
            double[][] mult = MatrixOperations.multiply(transp, a_matr);
            a_matr = MatrixOperations.multiply(mult, rot);
            sum = 0;
            for (int i = 0; i < dim - 1; i++) {
                for (int j = i + 1; j < dim; j++) {
                    sum += a_matr[i][j] * a_matr[i][j];
                }
            }
            for (int i = 0; i < dim; i++) {
                eigenvalues[i] = a_matr[i][i];
            }
            eps = Math.sqrt(sum);
        }
    }

    void lab1_n8_1_4() {
        System.out.println("\n~~~ Rotation algorithm ~~~");
        System.out.println("\nOriginal matrix:");
        MatrixOperations.print_matrix(original);
        calculate();
        System.out.println("\nEigenalues:");
        for (int i = 0; i < dim; i++) {
            System.out.print("" + eigenvalues[i] + " ");
        }
        System.out.println();
        System.out.println("\nEigenvectors:");
        MatrixOperations.print_matrix(eigenvectors);
        System.out.println("\n~~~~~~~~~~~~~~~~~~");
    }
}
