package lab1;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        /*
        int size = LU_Algorithm.readSize(".//src//main//java//lab1//l1.1.txt");
        double[][] matr = new double[size][size];
        double[] b_column = new double[size];
        LU_Algorithm.readData(".//src//main//java//lab1//l1.1.txt", size, matr, b_column);
        new LU_Algorithm(size, matr, b_column);
        //lab1_n8_1_1();
        */

        /*
        // Item 1.1.
        LU_Algorithm.readData(".//src//main//java//lab1//l1.1.txt");
        LU_Algorithm.lab1_n8_1_1();
        */

        /*
        // Item 1.2
        TridiagonalAlgorithm trid = new TridiagonalAlgorithm();
        String fl = ".//src//main//java//lab1//l1.2.txt";
        //String fl = ".//src//main//java//lab1//test.txt";
        trid.readData(fl);
        trid.lab1_n8_1_2();
        */

        // Item 1.3
        IterativeSiedelAlgorithm itAlg = new IterativeSiedelAlgorithm();
        itAlg.readData(".//src//main//java//lab1//l1.3.txt");
        //itAlg.readData(".//src//main//java//lab1//test.txt");
        itAlg.lab1_n8_1_3();
    }
}
