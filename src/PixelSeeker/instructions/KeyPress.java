package PixelSeeker.instructions;

import PixelSeeker.DataStorage.Element;
import PixelSeeker.DataStorage.NameManagement;
import PixelSeeker.DataStorage.NumericalElement;
import PixelSeeker.exceptions.ExpressionExtractionFailureException;
import PixelSeeker.exceptions.IncorrectParametersException;
import PixelSeeker.exceptions.InstructionSyntaxException;
import PixelSeeker.expressions.Expression;

import java.awt.Robot;

public class KeyPress extends Instruction {
    private static Robot robot;
    static String type = "KeyPress";
    final static String identifier = "keypress";
    static {
        try {
            robot = new Robot();
        }catch (Exception e){
            System.out.println("Fatal error cause by Java.awt.Robot class.");   //take a look at this later on
            System.exit(1);
        }
    }
    public KeyPress(Expression paramExpression, NameManagement context) throws java.awt.AWTException, InstructionSyntaxException {
        super(paramExpression,2, null, false, context);

    }
    public Element execute() throws ExpressionExtractionFailureException, IncorrectParametersException {
        extract();
        if(!param.get(0).isNum() || !param.get(1).isNum())
            throw new IncorrectParametersException("Supplied incorrect type of expression. Required: Num");
        robot.keyPress(((NumericalElement)param.get(0)).get());
        try{ Thread.sleep(((NumericalElement)param.get(1)).get()); } catch (java.lang.InterruptedException e) { e.printStackTrace(); System.out.println(e.toString()); }
        robot.keyRelease(((NumericalElement)param.get(0)).get());
        return null;
    }
}
