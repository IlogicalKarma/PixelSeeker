package PixelSeeker.instructions;

import PixelSeeker.exceptions.IncorrectParameterNumberException;
import PixelSeeker.exceptions.MissingInstructionSetException;
import PixelSeeker.expressions.Expression;

import java.security.Key;
import java.util.HashMap;

public class InstructionHandler{
    private InstructionHandler instructionHandler = new InstructionHandler();
    private InstructionHandler(){}
    InstructionHandler getInstance(){
        return instructionHandler;
    }
    public static Instruction retrieve(String string, Expression param[], int paramNr, InstructionSet instructionSet, String type) throws IncorrectParameterNumberException, MissingInstructionSetException, java.awt.AWTException {
        switch (string){
            case "if":
                return new IfStatement(param, instructionSet);
            case "while":
                return new WhileStatement(param, instructionSet);
            case "keypress":
                return new KeyPress(param);
            case "out":
                return new Out(param);
        }
        return null;
    }
    public static String identify(String string) throws Exception{
        switch (string){
            case IfStatement.identifier:
                return IfStatement.type;
            case WhileStatement.identifier:
                return WhileStatement.type;
            case KeyPress.identifier:
                return KeyPress.type;
            case Out.identifier:
                return Out.type;
        }
        return null;
    }
}
