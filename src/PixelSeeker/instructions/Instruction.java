package PixelSeeker.instructions;

import PixelSeeker.exceptions.ExpressionExtractionFailureException;
import PixelSeeker.exceptions.IncorrectParametersException;
import PixelSeeker.exceptions.InvalidVariableNameException;
import PixelSeeker.expressions.Expression;

public abstract class Instruction {
    Expression[] param;
    InstructionSet instructionSet;
    String  type;
    String identifier;
    int paramNr = 0;
    public Instruction(Expression[] param, int paramNr, InstructionSet instructionSet) throws IncorrectParametersException {
        this.paramNr = paramNr;
        if(param.length != paramNr && paramNr != -1)
            throw new IncorrectParametersException(paramNr, param.length);
        this.instructionSet = instructionSet;
        this.param = param;
    }
    public abstract void execute() throws ExpressionExtractionFailureException, InvalidVariableNameException, IncorrectParametersException;
}