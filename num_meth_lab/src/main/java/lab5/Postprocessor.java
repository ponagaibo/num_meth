package lab5;

import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;
import java.io.IOException;
import java.util.Arrays;

public class Postprocessor {
    public static void main(String[] args) throws IOException, PythonExecutionException {
        Plot plt = Plot.create();
        plt.plot()
                .add(Arrays.asList(0.0, 0.2, 0.4, 0.6, 0.8, 1.0))
                .add(Arrays.asList(0.97252, 0.94238, 0.89927, 0.85197, 0.79784, 0.65738))
                .label("u(x,1)");
        plt.plot()
                .add(Arrays.asList(0.0, 0.2, 0.4, 0.6, 0.8, 1.0))
                .add(Arrays.asList(0.96864, 0.93518, 0.89113, 0.84344, 0.78934, 0.53738))
                .label("u(x,3)");
        plt.plot()
                .add(Arrays.asList(0.0, 0.2, 0.4, 0.6, 0.8, 1.0))
                .add(Arrays.asList(0.96395, 0.92770, 0.88278, 0.83473, 0.78075, 0.48738))
                .label("u(x,3)");
        plt.plot()
                .add(Arrays.asList(0.0, 0.2, 0.4, 0.6, 0.8, 1.0))
                .add(Arrays.asList(0.95395, 0.91770, 0.85278, 0.80473, 0.75075, 0.43738))
                .label("real");

        plt.xlabel("x");
        plt.ylabel("u(x,t)");
        plt.text(0.5, 0.2, "ratata");
        plt.title("Parabolic");
        plt.legend();
        plt.show();
    }
}
