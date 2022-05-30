package PixelSeeker;

import PixelSeeker.exceptions.*;
import PixelSeeker.expressions.Expression;
import PixelSeeker.instructions.*;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Main {
    static List<String> input;
    static int ln = 0;
    static String commentIdentifier = "//";
    static InstructionSet main = new InstructionSet();
    static InstructionSet read(String level) throws InvalidVariableNameException, ExpressionExtractionFailureException, java.awt.AWTException, IncorrectParametersException, MissingInstructionSetException  {
        String[] split;
        InstructionSet instructionSet = new InstructionSet();
        while(input.size() > ln){
            if(input.get(ln).startsWith(commentIdentifier) || input.get(ln).trim().isEmpty()){
                ln++;
                continue;
            }

            split = input.get(ln).split(",");
            if(!input.get(ln).startsWith(level) && level.length() != 0)
                return instructionSet;
            ln++;
            Expression[] param = new Expression[split.length-1];
            for(int i = 0; i < split.length-1; i++)
                param[i] = new Expression(split[i+1]);
            InstructionSet codeBlock = read(level + "\t");
            instructionSet.add(InstructionHandler.retrieve(split[0].trim(), param, codeBlock));
        }
        return instructionSet;
    }
    public static void main(String[] args) {
        if(args.length < 1){
            System.out.println("Provided empty path");
            return;
        }
        String p = args[0];
        if(!p.endsWith(".pskr")){
            System.out.println("Invalid file type. File must be of type \".pskr\".");
            return;
        }
        try {
            input = Files.readAllLines(Paths.get(p), StandardCharsets.US_ASCII);
        }catch (java.io.IOException e){
            e.printStackTrace();
        }
        try {
            main = read("");

        }catch (Exception e){
            System.out.println("Error on line " + ln);
            e.printStackTrace();
        }
        try {
            main.execute();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
