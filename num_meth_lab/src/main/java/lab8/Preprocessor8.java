package lab8;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Map;

import static lab8.Lab8.printStat;

public class Preprocessor8 extends Application {
    Map<Double, Double[][]> lastSolution;
    double lastK;
    double lastTau;
    double lastHx;
    double lastHy;
    double lastA = 1.0;
    double lastT = Math.PI; // change in P2DM

    Lab8.Function3<Double, Double, Double, Double> analyticSolution = (x, y, t) ->
            Math.cos(2.0 * x) * Math.sinh(y) * Math.exp(-3.0 * lastA * t);

    @Override
    public void start(Stage stage) throws Exception {
        final int[] methodSelected = {0};
        final boolean[] n1Selected = {false};
        final boolean[] n2Selected = {false};
        final boolean[] kSelected = {false};

        Text textChooseMethod = new Text("Choose method: ");
        Text textEnterN1 = new Text("Enter N1:");
        Text textEnterN2 = new Text("Enter N2:");
        Text textEnterK = new Text("Enter k:");

        TextField textFieldForN1 = new TextField();
        TextField textFieldForN2 = new TextField();
        TextField textFieldForK = new TextField();

        Button buttonSolve = new Button("Solve");
        Button buttonPlot = new Button("Show plot");
        buttonPlot.setDisable(true);
        buttonSolve.setDisable(false);

        ToggleGroup groupMethods = new ToggleGroup();
        RadioButton radioButtonAd = new RadioButton("Alternating direction method");
        radioButtonAd.setToggleGroup(groupMethods);
        RadioButton radioButtonFs = new RadioButton("Fractional steps method");
        radioButtonFs.setToggleGroup(groupMethods);

        Slider sliderTime = new Slider();
        sliderTime.setVisible(true);
        sliderTime.setMin(0);
        sliderTime.setMax(100);
        sliderTime.setValue(0);
        sliderTime.setShowTickLabels(true);
        sliderTime.setShowTickMarks(true);
        sliderTime.setSnapToTicks(true);
        int amointOfTicks = 20;
        int m = (int) Math.ceil(lastK / amointOfTicks);
        double step = m * lastTau;
        System.out.println("step = " + step + ", m = " + m);
        sliderTime.setMinorTickCount(5);
        sliderTime.setBlockIncrement(step);
        sliderTime.setMinorTickCount(5);
        Label labelTimeValue = new Label("time = " + Double.toString(sliderTime.getValue()));
        labelTimeValue.setVisible(false);

        CheckBox checkBoxTable = new CheckBox("Show table");
        File file = new File("C:/Users/Anastasiya/Desktop/MAI/numMeth/num_meth_lab/src/main/java/lab8/var.jpg");

        String localUrl = null;
        try {
            localUrl = file.toURI().toURL().toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        if (localUrl == null) {
            System.out.println("alarm!");
        }
        Image image = new Image(localUrl);

        ImageView imageView = new ImageView();
        imageView.setImage(image);

        sliderTime.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                labelTimeValue.setText(String.format("time = %.6f", new_val));
            }
        });

        textFieldForN1.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                buttonPlot.setDisable(true);
            }
        });

        textFieldForN2.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                buttonPlot.setDisable(true);
            }
        });

        textFieldForK.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                buttonPlot.setDisable(true);
            }
        });

        groupMethods.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle old_toggle, Toggle new_toggle) {
                buttonPlot.setDisable(true);
                if (groupMethods.getSelectedToggle() != null) {
                    if (groupMethods.getSelectedToggle() == radioButtonAd) {
//                        System.out.println("\n\n~~~ Explicit Finite Difference Method  ~~~");
                        methodSelected[0] = 1;
                    } else if (groupMethods.getSelectedToggle() == radioButtonFs) {
//                        System.out.println("\n\n~~~ Implicit Finite Difference Method  ~~~");
                        methodSelected[0] = 2;
                    }
                }
            }
        });

        buttonSolve.setOnAction(event -> {
            if ((textFieldForN1.getText() != null && !textFieldForN1.getText().isEmpty())) {
                n1Selected[0] = true;
                if (kSelected[0] && n2Selected[0]) {
                    buttonSolve.setDisable(false);
                }
            } else {
                System.out.println("You have not entered N1");
            }

            if ((textFieldForN2.getText() != null && !textFieldForN2.getText().isEmpty())) {
                n2Selected[0] = true;
                if (kSelected[0] && n1Selected[0]) {
                    buttonSolve.setDisable(false);
                }
            } else {
                System.out.println("You have not entered N2");
            }

            if ((textFieldForK.getText() != null && !textFieldForK.getText().isEmpty())) {
                kSelected[0] = true;
                if (n1Selected[0] && n2Selected[0]) {
                    buttonSolve.setDisable(false);
                }
                lastK = Double.parseDouble(textFieldForK.getText());

            } else {
                System.out.println("You have not entered K");
            }

            if (n1Selected[0] && n2Selected[0] && kSelected[0] && methodSelected[0] != 0) {
                Integer nn1 = Integer.parseInt(textFieldForN1.getText());
                Integer nn2 = Integer.parseInt(textFieldForN2.getText());
                Integer kk = Integer.parseInt(textFieldForK.getText());
                lastK = kk;
//                sliderTime.setMax(tt);
                if (methodSelected[0] == 1) {
//                    System.out.println("Explicit");
                    AlternatingDirection ad = new AlternatingDirection(nn1, nn2);
                    ad.solve(kk);
                    lastSolution = ad.getFullSolution();
//                    lastK = ad.getK();
                    lastTau = ad.getTau();
                    lastHx = ad.getHx();
                    lastHy = ad.getHy();
                } else if (methodSelected[0] == 2) {
//                    System.out.println("Implicit");
                    FractionalSteps fs = new FractionalSteps(nn1, nn2);
                    fs.solve(kk);
                    lastSolution = fs.getFullSolution();
//                    lastK = fs.getK();
                    lastTau = fs.getTau();
                    lastHx = fs.getHx();
                    lastHy = fs.getHy();
                }
//                System.out.println("N: " + nn + ", T: " + tt);
                buttonPlot.setDisable(false);
                double mm = (int) Math.ceil(lastK / amointOfTicks);
                double sstep = mm * lastTau;
                sliderTime.setMax(lastT);
                System.out.println("m = " + mm + ", lastK = " + lastK + ", step = " + sstep
                        + ", lastTau = " + lastTau + ", lastHx = " + lastHx + ", lastHy = " + lastHy);

                sliderTime.setMajorTickUnit(sstep * 5.0);
                sliderTime.setMinorTickCount(4);
                sliderTime.setBlockIncrement(sstep);
                sliderTime.setVisible(true);
                labelTimeValue.setVisible(true);
                if (checkBoxTable.isSelected()) {
//                    printMap(lastSolution, lastTau);
                    printStat(lastSolution, analyticSolution, lastTau, lastHx, lastHy);
                }
            } else {
                System.out.println("You have not choose all parameters");
            }
        });

        buttonPlot.setOnAction(event -> {
            double thisTime = sliderTime.getValue();
//            int rounded = (int) (thisTime * 10000);
//            double newTime = ((double) rounded) / 10000.0;
            double timeNumber = Math.floor(thisTime / lastTau);
            System.out.println("thisTime: " + thisTime + ", N: " + timeNumber);
            Double[][] thisSolution = lastSolution.get(timeNumber);
            System.out.println("Solution size: " + lastSolution.size());
            if (thisSolution == null) {
                System.out.println("Error! Empty result");
            } else {
                System.out.println("Sol:");
                for (int ii = 0; ii < thisSolution.length; ii++) {
                    for (int jj = 0; jj < thisSolution[0].length; jj++) {
                        System.out.print(thisSolution[ii][jj] + " ");
                    }
                    System.out.println();
                }
                MyRunnableMethodPlot myRunnable = new MyRunnableMethodPlot(thisSolution, analyticSolution, lastHx, lastHy, thisTime);
                Thread t = new Thread(myRunnable);
                t.start();
            }

//            Double[] arrayRealY = new Double[thisSolution[0].length];
//            for (int i = 0; i < thisSolution[0].length; i++) {
//                arrayRealY[i] = analyticSolution.apply(thisSolution[0][i], thisTime);
//            }

        });

        GridPane gridPane = new GridPane();
        gridPane.setMinSize(600, 400);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(5);
        gridPane.setHgap(8);
        gridPane.setAlignment(Pos.CENTER);

        gridPane.add(imageView, 0, 0, 3, 1);

        gridPane.add(textChooseMethod, 0, 1);
        gridPane.add(radioButtonAd, 0, 2);
        gridPane.add(radioButtonFs, 0, 3);

        gridPane.add(textEnterN1, 0, 4);
        gridPane.add(textFieldForN1, 1, 4);
        gridPane.add(textEnterN2, 0, 5);
        gridPane.add(textFieldForN2, 1, 5);
        gridPane.add(textEnterK, 0, 6);
        gridPane.add(textFieldForK, 1, 6);

        gridPane.add(checkBoxTable, 0, 7);
        gridPane.add(buttonSolve, 1, 7);
        gridPane.add(labelTimeValue, 2, 7);
        gridPane.add(sliderTime, 0, 8, 3, 1);
        gridPane.add(buttonPlot, 1, 9);

        Scene scene = new Scene(gridPane);
        stage.setTitle("Parabolic methods");
        stage.setScene(scene);
        stage.show();


//        AlternatingDirection ad = new AlternatingDirection(30, 50);
//        ad.solve(20);
//        FractionalSteps fs = new FractionalSteps(30, 50);
//        fs.solve(20);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
