package PixelSeeker.instructions;

import PixelSeeker.storage.Data;
import PixelSeeker.storage.Context;
import PixelSeeker.exceptions.ExpressionExtractionFailureException;
import PixelSeeker.exceptions.IncorrectParametersException;
import PixelSeeker.exceptions.InstructionSyntaxException;
import PixelSeeker.expressions.Expression;

public class RunExpression extends Instruction{
    public final static boolean requiresInstructionSet = false;
    public final static String identifier = null;
    RunExpression(Expression paramExpression, Context context) throws InstructionSyntaxException, ExpressionExtractionFailureException {
        super(paramExpression,-1, null, false, context);
    }
    public Data execute() throws ExpressionExtractionFailureException, IncorrectParametersException {
        extract();
        return null;
    }
}
