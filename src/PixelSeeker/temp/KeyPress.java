package PixelSeeker.temp;

import PixelSeeker.Instruction;
import PixelSeeker.InstructionSet;
import PixelSeeker.storage.Data;
import PixelSeeker.storage.Context;
import PixelSeeker.exceptions.ExpressionExtractionFailureException;
import PixelSeeker.exceptions.IncorrectParametersException;
import PixelSeeker.exceptions.InstructionSyntaxException;
import PixelSeeker.expressions.Expression;

import java.awt.Robot;

public class KeyPress extends Instruction {
    private static Robot robot;
    public final static boolean requiresInstructionSet = false;
    final static String identifier = "keypress";
    static {
        try {
            robot = new Robot();
        }catch (Exception e){
            System.out.println("Fatal error cause by Java.awt.Robot class.");   //take a look at this later on
            System.exit(1);
        }
    }
    public KeyPress(Expression paramExpression, InstructionSet instructionSet, Context context) throws java.awt.AWTException, InstructionSyntaxException {
        super(paramExpression,2, null, false, context);

    }
    public Data execute() throws ExpressionExtractionFailureException, IncorrectParametersException {
        if(!param[0].isNum() || !param[1].isNum())
            throw new IncorrectParametersException("Supplied incorrect type of expression. Required: Num");
        robot.keyPress(param[0].toNum());
        try{ Thread.sleep(param[1].toNum()); } catch (java.lang.InterruptedException e) { e.printStackTrace(); System.out.println(e.toString()); }
        robot.keyRelease(param[0].toNum());
        return null;
    }
}
