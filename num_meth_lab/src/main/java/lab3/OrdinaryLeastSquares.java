package lab3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

public class OrdinaryLeastSquares {
    static double[] x;
    static double[] y;
    static int dim;
    static double precision;

    void readData(String inFile) throws FileNotFoundException {
        File inputFile = new File(inFile);
        Scanner sc = new Scanner(inputFile).useLocale(Locale.UK);
        if (!sc.hasNextInt()) return;
        dim = sc.nextInt();
        x = new double[dim];
        y = new double[dim];
        for (int i = 0; i < dim; i++) {
            if (!sc.hasNextDouble()) return;
            x[i] = sc.nextDouble();
            y[i] = sc.nextDouble();
        }
        if (!sc.hasNextDouble()) return;
        precision = sc.nextDouble();
        for (int i = 0; i < dim; i++) {
            System.out.println("" + x[i] + " : " + y[i]);
        }
        System.out.println("presicion: " + precision);
    }


}
