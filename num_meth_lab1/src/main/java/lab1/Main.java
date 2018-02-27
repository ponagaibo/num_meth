package lab1;

import java.io.FileNotFoundException;

import static lab1.LU_method.*;

public class Main {
    static String inF = ".//src//main//java//lab1//l1.1.txt";
    static String outF = ".//src//main//java//lab1//out_l1.1.txt";

    public static void main(String[] args) throws FileNotFoundException {
        /*
        int size = LU_method.readSize(inF);
        double[][] matr = new double[size][size];
        double[] b_column = new double[size];
        LU_method.readData(inF, size, matr, b_column);
        new LU_method(size, matr, b_column);
        //lab1_n8_1_1();
        */
        tridiagonal_algorithm trid = new tridiagonal_algorithm();
        String fl = ".//src//main//java//lab1//l1.2.txt";
        trid.readData(fl);
        trid.lab1_n8_1_2();
    }
}
