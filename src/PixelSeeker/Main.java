package PixelSeeker;

import PixelSeeker.storage.Context;
import PixelSeeker.exceptions.*;
import PixelSeeker.expressions.Expression;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    static List<String> input;
    static int ln = 0;
    static String commentIdentifier = "//";
    static InstructionSet main = new InstructionSet();

    static InstructionSet read(String level, Context context) throws ExpressionExtractionFailureException, java.awt.AWTException, IncorrectParametersException, InstructionSyntaxException {
        Expression expression;
        Instruction instruction;
        InstructionSet instructionSet = new InstructionSet();
        String firstArgument, rawExpression;
        int splitter;
        while(input.size() > ln){
            if(input.get(ln).startsWith(commentIdentifier) || input.get(ln).trim().isEmpty()){
                ln++;
                continue;
            }
            if(!input.get(ln).startsWith(level) && !level.isEmpty())
                return instructionSet;
            splitter = input.get(ln).indexOf(' ');
            firstArgument = splitter != -1 ? input.get(ln).substring(level.length(), splitter).trim() : "";
            rawExpression = splitter != -1 ? input.get(ln).substring(splitter) : input.get(ln);
            expression = new Expression(rawExpression.isEmpty() ? null : rawExpression, context);
            ln++;
            InstructionSet codeBlock = read(level + "\t", context);
            instruction = InstructionHandler.retrieve(firstArgument, expression, codeBlock, context);
            instructionSet.add(instruction);
        }
        return instructionSet;
    }
    public static void main(String[] args) {
        /*if(args.length < 1){
            System.out.println("Provided empty path");
            return;
        }
        String p = args[0];*/
        String p = "C:\\WorkingDirectory\\fisier.pskr";
        if(!p.endsWith(".pskr")){
            System.out.println("Invalid file type. File must be of type \".pskr\".");
            return;
        }
        try {
            input = Files.readAllLines(Paths.get(p), StandardCharsets.UTF_8);
        }catch (java.io.IOException e){
            e.printStackTrace();
        }
        try {
            main = read("", new Context());

        }catch (Exception e){
            System.out.println("Error on line " + (ln+1));
            e.printStackTrace();
        }
        try {
            main.execute();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
