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
            System.out.println("Fata error cause by initialization of Java.awt.Robot class.");
            System.exit(1);
        }
    }
    public KeyPress(Expression param, NameManagement context) throws java.awt.AWTException, InstructionSyntaxException {
        super(param,2, null, false, context);

    }
    public Element execute() throws ExpressionExtractionFailureException, IncorrectParametersException {
        extract();
        if(!param.getElement(0).isNum() || !param.getElement(1).isNum())
            throw new IncorrectParametersException("Supplied incorrect type of expression. Required: Num");
        robot.keyPress(((NumericalElement)param.getElement(0)).get());
        try{ Thread.sleep(((NumericalElement)param.getElement(1)).get()); } catch (java.lang.InterruptedException e) { e.printStackTrace(); System.out.println(e.toString()); }
        robot.keyRelease(((NumericalElement)param.getElement(0)).get());
        return null;
    }
}
