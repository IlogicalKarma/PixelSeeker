package PixelSeeker.instructions;

import PixelSeeker.exceptions.IncorrectParameterNumberException;
import PixelSeeker.expressions.Expression;

public abstract class Instruction {
    Expression param[];
    InstructionSet instructionSet;
    static String type;
    static String identifier;
    int paramNr = 0;
    public Instruction(Expression param[], int paramNr, InstructionSet instructionSet, String type) throws IncorrectParameterNumberException {
        this.paramNr = paramNr;
        if(param.length != paramNr && paramNr != -1)
            throw new IncorrectParameterNumberException(paramNr, param.length);
        this.instructionSet = instructionSet;
        this.param = param;
        this.type = type;
    }
    public abstract void execute();
}
//not needed