package PixelSeeker.instructions;

import PixelSeeker.DataStorage.NameManagement;
import PixelSeeker.exceptions.ExpressionExtractionFailureException;
import PixelSeeker.exceptions.IncorrectParametersException;
import PixelSeeker.exceptions.InstructionSyntaxException;
import PixelSeeker.expressions.Expression;

public class InstructionHandler{
    private InstructionHandler instructionHandler = new InstructionHandler();
    private InstructionHandler(){}

    public static Instruction retrieve(String string, Expression paramExpression, InstructionSet instructionSet, NameManagement context) throws InstructionSyntaxException, java.awt.AWTException{
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
    public static boolean canRetrieve(String string){
        switch (string.toLowerCase()){
            case If.identifier:
                return true;
            case While.identifier:
                return true;
            case KeyPress.identifier:
                return true;
            case Out.identifier:
                return true;
            case Assign.identifier:
                return true;
            default:
                return false;
        }
    }
}
