package lab5;

import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PlotImpl;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class Main {
    private static final boolean DRY_RUN = true;
    public static void main(String[] args) throws IOException, PythonExecutionException {
        /*Double[][] arrayX = new Double[2][5];
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
        }*/

        Plot plt = Plot.create();

        plt.subplot(2, 1, 1);
        plt.plot()
                .add(Arrays.asList(1, 2, 3), Arrays.asList(1, 4, 9))
                .label("u(x,1)");
        plt.xlabel("a");
        plt.ylabel("u(a,t)");
        plt.title("ParabolicA");
        plt.legend();

        plt.subplot(2, 1, 2);
        plt.plot()
                .add(Arrays.asList(1, 2, 3), Arrays.asList(1, -8, 27))
                .label("u(x,2)");
        plt.xlabel("b");
        plt.ylabel("u(b,t)");
        plt.title("ParabolicB");
        plt.legend();

        plt.show();


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

