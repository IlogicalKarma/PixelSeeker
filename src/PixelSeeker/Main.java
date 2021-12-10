package PixelSeeker;

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
        //System.out.println(string.substring(0));
        //Expression expression = new Expression("(false = false + 3) = 4");
        //System.out.println(expression.extract());
        Expression expression = new Expression("(false = true) + (1)");
        System.out.println(expression);
        //InstructionSet instructionSet = (Class.forName("InstructionSet")) object;
        //instructionSet.
    }


    public static void main(String[] args) {
        launch(args);

    }
}
