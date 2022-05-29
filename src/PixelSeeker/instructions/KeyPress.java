package PixelSeeker.instructions;

import PixelSeeker.Parcel;
import PixelSeeker.exceptions.ExpressionExtractionFailureException;
import PixelSeeker.exceptions.IncorrectParametersException;
import PixelSeeker.expressions.Expression;

import java.awt.*;

public class KeyPress extends Instruction {
    private Robot robot;
    static String type = "KeyPress";
    final static String identifier = "keypress";
    public KeyPress(Expression[] param) throws java.awt.AWTException, IncorrectParametersException {
        super(param,2, null);
        this.robot = new Robot();
    }
    public void execute() throws ExpressionExtractionFailureException, IncorrectParametersException {
        param[0].extract();
        Parcel p0 = param[0].getParcel();
        if(p0.getClassObject().equals(Var.class))
            p0 = ((Var)param[0].getParcel().getObject()).getValue();
        param[1].extract();
        Parcel p1 = param[1].getParcel();
        if(p1.getClassObject().equals(Var.class))
            p1 = ((Var)param[1].getParcel().getObject()).getValue();
        if(!p0.getClassObject().equals(Integer.class) || !p1.getClassObject().equals(Integer.class))
            throw new IncorrectParametersException("Supplied incorrect type of expression. Required: Num");
        robot.keyPress((int)p0.getObject());
        try{ Thread.sleep((int)p0.getObject()); } catch (java.lang.InterruptedException e) { e.printStackTrace(); System.out.println(e.toString()); }
        robot.keyRelease((int)p0.getObject());
    }
}
