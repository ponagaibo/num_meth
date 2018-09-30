package lab1;

class MatrixOperations {
    static double[][] transpose(double[][] m) {
        int dim = m.length;
        double[][] res = new double[dim][dim];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                res[j][i] = m[i][j];
            }
        }
        return res;
    }

    static double[][] identityMatrix(int dim) {
        double[][] identity = new double[dim][dim];
        for (int i = 0; i < dim; i++) {
            identity[i][i] = 1;
        }
        return identity;
    }

    static double[][] multiply(double[][] a, double[][] b) {
        int dim = a.length;
        double[][] res = new double[dim][dim];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                for (int k = 0; k < dim; k++) {
                    res[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return res;
    }

    static void print_matrix(double[][] matr) {
        int dim = matr.length;
        for (double[] aMatr : matr) {
            for (int j = 0; j < dim; j++) {
                System.out.print("" + aMatr[j] + " ");
            }
            System.out.println();
        }
    }
}
