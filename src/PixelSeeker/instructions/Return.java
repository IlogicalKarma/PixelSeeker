package PixelSeeker.instructions;

import PixelSeeker.storage.Data;
import PixelSeeker.storage.Context;
import PixelSeeker.exceptions.ExpressionExtractionFailureException;
import PixelSeeker.exceptions.IncorrectParametersException;
import PixelSeeker.exceptions.InstructionSyntaxException;
import PixelSeeker.exceptions.InvalidVariableNameException;
import PixelSeeker.expressions.Expression;

public class Return extends Instruction{
    final static String identifier = "return";
    static String type = "Return";
    public Return(Expression paramExpression, Context context) throws InstructionSyntaxException, ExpressionExtractionFailureException {
        super(paramExpression, -1, null, false, context);
    }

    @Override
    public Data execute() throws ExpressionExtractionFailureException, InvalidVariableNameException, IncorrectParametersException {
        extract();
        return paramData;
    }
}
