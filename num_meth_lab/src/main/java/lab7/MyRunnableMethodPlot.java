package lab7;

import com.github.sh0nk.matplotlib4j.NumpyUtils;
import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;
import com.github.sh0nk.matplotlib4j.builder.ContourBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static lab7.Lab7.arrayNorm;

public class MyRunnableMethodPlot implements Runnable{
    ArrayList<ArrayList<Double>> myRes;
    ArrayList<ArrayList<Double>> realSolution;
    List<List<Double>> error;
    double hx;
    double hy;

    MyRunnableMethodPlot(ArrayList<ArrayList<Double>> myRes, Lab7.Function2<Double, Double, Double> analyticSolution,
                         double hx, double hy) {
        this.myRes = myRes;
        this.realSolution = new ArrayList<>();
        this.error = new ArrayList<>();
        this.hx = hx;
        this.hy = hy;
        int i = 0;
        int j = 0;
        for (ArrayList<Double> l : myRes) {
            ArrayList<Double> tmpReal = new ArrayList<>();
            List<Double> tmpErr = new ArrayList<>();
            i = 0;
            for (Double d : l) {
                double tmp = analyticSolution.apply(hx * j, hy * i);
                tmpReal.add(tmp);
                tmpErr.add(Math.abs(tmp - myRes.get(j).get(i)));
                i++;
            }
            realSolution.add(tmpReal);
            error.add(tmpErr);
            j++;
        }
}

    @Override
    public void run() {
        int numx = (int) Math.round(1 / hx) + 1;
        int numy = (int) Math.round(Math.PI / 2.0 / hy) + 1;
        List<Double> x = NumpyUtils.linspace(0, 1, numx);
        List<Double> y = NumpyUtils.linspace(0, Math.PI / 2.0, numy);
        NumpyUtils.Grid<Double> grid = NumpyUtils.meshgrid(x, y);

        List<List<Double>> res = new ArrayList<>();
        List<List<Double>> errFinal = new ArrayList<>();
        double maxErr = 0;
        for (int j = 0; j < myRes.get(0).size(); j++) {
            List<Double> ld = new ArrayList<>();
            List<Double> le = new ArrayList<>();
            for (int i = 0; i < myRes.size(); i++) {
                double val = myRes.get(i).get(j);
                double errVal = error.get(i).get(j);
                if (errVal > maxErr) {
                    maxErr = errVal;
                }
                if (val < 0) {
                    ld.add(0.0);
                } else {
                    ld.add(val);
                }
                le.add(errVal);
            }
            res.add(ld);
            errFinal.add(le);
        }

        Plot plt = Plot.create();
        plt.subplot(2, 1, 1);
        List<Double> listN = NumpyUtils.linspace(-0.1, 1.0, 40);
        ContourBuilder contourRes = plt.contour().add(x, y, res).levels(listN);
        plt.clabel(contourRes).inline(true).fontsize(8);
        plt.title("Elliptic equation");

        plt.subplot(2, 1, 2);

        List<Double> listE = NumpyUtils.linspace(0.0, maxErr, 20);
        ContourBuilder contourErr = plt.contour().add(x, y, errFinal).levels(listE);
        plt.clabel(contourErr).inline(true).fontsize(8);
        plt.title("Error");

        try {
            plt.show();
        } catch (IOException | PythonExecutionException e) {
            e.printStackTrace();
        }
    }
}
