package lab7;

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
import lab5.CrankNicolsonMethod;
import lab5.ImplicitFiniteDifferenceMethod;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import static lab7.Lab7.printStat;

public class Preprocessor7 extends Application {
    ArrayList<ArrayList<Double>> lastSolution;
    double lastHx;
    double lastHy;
    double relax;

    Lab7.Function2<Double, Double, Double> analyticSolution = (x, y) -> x * Math.cos(y);
    @Override
    public void start(Stage stage) throws Exception {
        final int[] methodSelected = {0};
        final boolean[] n1Selected = {false};
        final boolean[] n2Selected = {false};

        Text textChooseMethod = new Text("Choose method: ");
        Text textEnterN1 = new Text("Enter N1 (for x):");
        Text textEnterN2 = new Text("Enter N2 (for y):");
        Text textEnterW = new Text("Enter parameter for relaxation: ");

        TextField textFieldForN1 = new TextField();
        TextField textFieldForN2 = new TextField();
        TextField textFieldForW = new TextField();

        textEnterW.setDisable(true);
        textFieldForW.setDisable(true);

        Button buttonSolve = new Button("Solve");
        Button buttonPlot = new Button("Show plot");
        buttonPlot.setDisable(true);
        buttonSolve.setDisable(false);

        ToggleGroup groupMethods = new ToggleGroup();
        RadioButton radioButtonLieb = new RadioButton("Liebmann method");
        radioButtonLieb.setToggleGroup(groupMethods);
        RadioButton radioButtonSied = new RadioButton("Siedel method");
        radioButtonSied.setToggleGroup(groupMethods);
        RadioButton radioButtonRelax = new RadioButton("Relaxation method");
        radioButtonRelax.setToggleGroup(groupMethods);

        CheckBox checkBoxTable = new CheckBox("Show table");

        File file = new File("C:/Users/Anastasiya/Desktop/MAI/numMeth/num_meth_lab/src/main/java/lab7/var.jpg");

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

        textFieldForN1.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                buttonPlot.setDisable(true);
                textEnterW.setDisable(true);
                textFieldForW.setDisable(true);
            }
        });

        textFieldForN2.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                buttonPlot.setDisable(true);
                textEnterW.setDisable(true);
                textFieldForW.setDisable(true);
            }
        });

        textFieldForW.textProperty().addListener(new ChangeListener<String>() {
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
                    if (groupMethods.getSelectedToggle() == radioButtonLieb) {
                        System.out.println("\n\n~~~ Liebmann Method  ~~~");
                        methodSelected[0] = 1;
                    } else if (groupMethods.getSelectedToggle() == radioButtonSied) {
                        System.out.println("\n\n~~~ Siedel Method  ~~~");
                        methodSelected[0] = 2;
                    } else if (groupMethods.getSelectedToggle() == radioButtonRelax) {
                        System.out.println("\n\n~~~ Relaxation Method  ~~~");
                        methodSelected[0] = 3;
                        textEnterW.setDisable(false);
                        textFieldForW.setDisable(false);
                    }
                }
            }
        });

        buttonSolve.setOnAction(event -> {
            if ((textFieldForN1.getText() != null && !textFieldForN1.getText().isEmpty())) {
                n1Selected[0] = true;
                if (n2Selected[0]) {
                    buttonSolve.setDisable(false);
                }
            } else {
                System.out.println("You have not entered N1");
            }

            if ((textFieldForN2.getText() != null && !textFieldForN2.getText().isEmpty())) {
                n2Selected[0] = true;
                if (n1Selected[0]) {
                    buttonSolve.setDisable(false);
                }
            } else {
                System.out.println("You have not entered N2");
            }

            if (n1Selected[0] && n2Selected[0] && methodSelected[0] != 0) {
                Integer n1 = Integer.parseInt(textFieldForN1.getText());
                Integer n2 = Integer.parseInt(textFieldForN2.getText());
                LiebmannMethod lm = new LiebmannMethod(n1, n2);
                if (methodSelected[0] == 1) {
                    try {
                        lm.solve(methodSelected[0], 1);
                    } catch (IOException | PythonExecutionException e) {
                        e.printStackTrace();
                    }
                    lastSolution = lm.getFullSolution();
                    lastHx = lm.getHx();
                    lastHy = lm.getHy();
                } else if (methodSelected[0] == 2) {
                    try {
                        lm.solve(methodSelected[0], 1);
                    } catch (IOException | PythonExecutionException e) {
                        e.printStackTrace();
                    }
                    lastSolution = lm.getFullSolution();
                    lastHx = lm.getHx();
                    lastHy = lm.getHy();
                } else if (methodSelected[0] == 3) {
                    if ((textFieldForW.getText() != null && !textFieldForW.getText().isEmpty())) {
                        relax = Double.parseDouble(textFieldForW.getText());
//                        relax = 1.5;
                    } else {
                        System.out.println("You have not entered parameter W");
                    }
                    try {
                        lm.solve(methodSelected[0], relax);
                    } catch (IOException | PythonExecutionException e) {
                        e.printStackTrace();
                    }
                    lastSolution = lm.getFullSolution();
                    lastHx = lm.getHx();
                    lastHy = lm.getHy();
                }
                buttonPlot.setDisable(false);
                if (checkBoxTable.isSelected()) {
                    printStat(lastSolution, analyticSolution, lastHx, lastHy);
                }
            } else {
                System.out.println("You have not choose all parameters");
            }
        });

        buttonPlot.setOnAction(event -> {
            MyRunnableMethodPlot myRunnable = new MyRunnableMethodPlot(lastSolution, analyticSolution, lastHx, lastHy);
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
        gridPane.add(radioButtonLieb, 0, 2);
        gridPane.add(radioButtonSied, 0, 3);
        gridPane.add(radioButtonRelax, 0, 4);

        gridPane.add(textEnterW,0, 5);
        gridPane.add(textFieldForW, 1, 5);

        gridPane.add(textEnterN1, 0, 6);
        gridPane.add(textFieldForN1, 1, 6);
        gridPane.add(textEnterN2, 0, 7);
        gridPane.add(textFieldForN2, 1, 7);

        gridPane.add(checkBoxTable, 0, 8);
        gridPane.add(buttonSolve, 1, 8);
        gridPane.add(buttonPlot, 1, 9);

        Scene scene = new Scene(gridPane);
        stage.setTitle("Parabolic methods");
        stage.setScene(scene);
        stage.show();


//        LiebmannMethod lm = new LiebmannMethod(0, 0);
//        lm.solve(3, 1.2);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
