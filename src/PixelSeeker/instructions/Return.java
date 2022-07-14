package PixelSeeker.instructions;

import PixelSeeker.storage.Data;
import PixelSeeker.storage.Context;
import PixelSeeker.exceptions.ExpressionExtractionFailureException;
import PixelSeeker.exceptions.IncorrectParametersException;
import PixelSeeker.exceptions.InstructionSyntaxException;
import PixelSeeker.exceptions.InvalidVariableNameException;
import PixelSeeker.expressions.Expression;

public class Return extends Instruction{
    public final static String identifier = "return";
    public final static boolean requiresInstructionSet = false;
    Return(Expression paramExpression, Context context) throws InstructionSyntaxException {
        super(paramExpression, -1, null, false, context);
    }

    @Override
    public Data execute() throws ExpressionExtractionFailureException, InvalidVariableNameException, IncorrectParametersException {
        extract();
        return paramData;
    }
}
