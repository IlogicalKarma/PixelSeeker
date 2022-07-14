package PixelSeeker.instructions;

import PixelSeeker.storage.Data;
import PixelSeeker.storage.Context;
import PixelSeeker.exceptions.ExpressionExtractionFailureException;
import PixelSeeker.exceptions.IncorrectParametersException;
import PixelSeeker.exceptions.InstructionSyntaxException;
import PixelSeeker.expressions.Expression;

public class Assign extends Instruction{
    public final static boolean requiresInstructionSet = false;
    public final static String identifier = "assign";
    Assign(Expression paramExpression, Context context) throws InstructionSyntaxException {
        super(paramExpression,2, null, false,context);
    }
    public Data execute() throws ExpressionExtractionFailureException, IncorrectParametersException  {
        extract();
        for(int i = 0; i < param.length; i++)
            param[i].copyTo(param[0]);
        return null;
    }
}
