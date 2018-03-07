package lab1;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        // Item 1.1.
        LU_Algorithm.readData("l1.1.txt");
        LU_Algorithm.lab1_n8_1_1();


        // Item 1.2
        TridiagonalAlgorithm.readData("l1.2.txt");
        TridiagonalAlgorithm.lab1_n8_1_2();


        // Item 1.3
        IterativeSiedelAlgorithm.readData("l1.3.txt");
        IterativeSiedelAlgorithm.lab1_n8_1_3();


        // Item 1.4
        RotationAlgorithm.readData("l1.4.txt");
        RotationAlgorithm.lab1_n8_1_4();


    }
}
