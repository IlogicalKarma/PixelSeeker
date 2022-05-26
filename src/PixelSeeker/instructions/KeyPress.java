package PixelSeeker.instructions;

import PixelSeeker.expressions.Expression;
import PixelSeeker.instructions.ActionType;

import java.awt.*;

public class KeyPress extends ActionType {
    private int lengthMili;
    private int key = -1;
    private Robot robot;
    public KeyPress() throws java.awt.AWTException{
        this.lengthMili = 100;
        robot = new Robot();
    }
    public KeyPress(Expression key) throws java.awt.AWTException{
        this(key.getValue(),100);
    }
    KeyPress(int key, int lengthMili) throws java.awt.AWTException{
        this.key = key;
        this.lengthMili = lengthMili;
        robot = new Robot();
    }
    @Override
    public void use() {
        robot.keyPress(key);
        try{ Thread.sleep(lengthMili); } catch (java.lang.InterruptedException e) { e.printStackTrace(); System.out.println(e.toString()); }
        robot.keyRelease(key);
    }
    public void edit(int key, int lengthMili){
        this.key = key;
        this.lengthMili = lengthMili;
    }
}
