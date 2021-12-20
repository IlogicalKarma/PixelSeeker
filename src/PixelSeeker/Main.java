package PixelSeeker;

import PixelSeeker.expressions.CheckHandler;
import PixelSeeker.expressions.Expression;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("GUI.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
        String string = new String("  ");
        System.out.println("" + string.trim().equals("") + string.equals(""));
        Expression expression = new Expression("(false = true) + (1) = (0 + 1)");
        System.out.println(expression);
        CheckHandler.True check = new CheckHandler.True();
        CheckHandler.True.
    }


    public static void main(String[] args) {
        launch(args);

    }
}
