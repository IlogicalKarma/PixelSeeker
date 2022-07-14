package PixelSeeker.instruction_internal_library;

import PixelSeeker.Instruction;
import PixelSeeker.InstructionSet;
import PixelSeeker.exceptions.ExpressionExtractionFailureException;
import PixelSeeker.exceptions.IncorrectParametersException;
import PixelSeeker.exceptions.InstructionSyntaxException;
import PixelSeeker.expressions.Expression;
import PixelSeeker.storage.Context;
import PixelSeeker.storage.Data;

public class RunExpression extends Instruction {
    public final static boolean requiresInstructionSet = false;
    public final static String identifier = null;

    public RunExpression(Expression paramExpression, InstructionSet instructionSet, Context context) throws InstructionSyntaxException, ExpressionExtractionFailureException {
        super(paramExpression, -1, null, false, context);
    }

    public Data execute() throws ExpressionExtractionFailureException, IncorrectParametersException {
        return null;
    }
}
