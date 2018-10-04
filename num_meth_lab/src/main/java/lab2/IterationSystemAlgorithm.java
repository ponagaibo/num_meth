package lab2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class IterationSystemAlgorithm {
    static double precision;
    double x;
    double y;

    public static void readData(String inFile) throws FileNotFoundException {
        File inputFile = new File(inFile);
        Scanner sc = new Scanner(inputFile);
        if (!sc.hasNextDouble()) return;
        precision = sc.nextDouble();
    }

    static int findRoots() {
        int cnt = 0;

        return cnt;
    }
}
