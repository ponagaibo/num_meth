package lab7;

import javafx.application.Application;
import javafx.stage.Stage;

public class Preprocessor extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        LiebmannMethod lm = new LiebmannMethod(0, 0,0);
        lm.solve(3, 2);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
