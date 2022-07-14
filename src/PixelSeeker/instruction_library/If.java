package PixelSeeker.instruction_library;

import PixelSeeker.Instruction;
import PixelSeeker.InstructionSet;
import PixelSeeker.exceptions.*;
import PixelSeeker.expressions.Expression;
import PixelSeeker.storage.Context;
import PixelSeeker.storage.Data;

public class If extends Instruction {
    public final static String identifier = "if";
    public final static boolean requiresInstructionSet = false;

    public If(Expression paramExpression, InstructionSet instructionSet, Context context) throws InstructionSyntaxException {
        super(paramExpression, 1, instructionSet, requiresInstructionSet, context);
    }

    @Override
    public Data execute() throws InvalidVariableNameException, ExpressionExtractionFailureException, IncorrectParametersException, RuntimeErrorException {
        Data r;
        if (paramData.toBool()) {
            r = instructionSet.execute();
            if (r != null)
                return r;
        }
        return null;
    }
}
