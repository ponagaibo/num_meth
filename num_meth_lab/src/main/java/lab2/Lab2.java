package lab2;

class Lab2 {
    static void lab2_n8_2_1() {
        System.out.println("\n~~~ Newton's method for equation ~~~");
        int n_cnt = NewtonsEquationAlgorithm.findRoot();
        System.out.println("Root: " + NewtonsEquationAlgorithm.root);
        System.out.println("Iterations: " + n_cnt);
        System.out.println("~~~~~~~~~~~~~~~~~~");
        System.out.println("\n~~~ Iteration method for equation ~~~");
        int it_cnt = IterationEquationMethod.findRoot();
        System.out.println("Root: " + IterationEquationMethod.root);
        System.out.println("Iterations: " + it_cnt);
        System.out.println("~~~~~~~~~~~~~~~~~~");
    }

    static void lab2_n8_2_2() {
        System.out.println("\n~~~ Newton's method for system ~~~");
        int n_cnt = NewtonsSystemAlgorithm.findRoots();
        System.out.println("Roots: " + NewtonsSystemAlgorithm.x + " " + NewtonsSystemAlgorithm.y);
        System.out.println("Iterations: " + n_cnt);
        System.out.println("~~~~~~~~~~~~~~~~~~");
        /*
        double c1 = NewtonsSystemAlgorithm.f1(NewtonsSystemAlgorithm.x, NewtonsSystemAlgorithm.y);
        double c2 = NewtonsSystemAlgorithm.f2(NewtonsSystemAlgorithm.x, NewtonsSystemAlgorithm.y);
        System.out.println("check: " + c1 + "  " + c2);
        */

        System.out.println("\n~~~ Iteration method for system ~~~");
        int it_cnt = IterationSystemMethod.findRoots();
        System.out.println("Roots: " + IterationSystemMethod.x + " " + IterationSystemMethod.y);
        System.out.println("Iterations: " + it_cnt);
        System.out.println("~~~~~~~~~~~~~~~~~~");
    }
}
