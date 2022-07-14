package PixelSeeker.instructions;

import PixelSeeker.storage.Context;
import PixelSeeker.exceptions.InstructionSyntaxException;
import PixelSeeker.expressions.Expression;

import java.security.Key;

public class InstructionHandler{
    private InstructionHandler instructionHandler = new InstructionHandler();
    private InstructionHandler(){}

    public static Instruction retrieve(String string, Expression paramExpression, InstructionSet instructionSet, Context context) throws InstructionSyntaxException, java.awt.AWTException{
        switch (string.toLowerCase()){
            case If.identifier:
                return new If(paramExpression, instructionSet, context);
            case While.identifier:
                return new While(paramExpression, instructionSet, context);
            case KeyPress.identifier:
                return new KeyPress(paramExpression, context);
            case Out.identifier:
                return new Out(paramExpression, context);
            case Assign.identifier:
                return new Assign(paramExpression, context);
            default:
                return null;
        }
    }
    public static Boolean preRetrieve(String string){
        switch (string.toLowerCase()){
            case If.identifier:
                return If.requiresInstructionSet;
            case While.identifier:
                return While.requiresInstructionSet;
            case KeyPress.identifier:
                return KeyPress.requiresInstructionSet;
            case Out.identifier:
                return Out.requiresInstructionSet;
            case Assign.identifier:
                return Assign.requiresInstructionSet;
            default:
                return null;
        }
    }
}
