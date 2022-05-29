package PixelSeeker.instructions;

import PixelSeeker.exceptions.ExpressionExtractionFailureException;
import PixelSeeker.exceptions.IncorrectParametersException;
import PixelSeeker.exceptions.InvalidVariableNameException;
import PixelSeeker.exceptions.MissingInstructionSetException;
import PixelSeeker.expressions.Expression;

public class InstructionHandler{
    private InstructionHandler instructionHandler = new InstructionHandler();
    private InstructionHandler(){}
    InstructionHandler getInstance(){
        return instructionHandler;
    }
    public static Instruction retrieve(String string, Expression[] param, InstructionSet instructionSet) throws ExpressionExtractionFailureException, IncorrectParametersException, MissingInstructionSetException, java.awt.AWTException{
        switch (string.toLowerCase()){
            case IfStatement.identifier:
                return new IfStatement(param, instructionSet);
            case WhileStatement.identifier:
                return new WhileStatement(param, instructionSet);
            case KeyPress.identifier:
                return new KeyPress(param);
            case Out.identifier:
                return new Out(param);
            case Assign.identifier:
                return new Assign(param);
        }
        return null;
    }
}
