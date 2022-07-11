package PixelSeeker.instructions;

import PixelSeeker.DataStorage.NameManagement;
import PixelSeeker.exceptions.ExpressionExtractionFailureException;
import PixelSeeker.exceptions.IncorrectParametersException;
import PixelSeeker.exceptions.InstructionSyntaxException;
import PixelSeeker.expressions.Expression;

public class InstructionHandler{
    private InstructionHandler instructionHandler = new InstructionHandler();
    private InstructionHandler(){}
    /*InstructionHandler getInstance(){
        return instructionHandler;
    }*/
    public static Instruction retrieve(String string, Expression param, InstructionSet instructionSet, NameManagement context) throws ExpressionExtractionFailureException, IncorrectParametersException, InstructionSyntaxException, java.awt.AWTException{
        switch (string.toLowerCase()){
            case If.identifier:
                return new If(param, instructionSet, context);
            case While.identifier:
                return new While(param, instructionSet, context);
            case KeyPress.identifier:
                return new KeyPress(param, context);
            case Out.identifier:
                return new Out(param, context);
            case Assign.identifier:
                return new Assign(param, context);
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
