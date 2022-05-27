package PixelSeeker.instructions;

import PixelSeeker.exceptions.IncorrectParameterNumberException;
import PixelSeeker.exceptions.MissingInstructionSetException;
import PixelSeeker.expressions.Expression;

public class WhileStatement extends Instruction{
    final static String identifier = "while";
    public WhileStatement(Expression[] param, InstructionSet instructionSet) throws IncorrectParameterNumberException, MissingInstructionSetException {
        super(param, 1, instructionSet, "WhileStatement");
        if(instructionSet == null)
            throw new MissingInstructionSetException();
    }

    @Override
    public void execute() {
        while(param[0].getBool())
            instructionSet.execute();
    }
}
