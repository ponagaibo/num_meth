package lab2;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
   /*     double a = 0.8;
        double b = 1;
        double x0 = 0.8;
        double mult = NewtonAlgorithm.func(0.8) * NewtonAlgorithm.func(1);
        System.out.println("[0,8; 1], f(a)f(b): " + mult);
        double start = NewtonAlgorithm.func(1) * NewtonAlgorithm.ddfunc(1);
        System.out.println("Check f(x0)f''(x0): " + start + ", x0 = " + x0);*/
        System.out.println("dir: " + System.getProperty("user.dir"));
        NewtonAlgorithm.readData("./src/main/java/lab2/l2.1.txt");
        NewtonAlgorithm.lab2_n8_2_1();

    }
}
