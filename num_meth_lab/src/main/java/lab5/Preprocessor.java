package lab5;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.Map;

import static lab5.Lab5.printMap;

// all methods and approximations - RadioButton (one from whole group)
// later may be CheckBox (yes or no every methods) to draw several plots

// MouseEvent clicked for button Solve and RadioButtons

// radioButton.isSelected to check variant
// group.getSelectedToggle() returns choosen button

public class Preprocessor extends Application {
    Map<Double, Double[][]> lastSolution;

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
        textFieldForN.setPromptText("Less than 20 for tridiagonal matrix");
        TextField textFieldForT = new TextField();
        textFieldForT.setPromptText("Less than 2*pi: " + (2 * Math.PI));

        Button buttonSolve = new Button("Solve");
        Button buttonSetN = new Button("Set N");
        Button buttonSetT = new Button("Set T");
        Button buttonPlot = new Button("Show plot");

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

        buttonSetN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if ((textFieldForN.getText() != null && !textFieldForN.getText().isEmpty())) {
                    nSelected[0] = true;
                } else {
                    System.out.println("You have not entered N");
                }
            }
        });

        buttonSetT.setOnAction(e -> {
            if ((textFieldForT.getText() != null && !textFieldForT.getText().isEmpty())) {
                tSelected[0] = true;
            } else {
                System.out.println("You have not entered T");
            }
        });

        groupMethods.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle old_toggle, Toggle new_toggle) {
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
                Lab5 lab5 = new Lab5();
                if (methodSelected[0] == 1) {
//                    System.out.println("Explicit");
                    lastSolution = lab5.lab5_efdm(nn, tt, aprSelected[0]);
                } else if (methodSelected[0] == 2) {
//                    System.out.println("Implicit");
                    try {
                        lastSolution = lab5.lab5_ifdm(nn, tt, aprSelected[0]);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                } else if (methodSelected[0] == 3) {
//                    System.out.println("Crank Nicolson");
                    try {
                        lastSolution = lab5.lab5_cnm(nn, tt, aprSelected[0]);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("N: " + nn + ", T: " + tt);
            } else {
                System.out.println("You have not choose all parameters");
            }
        });

        buttonPlot.setOnAction(event -> {
            System.out.println("Here the answer");
            printMap(lastSolution);
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
        gridPane.add(buttonPlot, 1, 9);

        Scene scene = new Scene(gridPane);
        stage.setTitle("Parabolic methods");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
