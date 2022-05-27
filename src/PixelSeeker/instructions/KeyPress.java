package PixelSeeker.instructions;

import PixelSeeker.exceptions.IncorrectParameterNumberException;
import PixelSeeker.expressions.Expression;

import java.awt.*;

public class KeyPress extends Instruction {
    private int lengthMili = 100;
    private int key = -1;
    private Robot robot;
    final static String identifier = "keypress";
    public KeyPress(Expression param[]) throws java.awt.AWTException, IncorrectParameterNumberException {
        super(param,2, null,"KeyPress");
        this.robot = new Robot();
    }
    public void execute() {
        robot.keyPress(key);
        try{ Thread.sleep(lengthMili); } catch (java.lang.InterruptedException e) { e.printStackTrace(); System.out.println(e.toString()); }
        robot.keyRelease(key);
    }
}
