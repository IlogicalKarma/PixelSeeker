package PixelSeeker;

import PixelSeeker.exceptions.ExpressionExtractionFailureException;
import PixelSeeker.exceptions.SyntaxException;
import PixelSeeker.expressions.Expression;
import PixelSeeker.instructions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    static String[] input = {"if   10","keypress 65"};
    static int ln = 0;
    static InstructionSet main = new InstructionSet();
    static InstructionSet read(String level) throws ExpressionExtractionFailureException, java.awt.AWTException, SyntaxException {
        String[] split;
        InstructionSet instructionSet = new InstructionSet();
        while(input.length > ln){
            if(!input[ln].startsWith(level))
                return instructionSet;
            split = input[ln].split(" ");
            System.out.println(split[0]);
            InstructionHandler.retrieve()
                switch (split[0].split(" ")[0]) {
                    case "if":
                        instructionSet.add(new IfStatement(new Expression(input[ln++].substring(2)), read(level+"   ")));
                        break;
                    case "keypress":
                        instructionSet.add(new KeyPress(new Expression(input[ln++].substring(8))));
                        break;
                    default:
                        throw new SyntaxException();
                }
        }
        return instructionSet;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String p = scanner.next();
        try {
            input = (new Scanner(new File(p))).toString().split("\n");
        }catch (FileNotFoundException e){
            e.printStackTrace();
            System.out.println(e);
        }
        try {
            main = read("");
        }catch (Exception e){
            System.out.println("Error on line " + (ln+1));
            e.printStackTrace();
            System.out.println(e);
        }
            /*
        String string = new String("  ");
        System.out.println("" + string.trim().equals("") + string.equals(""));
        Expression expression = new Expression("(false = True) + (1 )= (0 + 1) + 10");
        System.out.println(expression);
        KeyPress instruction = new KeyPress(65);
        main.add(instruction);
        IfStatement statement = new IfStatement(expression,main);
        main.add(statement);
        main.execute();
        */
    }
}
