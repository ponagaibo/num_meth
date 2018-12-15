package lab8;

import com.github.sh0nk.matplotlib4j.NumpyUtils;
import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;
import com.github.sh0nk.matplotlib4j.builder.ContourBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyRunnableMethodPlot implements Runnable{
    ArrayList<ArrayList<Double>> myRes;
    ArrayList<ArrayList<Double>> realSolution;
    List<List<Double>> error;
    double hx;
    double hy;
    double tau;

    MyRunnableMethodPlot(Double[][] res, Lab8.Function3<Double, Double, Double, Double> analyticSolution,
                         double hx, double hy, double time) {
        myRes = new ArrayList<>();
        for (int i = 0; i < res.length; i++) {
            ArrayList<Double> tmpl = new ArrayList<>();
            for (int j = 0; j < res[0].length; j++) {
                tmpl.add(res[i][j]);
            }
            myRes.add(tmpl);
        }

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
                double tmp = analyticSolution.apply(hx * j, hy * i, time);
                tmpReal.add(tmp);
                tmpErr.add(Math.abs(tmp - myRes.get(j).get(i)));
                i++;
            }
            realSolution.add(tmpReal);
            error.add(tmpErr);
            j++;
        }


        /*this.myRes = myRes;
        this.realSolution = new ArrayList<>();
        this.error = new ArrayList<>();
        this.hx = hx;
        this.hy = hy;
        this.tau = tau;
        for (Map.Entry<Double, Double[][]> entry : myRes.entrySet()) {
            ArrayList<Double> tmpReal = new ArrayList<>();
            List<Double> tmpErr = new ArrayList<>();
            Double key = entry.getKey();
            Double[][] value = entry.getValue();
            for (int i = 0; i < value.length; i++) {
                for (int j = 0; j < value[0].length; j++) {
                    double tmp = analyticSolution.apply(hx * j, hy * i, tau * key);
                    tmpReal.add(tmp);
                    tmpErr.add(Math.abs(tmp - value[i][j])); // mb j i
                }
                realSolution.add(tmpReal);
                error.add(tmpErr);
            }
        }*/
    }

    @Override
    public void run() {
        int numx = (int) Math.round(Math.PI / 2.0 / hx) + 1;
        int numy = (int) Math.round(Math.log(2.0) / hy) + 1;
        List<Double> x = NumpyUtils.linspace(0, Math.PI / 2.0, numx);
        List<Double> y = NumpyUtils.linspace(0, Math.log(2.0), numy);
        NumpyUtils.Grid<Double> grid = NumpyUtils.meshgrid(x, y);

        List<List<Double>> res = new ArrayList<>();
        List<List<Double>> errFinal = new ArrayList<>();
        double maxErr = 0;
        double maxVal = Double.MIN_VALUE;
        double minVal = Double.MAX_VALUE;

        for (int j = 0; j < myRes.get(0).size(); j++) {
            List<Double> ld = new ArrayList<>();
            List<Double> le = new ArrayList<>();
            for (int i = 0; i < myRes.size(); i++) {
                Double val = myRes.get(i).get(j);

                double errVal = error.get(i).get(j);
                if (errVal > maxErr) {
                    maxErr = errVal;
                }
                if (val > maxVal) {
                    maxVal = val;
                }
                if (val < minVal) {
                    minVal = val;
                }
                Double zero = 0.0;
                if (val.equals(zero)) {
                    ld.add(zero);
//                    System.out.print("0.0 ");
                } else {
                    ld.add(val);
//                    System.out.print(val + " ");
                }

                le.add(errVal);
//                System.out.print(errVal + " ");
            }
//            System.out.println();
            res.add(ld);
            errFinal.add(le);
        }

        Plot plt = Plot.create();
        plt.subplot(2, 1, 1);
        System.out.println("Max val = " + maxVal + ", min val = " + minVal);
        List<Double> listN = NumpyUtils.linspace(minVal, maxVal, 15);
        ContourBuilder contourRes = plt.contour().add(x, y, res).levels(listN);
        plt.clabel(contourRes).inline(true).fontsize(6);
        plt.title("Parabolic 2D equation");

        plt.subplot(2, 1, 2);

        System.out.println("Max error = " + maxErr);
        List<Double> listE = NumpyUtils.linspace(0.0, maxErr, 10);
        ContourBuilder contourErr = plt.contour().add(x, y, errFinal).levels(listE);
        plt.clabel(contourErr).inline(true).fontsize(6);
        plt.title("Error");

        try {
            plt.show();
        } catch (IOException | PythonExecutionException e) {
            e.printStackTrace();
        }
    }
}
