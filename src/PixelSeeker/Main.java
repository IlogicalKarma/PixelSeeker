package PixelSeeker;

import PixelSeeker.expressions.Expression;
import PixelSeeker.instructions.*;

import java.io.File;
import java.util.Scanner;

public class Main {
    static InstructionSet main = new InstructionSet();
    static InstructionSet read(File file) throws java.io.FileNotFoundException{
        Scanner scanner = new Scanner(file);
        String line;
        String[] split;
        if(!file.canRead())
            throw new java.io.FileNotFoundException();
        while(scanner.hasNextLine()){
            line = scanner.nextLine();
            split = line.split(" ");
            switch (split[0]){

            }
        }
        return new InstructionSet();
    }
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String p = scanner.next();
        read(new File(p));
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
