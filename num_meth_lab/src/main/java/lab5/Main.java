
package lab5;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Double[][] arrayX = new Double[2][5];
        for (int i = 0; i < 5; i++) {
            arrayX[0][i] = Double.valueOf(i);
            arrayX[1][i] = Double.valueOf(i * 10 + 1);
        }
        List<Double> a = Arrays.asList(arrayX[0]);
        List<Double> b = Arrays.asList(arrayX[1]);
        System.out.println("size1: " + a.size());
        for (int i = 0; i < a.size(); i++) {
            System.out.println(a.get(i));
        }
        System.out.println("size2: " + b.size());
        for (int i = 0; i < b.size(); i++) {
            System.out.println(b.get(i));
        }


//    System.out.println("~~~~~~~ Parabolic partial differential equation ~~~~~~~");

//    Lab5 lab5 = new Lab5();
//        System.out.println("\n\n~~~ Explicit Finite Difference Method  ~~~");
//        lab5.lab5_efdm(h, tau);
//        System.out.println("\n\n~~~ Implicit Finite Difference Method  ~~~");
//        lab5.lab5_ifdm(h, tau);
//        System.out.println("\n\n~~~ Crank-Nicolson Method  ~~~");
//        lab5.lab5_cnm(n, t, 1);
    }
}

