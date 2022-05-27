package PixelSeeker.instructions;

import PixelSeeker.exceptions.IncorrectParameterNumberException;
import PixelSeeker.exceptions.MissingInstructionSetException;
import PixelSeeker.expressions.Expression;

public class IfStatement extends Instruction{
    final static String identifier = "if";
    public IfStatement(Expression[] param, InstructionSet instructionSet) throws IncorrectParameterNumberException, MissingInstructionSetException {

        super(param, 0, instructionSet, "IfStatement");
        if(instructionSet == null)
            throw new MissingInstructionSetException();
    }

    @Override
    public void execute() {
        if(param[0].getBool())
            instructionSet.execute();
    }
}
