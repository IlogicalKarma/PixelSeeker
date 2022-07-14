package PixelSeeker.instructions;

import PixelSeeker.storage.Data;
import PixelSeeker.storage.Context;
import PixelSeeker.exceptions.ExpressionExtractionFailureException;
import PixelSeeker.exceptions.IncorrectParametersException;
import PixelSeeker.exceptions.InstructionSyntaxException;
import PixelSeeker.expressions.Expression;

public class Out extends Instruction{
    public final static String identifier = "out";
    public final static boolean requiresInstructionSet = false;
    Out(Expression paramExpression, Context context) throws InstructionSyntaxException {
        super(paramExpression, -1, null, false, context);
    }
    @Override
    public Data execute() throws ExpressionExtractionFailureException, IncorrectParametersException {
        extract();
        if(param.length == 0) {
            System.out.println("\n");
            return null;
        }
        System.out.print(paramData.toUserString());
        return null;
    }
}
