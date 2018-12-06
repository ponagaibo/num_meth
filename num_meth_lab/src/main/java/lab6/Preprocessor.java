package lab6;

import com.github.sh0nk.matplotlib4j.PythonExecutionException;
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
import static lab6.Lab6.printStat;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;

public class Preprocessor extends Application {
    Map<Double, Double[][]> lastSolution;
    double lastK;
    double lastTau;
    double lastA;
    double lastH;
    double lastS;
    double maxTime = 3.0 * Math.PI;
    Lab6.Function2<Double, Double, Double> analyticSolution =
            (x, t) -> Math.sin(x - lastA * t) + Math.cos(x + lastA * t);

    @Override
    public void start(Stage stage) throws Exception {
        final int[] methodSelected = {0};
        final int[] aprXSelected = {0};
        final int[] aprTSelected = {0};
        final boolean[] nSelected = {false};
        final boolean[] aSelected = {false};
        final boolean[] sSelected = {false};

        Text textChooseMethod = new Text("Choose method: ");
        Text textChooseAprX = new Text("Choose space variable approximation");
        Text textChooseAprT = new Text("Choose time variable approximation");
        Text textEnterN = new Text("Enter N:");
        Text textEnterA = new Text("Enter a:");
        Text textEnterS = new Text("Enter sigma: ");

        TextField textFieldForN = new TextField();
        TextField textFieldForA = new TextField();
        TextField textFieldForS = new TextField();
        textFieldForS.setPromptText("< 1");

        Button buttonSolve = new Button("Solve");
        Button buttonPlot = new Button("Show plot");
        buttonPlot.setDisable(true);
        buttonSolve.setDisable(false);

        ToggleGroup groupMethods = new ToggleGroup();
        RadioButton radioButtonExpl = new RadioButton("Explicit cross method");
        radioButtonExpl.setToggleGroup(groupMethods);
        RadioButton radioButtonImpl = new RadioButton("Implicit method");
        radioButtonImpl.setToggleGroup(groupMethods);

        ToggleGroup groupAprX = new ToggleGroup();
        RadioButton radioButtonAprX1 = new RadioButton("Space variable approximation accuracy: 1, points: 2");
        radioButtonAprX1.setToggleGroup(groupAprX);
        RadioButton radioButtonAprX2 = new RadioButton("Space variable approximation accuracy: 2, points: 3");
        radioButtonAprX2.setToggleGroup(groupAprX);
        RadioButton radioButtonAprX3 = new RadioButton("Space variable approximation accuracy: 2, points: 2");
        radioButtonAprX3.setToggleGroup(groupAprX);

        ToggleGroup groupAprT = new ToggleGroup();
        RadioButton radioButtonAprT1 = new RadioButton("Time variable approximation accuracy: 1");
        radioButtonAprT1.setToggleGroup(groupAprT);
        RadioButton radioButtonAprT2 = new RadioButton("Time variable approximation accuracy: 2");
        radioButtonAprT2.setToggleGroup(groupAprT);

        Slider sliderTime = new Slider();
        sliderTime.setVisible(true);
        sliderTime.setMin(0);
        sliderTime.setMax(maxTime);
        sliderTime.setValue(0);
        sliderTime.setShowTickLabels(true);
        sliderTime.setShowTickMarks(true);
        sliderTime.setSnapToTicks(true);
        int amointOfTicks = 20;
        int m = (int) Math.ceil(lastK / amointOfTicks);
        double step = m * lastTau;
        sliderTime.setMinorTickCount(5);
        sliderTime.setBlockIncrement(step);
        sliderTime.setMinorTickCount(5);
        Label labelTimeValue = new Label("time = " + Double.toString(sliderTime.getValue()));
        labelTimeValue.setVisible(false);

        CheckBox checkBoxTable = new CheckBox("Show table");

        File file = new File("C:/Users/Anastasiya/Desktop/MAI/numMeth/num_meth_lab/src/main/java/lab6/var.jpg");
        String localUrl = null;
        try {
            localUrl = file.toURI().toURL().toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
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

        textFieldForN.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                buttonPlot.setDisable(true);
            }
        });

        textFieldForA.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                buttonPlot.setDisable(true);
            }
        });

        textFieldForS.textProperty().addListener(new ChangeListener<String>() {
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
                    if (groupMethods.getSelectedToggle() == radioButtonExpl) {
                        methodSelected[0] = 1;
                    } else if (groupMethods.getSelectedToggle() == radioButtonImpl) {
                        methodSelected[0] = 2;
                    }
                }
            }
        });

        groupAprX.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle old_toggle, Toggle new_toggle) {
                buttonPlot.setDisable(true);
                if (groupAprX.getSelectedToggle() != null) {
                    if (groupAprX.getSelectedToggle() == radioButtonAprX1) {
                        aprXSelected[0] = 1;
                    } else if (groupAprX.getSelectedToggle() == radioButtonAprX2) {
                        aprXSelected[0] = 2;
                    } else if (groupAprX.getSelectedToggle() == radioButtonAprX3) {
                        aprXSelected[0] = 3;
                    }
                }
            }
        });

        groupAprT.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle old_toggle, Toggle new_toggle) {
                buttonPlot.setDisable(true);
                if (groupAprT.getSelectedToggle() != null) {
                    if (groupAprT.getSelectedToggle() == radioButtonAprT1) {
                        aprTSelected[0] = 1;
                    } else if (groupAprT.getSelectedToggle() == radioButtonAprT2) {
                        aprTSelected[0] = 2;
                    }
                }
            }
        });

        buttonSolve.setOnAction(event -> {
            if ((textFieldForN.getText() != null && !textFieldForN.getText().isEmpty())) {
                nSelected[0] = true;
                if (nSelected[0] && aSelected[0] && sSelected[0]) {
                    buttonSolve.setDisable(false);
                }
            } else {
                System.out.println("You have not entered N");
            }

            if ((textFieldForA.getText() != null && !textFieldForA.getText().isEmpty())) {
                aSelected[0] = true;
                if (nSelected[0] && aSelected[0] && sSelected[0]) {
                    buttonSolve.setDisable(false);
                }
                lastA = Double.parseDouble(textFieldForA.getText());
            } else {
                System.out.println("You have not entered a");
            }

            if ((textFieldForS.getText() != null && !textFieldForS.getText().isEmpty())) {
                sSelected[0] = true;
                if (nSelected[0] && aSelected[0] && sSelected[0]) {
                    buttonSolve.setDisable(false);
                }
                lastS = Double.parseDouble(textFieldForS.getText());
            } else {
                System.out.println("You have not entered a");
            }

            if (nSelected[0] && aSelected[0] && sSelected[0]
                    && methodSelected[0] != 0 && aprXSelected[0] != 0 && aprTSelected[0] != 0) {
                Integer nn = Integer.parseInt(textFieldForN.getText());
                Double aa = Double.parseDouble(textFieldForA.getText());
                Double ss = Double.parseDouble(textFieldForS.getText());
                lastA = aa;
                if (methodSelected[0] == 1) {
                    ExplicitCrossMethod em = new ExplicitCrossMethod(nn, aa, ss);
                    try {
                        em.solve(aprXSelected[0], aprTSelected[0]);
                    } catch (IOException | PythonExecutionException e) {
                        e.printStackTrace();
                    }
                    lastSolution = em.getFullSolution();
                    lastK = em.getK();
                    lastTau = em.getTau();
                    lastH = em.getH();
                } else if (methodSelected[0] == 2) {
                    ImplicitMethod im = new ImplicitMethod(nn, aa, ss);
                    try {
                        im.solve(aprXSelected[0], aprTSelected[0]);
                    } catch (IOException | PythonExecutionException e) {
                        e.printStackTrace();
                    }
                    lastSolution = im.getFullSolution();
                    lastK = im.getK();
                    lastTau = im.getTau();
                    lastH = im.getH();
                }
                buttonPlot.setDisable(false);
                double mm = (int) Math.ceil(lastK / amointOfTicks);
                double sstep = mm * lastTau;
                sliderTime.setMax(maxTime);
                sliderTime.setMajorTickUnit(sstep * 5.0);
                sliderTime.setMinorTickCount(4);
                sliderTime.setBlockIncrement(sstep);
                sliderTime.setVisible(true);
                labelTimeValue.setVisible(true);
                if (checkBoxTable.isSelected()) {
                    printStat(lastSolution, analyticSolution, lastTau, lastH);
                }
            } else {
                System.out.println("You have not choose all parameters");
            }
        });

        buttonPlot.setOnAction(event -> {
            double thisTime = sliderTime.getValue();
            double timeNumber = Math.floor(thisTime / lastTau);
//            System.out.println("thisTime: " + thisTime + ", N: " + timeNumber);
            Double[][] thisSolution = lastSolution.get(timeNumber);
//            System.out.println("Solution of size " + thisSolution.length + " x " + thisSolution[0].length + ":");
/*            for (int i = 0; i < thisSolution.length; i++) {
                for (int j = 0; j < thisSolution[0].length; j++) {
                    System.out.println(thisSolution[i][j] + " ");
                }
                System.out.println();
            }*/

            Double[] arrayRealY = new Double[thisSolution[0].length];
            for (int i = 0; i < thisSolution[0].length; i++) {
                arrayRealY[i] = analyticSolution.apply(thisSolution[0][i], thisTime);
            }
            MyRunnableMethodPlot myRunnable = new MyRunnableMethodPlot(thisSolution, arrayRealY, thisTime);
            Thread t = new Thread(myRunnable);
            t.start();
        });

        GridPane gridPane = new GridPane();
        gridPane.setMinSize(600, 400);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(5);
        gridPane.setHgap(8);
        gridPane.setAlignment(Pos.CENTER);

        gridPane.add(imageView, 0, 0, 3, 1);

        gridPane.add(textChooseMethod, 0, 1);
        gridPane.add(radioButtonExpl, 0, 2);
        gridPane.add(radioButtonImpl, 0, 3);

        gridPane.add(textChooseAprX, 1, 1);
        gridPane.add(radioButtonAprX1, 1, 2);
        gridPane.add(radioButtonAprX2, 1, 3);
        gridPane.add(radioButtonAprX3, 1, 4);

        gridPane.add(textChooseAprT, 2, 1);
        gridPane.add(radioButtonAprT1, 2, 2);
        gridPane.add(radioButtonAprT2, 2, 3);

        gridPane.add(textEnterN, 0, 5);
        gridPane.add(textFieldForN, 1, 5);
        gridPane.add(textEnterA, 0, 6);
        gridPane.add(textFieldForA, 1, 6);
        gridPane.add(textEnterS, 0, 7);
        gridPane.add(textFieldForS, 1, 7);

        gridPane.add(checkBoxTable, 0, 9);
        gridPane.add(buttonSolve, 1, 9);
        gridPane.add(labelTimeValue, 2, 9);
        gridPane.add(sliderTime, 0, 10, 3, 1);
        gridPane.add(buttonPlot, 1, 11);

        Scene scene = new Scene(gridPane);
        stage.setTitle("Hyperbolic methods");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
