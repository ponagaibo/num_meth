package lab2;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        NewtonsEquationAlgorithm.readData("./src/main/java/lab2/l2.1.txt");
        IterationEquationMethod.readData("./src/main/java/lab2/l2.1.txt");
        //Lab2.lab2_n8_2_1();
        NewtonsSystemAlgorithm.readData("./src/main/java/lab2/l2.2.txt");
        IterationSystemAlgorithm.readData("./src/main/java/lab2/l2.2.txt");
        Lab2.lab2_n8_2_2();
    }
}
