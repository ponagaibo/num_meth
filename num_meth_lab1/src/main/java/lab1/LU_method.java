package lab1;

public class LU_method {
    // A = LU
    // E = AX, X = A^(-1)
    private static double[][] L_matrix;
    private static double[][] U_matrix;
    private static double[][] A_matrix;
    private static double[] b;

    private static double[][] E_matrix = { {1, 0, 0, 0},
                                           {0, 1, 0, 0},
                                           {0, 0, 1, 0},
                                           {0, 0, 0, 1} };
    static int dim;

    public LU_method(int n, double[][] matr, double[] b) {
        dim = n;
        A_matrix = matr;
        L_matrix = new double[n][n];
        U_matrix = new double[n][n];
        this.b = b;
    }

    public LU_method(int n, double[][] matr) {
        dim = n;
        A_matrix = matr;
        L_matrix = new double[n][n];
        U_matrix = new double[n][n];
        this.b = b;
    }

    public void setDim(int dim) {
        this.dim = dim;
    }

    public void setB(double[] b) {
        this.b = b;
    }

    public void setMatrix(double[][] matr) {
        A_matrix = matr;
    }

    public static void decompose() {
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                U_matrix[0][i] = A_matrix[0][i];
                L_matrix[i][0] = A_matrix[i][0] / U_matrix[0][0];
                double sum = 0;
                for (int k = 0; k < i; k++) {
                    sum += L_matrix[i][k] * U_matrix[k][j];
                }
                U_matrix[i][j] = A_matrix[i][j] - sum;
                if (i > j) {
                    L_matrix[j][i] = 0;
                } else {
                    sum = 0;
                    for (int k = 0; k < i; k++) {
                        sum += L_matrix[j][k] * U_matrix[k][i];
                    }
                    L_matrix[j][i] = (A_matrix[j][i] - sum) / U_matrix[i][i];
                }
            }
        }
    }

    public static double[][] multiply(double[][] A, double[][] B) {
        double[][] C = new double[dim][dim];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                C[i][j] = 0.00000;
            }
        }

        for (int i = 0; i < dim; i++) { // aRow
            for (int j = 0; j < dim; j++) { // bColumn
                for (int k = 0; k < dim; k++) { // aColumn
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return C;
    }

    public static void decompose1() { // LU != A
        double[][] newL = new double[dim][dim];
        double[][] newU = new double[dim][dim];
        double[][] newA = new double[dim][dim];
        int[] p = new int[dim];
        for (int i = 0; i < dim; i++) {
            p[i] = i;
            //newL[i][i] = 1.0;
            System.arraycopy(A_matrix, 0, newA, 0, dim);
        }
        for (int i = 0; i < dim - 1; i++) {
            double max = Math.abs(newA[p[i]][i]);
            int pos = i;
            for (int j = i + 1; j < dim; j++) {
                if (Math.abs(newA[p[j]][i]) > max) {
                    max = newA[p[j]][i];
                    pos = j;
                }
            }
            if (max != i) {
                int c = p[i];
                p[i] = p[pos];
                p[pos] = c;

            }
            for (int j = i + 1; j < dim; j++) {
                double k = newA[p[j]][i] / newA[p[i]][i];
                for (int l = 0; l < dim; l++) {
                    newA[p[j]][l] -= k * newA[p[i]][l];
                }
                newL[p[j]][i] = k;
            }
        }
        double[][] tmp = new double[dim][dim];
        for (int i = 0; i < dim; i++) {
            System.arraycopy(newA[p[i]], 0, newU[i], 0, dim);
            System.arraycopy(newL[p[i]], 0, tmp[i], 0, dim);
        }
        double[][] mult = multiply(newL, newA);
        System.out.println("Mult: ");
        print_matrix(mult);

        System.out.println("\nMatrix L:");
        print_matrix(tmp);
        System.out.println("\nMatrix U:");
        print_matrix(newU);
/*
        LU_method check = new LU_method(4, newU, b);
        check.decompose();
        double[][] inv_c = check.inverse_matrix();
        System.out.println("Inverse U: ");
        print_matrix(inv_c);
        double[][] mult = multiply(A_matrix, inv_c);
        System.out.println("Mult: ");
        print_matrix(mult);
        */

    }

    public static double[] solve(double[] column) {
        double[] tmp = new double[dim];
        System.arraycopy(column, 0, tmp, 0, dim);
        for (int i = 0; i < dim - 1; i++) {
            for (int j = i + 1; j < dim; j++) {
                tmp[j] -= L_matrix[j][i] * tmp[i];
            }
        }
        for (int i = dim - 1; i >= 0; i--) {
            tmp[i] /= U_matrix[i][i];
            for (int j = i - 1; j >= 0; j--) {
                tmp[j] -= U_matrix[j][i] * tmp[i];
            }
        }
        return tmp;
    }

    public static double[][] inverse_matrix() {
        double[][] inverse_m = new double[dim][dim];
        for (int j = 0; j < dim; j++) {
            double[] tmp = solve(E_matrix[j]);
            for (int i = 0; i < dim; i++) {
                inverse_m[i][j] = tmp[i];
            }
        }
        return inverse_m;
    }

    public static double determinant() {
        double res = 1;
        for (int i = 0; i < dim; i++) {
            res *= U_matrix[i][i];
        }
        return res;
    }

    public static void print_matrix(double[][] matrix) {
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                System.out.print("" + matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void lab1_n8(LU_method system) {
        // Item 1.1
        /*
        System.out.println("Matrix A:");
        print_matrix(system.A_matrix);
        system.decompose();
        System.out.println("\nMatrix L:");
        print_matrix(system.L_matrix);
        System.out.println("\nMatrix U:");
        print_matrix(system.U_matrix);
        */

        /*
        System.out.println("\nDeterminant = " + system.determinant() + "\n");
        double[] solving = system.solve(system.b);
        System.out.println("\nSolving of system:");
        for (int i = 0; i < dim; i++) {
            System.out.print("" + solving[i] + " ");
        }
        double[][] inverse_m = system.inverse_matrix();
        System.out.println("\nInvese matrix:");
        print_matrix(inverse_m);
        */
        system.decompose1();
    }

    public static void main(String[] args) {
        double[][] matr = { {-4, -9, 4, 3},
                            {2, 7, 9, 8},
                            {4, -4, 0, -2},
                            {-8, 5, 2, 9}};

        double[] b = {-51, 76, 26, -73};

        LU_method system = new LU_method(4, matr, b);
        lab1_n8(system);
    }
}