package lab5;

import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

import static lab5.Lab5.printMap;

// later may be CheckBox (yes or no every methods) to draw several plots

// disable buttonPlot when change method or approx

// TODO: plot for error, several plots, time via N, checkBox for table

public class Preprocessor extends Application {
    Map<Double, Double[][]> lastSolution;
    double lastK;
    double lastTau;
    double lastT;
    double lastH;
    Lab5.Function2<Double, Double, Double> analyticSolution = (x, t) -> Math.sin(t) * Math.cos(x);

    @Override
    public void start(Stage stage) {
        final int[] methodSelected = {0};
        final int[] aprSelected = {0};
        final boolean[] nSelected = {false};
        final boolean[] tSelected = {false};

        Text textChooseMethod = new Text("Choose method: ");
        Text textChooseApr = new Text("Choose approximation");
        Text textEnterN = new Text("Enter N:");
        Text textEnterT = new Text("Enter T:");

        TextField textFieldForN = new TextField();
        TextField textFieldForT = new TextField();
        textFieldForT.setPromptText("Less than 2*pi: " + (2 * Math.PI));

        Button buttonSolve = new Button("Solve");
        Button buttonSetN = new Button("Set N");
        Button buttonSetT = new Button("Set T");
        Button buttonPlot = new Button("Show plot");
        buttonPlot.setDisable(true);
        buttonSolve.setDisable(true);

        ToggleGroup groupMethods = new ToggleGroup();
        RadioButton radioButtonExpl = new RadioButton("Explicit method");
        radioButtonExpl.setToggleGroup(groupMethods);
        RadioButton radioButtonImpl = new RadioButton("Implicit method");
        radioButtonImpl.setToggleGroup(groupMethods);
        RadioButton radioButtonCN = new RadioButton("Crank-Nicolson method");
        radioButtonCN.setToggleGroup(groupMethods);

        ToggleGroup groupApr = new ToggleGroup();
        RadioButton radioButtonApr1 = new RadioButton("Approximation accuracy: 1, points: 2");
        radioButtonApr1.setToggleGroup(groupApr);
        RadioButton radioButtonApr2 = new RadioButton("Approximation accuracy: 2, points: 3");
        radioButtonApr2.setToggleGroup(groupApr);
        RadioButton radioButtonApr3 = new RadioButton("Approximation accuracy: 2, points: 2");
        radioButtonApr3.setToggleGroup(groupApr);

        Slider sliderTime = new Slider();
        sliderTime.setVisible(true);
        sliderTime.setMin(0);
        sliderTime.setMax(100);
        sliderTime.setValue(0);
        sliderTime.setShowTickLabels(true);
        sliderTime.setShowTickMarks(true);
        sliderTime.setSnapToTicks(true);
        int amointOfTicks = 20; // if K < 20 => amountOfTicks = K
        int m = (int) Math.ceil(lastK / amointOfTicks);
        double step = m * lastTau;
        sliderTime.setMinorTickCount(5);
        sliderTime.setBlockIncrement(step);

//        sliderTime.setMajorTickUnit(lastT / 5); // каждое пятое значение
        sliderTime.setMinorTickCount(5);
//        sliderTime.setBlockIncrement(step);
        final Label labelTimeValue = new Label(Double.toString(sliderTime.getValue()));
        labelTimeValue.setVisible(false);

        CheckBox checkBoxTable = new CheckBox("Show table");
/*        checkBoxTable.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov,
                                Boolean old_val, Boolean new_val) {
            }
        });*/

        sliderTime.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                labelTimeValue.setText(String.format("%.6f", new_val));
            }
        });



        buttonSetN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if ((textFieldForN.getText() != null && !textFieldForN.getText().isEmpty())) {
                    nSelected[0] = true;
                    if (tSelected[0]) {
                        buttonSolve.setDisable(false);
                    }
                } else {
                    System.out.println("You have not entered N");
                }
            }
        });

        buttonSetT.setOnAction(e -> {
            if ((textFieldForT.getText() != null && !textFieldForT.getText().isEmpty())) {
                tSelected[0] = true;
                if (nSelected[0]) {
                    buttonSolve.setDisable(false);
                }
                lastT = Double.parseDouble(textFieldForT.getText());

            } else {
                System.out.println("You have not entered T");
            }
        });

        groupMethods.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle old_toggle, Toggle new_toggle) {
                buttonPlot.setDisable(true);
                if (groupMethods.getSelectedToggle() != null) {
                    if (groupMethods.getSelectedToggle() == radioButtonExpl) {
                        System.out.println("\n\n~~~ Explicit Finite Difference Method  ~~~");
                        methodSelected[0] = 1;
                    } else if (groupMethods.getSelectedToggle() == radioButtonImpl) {
                        System.out.println("\n\n~~~ Implicit Finite Difference Method  ~~~");
                        methodSelected[0] = 2;
                    } else if (groupMethods.getSelectedToggle() == radioButtonCN) {
                        System.out.println("\n\n~~~ Crank-Nicolson Method  ~~~");
                        methodSelected[0] = 3;
                    }
                }
            }
        });

        groupApr.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle old_toggle, Toggle new_toggle) {
                buttonPlot.setDisable(true);
                if (groupApr.getSelectedToggle() != null) {
                    if (groupApr.getSelectedToggle() == radioButtonApr1) {
//                        System.out.println("You choose Approximation accuracy: 1, points: 2");
                        aprSelected[0] = 1;
                    } else if (groupApr.getSelectedToggle() == radioButtonApr2) {
//                        System.out.println("You choose Approximation accuracy: 2, points: 3");
                        aprSelected[0] = 2;
                    } else if (groupApr.getSelectedToggle() == radioButtonApr3) {
//                        System.out.println("You choose Approximation accuracy: 2, points: 2");
                        aprSelected[0] = 3;
                    }
                }
            }
        });

        buttonSolve.setOnAction(event -> {
            if (nSelected[0] && tSelected[0] && methodSelected[0] != 0 && aprSelected[0] != 0) {
                Integer nn = Integer.parseInt(textFieldForN.getText());
                Double tt = Double.parseDouble(textFieldForT.getText());
                lastT = tt;
//                sliderTime.setMax(tt);
                if (methodSelected[0] == 1) {
//                    System.out.println("Explicit");
                    ExplicitFiniteDifferenceMethod efdm = new ExplicitFiniteDifferenceMethod(nn, tt);
                    efdm.solve(aprSelected[0]);
                    lastSolution = efdm.getFullSolution();
                    lastK = efdm.getK();
                    lastTau = efdm.getTau();
                    lastH = efdm.getH();
                } else if (methodSelected[0] == 2) {
//                    System.out.println("Implicit");
                    ImplicitFiniteDifferenceMethod ifdm = new ImplicitFiniteDifferenceMethod(nn, tt);
                    ifdm.solve(aprSelected[0]);
                    lastSolution = ifdm.getFullSolution();
                    lastK = ifdm.getK();
                    lastTau = ifdm.getTau();
                    lastH = ifdm.getH();
                } else if (methodSelected[0] == 3) {
//                    System.out.println("Crank Nicolson");
                    CrankNicolsonMethod cnm = new CrankNicolsonMethod(nn, tt);
                    cnm.solve(aprSelected[0]);
                    lastSolution = cnm.getFullSolution();
                    lastK = cnm.getK();
                    lastTau = cnm.getTau();
                    lastH = cnm.getH();
                }
//                System.out.println("N: " + nn + ", T: " + tt);
                buttonPlot.setDisable(false);
                double mm = (int) Math.ceil(lastK / amointOfTicks);
                double sstep = mm * lastTau;
                sliderTime.setMax(lastT);
//                System.out.println("m = " + mm + ", lastK = " + lastK + ", step = " + sstep
//                        + ", lastTau = " + lastTau + ", lastH = " + lastH);

                sliderTime.setMajorTickUnit(sstep * 5.0);
                sliderTime.setMinorTickCount(4);
                sliderTime.setBlockIncrement(sstep);
                sliderTime.setVisible(true);
                labelTimeValue.setVisible(true);
                if (checkBoxTable.isSelected()) {
                    printMap(lastSolution);
                }
            } else {
                System.out.println("You have not choose all parameters");
            }
        });

        buttonPlot.setOnAction(event -> {
            double thisTime = sliderTime.getValue();
            int rounded = (int) (thisTime * 10000);
            double newTime = ((double) rounded) / 10000.0;
            Double[][] thisSolution = lastSolution.get(thisTime);

            Plot plt = Plot.create();
            Double[] arrayRealY = new Double[thisSolution[0].length];
            for (int i = 0; i < thisSolution[0].length; i++) {
                arrayRealY[i] = analyticSolution.apply(thisSolution[0][i], thisTime);
            }

            plt.plot()
                    .add(Arrays.asList(thisSolution[0]))
                    .add(Arrays.asList(thisSolution[1]))
                    .label("u(x," + newTime + ")");
            plt.plot()
                    .add(Arrays.asList(thisSolution[0]))
                    .add(Arrays.asList(arrayRealY))
                    .label("real u(x," + newTime + ")");
            plt.xlabel("x");
            plt.ylabel("u(x,t)");
//            plt.text(0.5, 0.2, "ratata");

            plt.title("Parabolic");
            plt.legend();
            try {
                plt.show();
            } catch (IOException | PythonExecutionException e) {
                e.printStackTrace();
            }
        });

        GridPane gridPane = new GridPane();
        gridPane.setMinSize(600, 400);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(5);
        gridPane.setHgap(8);
        gridPane.setAlignment(Pos.CENTER);

        gridPane.add(textChooseMethod, 0, 0);
        gridPane.add(radioButtonExpl, 0, 1);
        gridPane.add(radioButtonImpl, 0, 2);
        gridPane.add(radioButtonCN, 0, 3);

        gridPane.add(textChooseApr, 1, 0);
        gridPane.add(radioButtonApr1, 1, 1);
        gridPane.add(radioButtonApr2, 1, 2);
        gridPane.add(radioButtonApr3, 1, 3);

        gridPane.add(textEnterN, 0, 5);
        gridPane.add(textFieldForN, 1, 5);
        gridPane.add(buttonSetN, 2, 5);
        gridPane.add(textEnterT, 0, 6);
        gridPane.add(textFieldForT, 1, 6);
        gridPane.add(buttonSetT, 2, 6);

        gridPane.add(buttonSolve, 1, 8);
        gridPane.add(checkBoxTable, 2, 8);
        gridPane.add(buttonPlot, 1, 11);
        gridPane.add(sliderTime, 0, 10, 3, 1);
        gridPane.add(labelTimeValue, 4, 10);

        Scene scene = new Scene(gridPane);
        stage.setTitle("Parabolic methods");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
