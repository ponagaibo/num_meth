package lab2;

public class Lab2 {
    public static void lab2_n8_2_1() {
        System.out.println("\n~~~ Newton's method for equation ~~~");
        int n_cnt = NewtonsEquationAlgorithm.findRoot();
        System.out.println("\nRoot: " + NewtonsEquationAlgorithm.root);
        System.out.println("Iterations: " + n_cnt);
        System.out.println("\n~~~ Iteration method for equation ~~~");
        int it_cnt = IterationEquationMethod.findRoot();
        System.out.println("\nRoot: " + IterationEquationMethod.root);
        System.out.println("Iterations: " + it_cnt);
    }

    public static void lab2_n8_2_2() {
        System.out.println("\n~~~ Newton's method for system ~~~");
        int n_cnt = NewtonsSystemAlgorithm.findRoots();
        System.out.println("\nRoots: " + NewtonsSystemAlgorithm.x + " " + NewtonsSystemAlgorithm.y);
        System.out.println("Iterations: " + n_cnt);
    }
}
