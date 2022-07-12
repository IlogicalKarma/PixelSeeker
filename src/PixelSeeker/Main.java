package PixelSeeker;

import PixelSeeker.DataStorage.NameManagement;
import PixelSeeker.exceptions.*;
import PixelSeeker.expressions.Expression;
import PixelSeeker.instructions.*;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    static List<String> input;
    static int ln = 0;
    static String commentIdentifier = "//";
    static InstructionSet main = new InstructionSet();

    static InstructionSet read(String level) throws ExpressionExtractionFailureException, java.awt.AWTException, IncorrectParametersException, InstructionSyntaxException {
        NameManagement mainContext = new NameManagement();
        Expression expression;
        Instruction instruction;
        InstructionSet instructionSet = new InstructionSet();
        String firstArgument;
        int splitter;
        while(input.size() > ln){
            if(input.get(ln).startsWith(commentIdentifier) || input.get(ln).trim().isEmpty()){
                ln++;
                continue;
            }
            if(!input.get(ln).startsWith(level) && !level.isEmpty())
                return instructionSet;
            splitter = input.get(ln).indexOf(' ');
            firstArgument = splitter != -1 ? input.get(ln).substring(level.length(), input.get(ln).indexOf(' ')).trim() : null;
            if(firstArgument != null && InstructionHandler.canRetrieve(firstArgument)) {
                expression = new Expression(input.get(ln).substring(input.get(ln).indexOf(' ')), mainContext);
                ln++;
                InstructionSet codeBlock = read(level + "\t");
                instruction = InstructionHandler.retrieve(firstArgument, expression, codeBlock, mainContext);
            }else {
                instruction = new RunExpression(new Expression(input.get(ln), mainContext), mainContext);
                ln++;
            }
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
            input = Files.readAllLines(Paths.get(p), StandardCharsets.US_ASCII);
        }catch (java.io.IOException e){
            e.printStackTrace();
        }
        try {
            main = read("");

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
