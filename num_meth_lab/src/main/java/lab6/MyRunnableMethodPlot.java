package lab6;

import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;

import java.io.IOException;
import java.util.Arrays;

import static lab6.Lab6.arrayNorm;

public class MyRunnableMethodPlot implements Runnable {
    Double[][] points;
    Double[] realSolution;
    Double[] error;
    double time;

    MyRunnableMethodPlot(Double[][] p, Double[] real, double t) {
        this.points = new Double[p.length][p[0].length];
        for (int i = 0; i < p.length; i++) {
            System.arraycopy(p[i], 0, points[i], 0, p[i].length);
        }
        this.realSolution = new Double[real.length];
        System.arraycopy(real, 0, realSolution, 0, real.length);
        this.time = t;
        this.error = new Double[real.length];
        for (int i = 0; i < real.length; i++) {
            error[i] = Math.abs(realSolution[i] - points[1][i]);
        }
    }

    @Override
    public void run() {
        double eps = arrayNorm(points[1], realSolution);
        Plot plt = Plot.create();

        plt.subplot(2, 1, 1);
        int rounded = (int) (time * 10000);
        double newTime = ((double) rounded) / 10000.0;
        plt.plot()
                .add(Arrays.asList(points[0]))
                .add(Arrays.asList(points[1]))
                .label("u(x," + newTime + ")");
        plt.plot()
                .add(Arrays.asList(points[0]))
                .add(Arrays.asList(realSolution))
                .label("real u(x," + newTime + ")").linestyle("--");
        plt.xlabel("x");
        plt.ylabel("u(x,t)");
        plt.title("Parabolic");
        plt.legend();

        plt.subplot(2, 1, 2);
        plt.plot()
                .add(Arrays.asList(points[0]))
                .add(Arrays.asList(error)).label("total error: " + eps);
        plt.xlabel("x");
        plt.ylabel("error(x," + newTime + ")");
        plt.title("Error");
        plt.legend();

        try {
            plt.show();
        } catch (IOException | PythonExecutionException e) {
            e.printStackTrace();
        }
    }
}
