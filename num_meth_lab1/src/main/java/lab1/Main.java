package lab1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static lab1.LU_method.lab1_n8;

public class Main {
    static String inF = "G:\\\\numMeth\\l1.1.txt";
    static String outF = "G:\\\\numMeth\\out_l1.1.txt";

    public static void main(String[] args) throws FileNotFoundException {
        File inputFile = new File(inF);
        Scanner sc = new Scanner(inputFile);
        if (!sc.hasNextInt()) return;
        int size = sc.nextInt();
        int cnt = 0;
        double[] elem = new double[size * (size + 1)];
        double[][] matr = new double[size][size];
        double[] b_column = new double[size];
        while (sc.hasNextDouble() && cnt < size * (size + 1)) {
            elem[cnt++] = sc.nextDouble();
        }
        cnt = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matr[i][j] = elem[cnt++];
            }
        }
        for (int i = 0; i < size; i++) {
            b_column[i] = elem[cnt++];
        }

        new LU_method(size, matr, b_column);
        lab1_n8();
    }
}
