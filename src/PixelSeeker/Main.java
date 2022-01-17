package PixelSeeker;

import PixelSeeker.expressions.CheckHandler;
import PixelSeeker.expressions.Expression;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.util.stream.Stream;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("GUI.fxml"));
        primaryStage.setTitle("Hello World");
        Scene scene = new Scene(root, 300,275);

        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
        String string = new String("  ");
        System.out.println("" + string.trim().equals("") + string.equals(""));
        Expression expression = new Expression("(false = alwaysTrue) + (1) = (0 + 1)");
        System.out.println(expression);
    }


    public static void main(String[] args) throws Exception {
        File source = new File(args[0]);
        String name = source.getName();
        if(!source.canRead() || !name.substring(name.length()-6).equals(".pskr"))
            throw new Exception("Invalid directory: " + source.getAbsolutePath());
        String sourceCode = source.toString();
        String parameter = new String();
        for(int i = 0; i < sourceCode.length(); i++){
            if(sourceCode.charAt(i) == ' ')
            parameter += sourceCode.charAt(i);
        }
    }

    private static void decisionTree(String )
}
