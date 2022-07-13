package PixelSeeker.instructions;

import PixelSeeker.DataStorage.Data;
import PixelSeeker.DataStorage.Context;
import PixelSeeker.exceptions.ExpressionExtractionFailureException;
import PixelSeeker.exceptions.IncorrectParametersException;
import PixelSeeker.exceptions.InstructionSyntaxException;
import PixelSeeker.expressions.Expression;

public class Out extends Instruction{
    final static String identifier = "out";
    static String type = "Out";
    public Out(Expression paramExpression, Context context) throws InstructionSyntaxException {
        super(paramExpression, -1, null, false, context);
    }
    @Override
    public Data execute() throws ExpressionExtractionFailureException, IncorrectParametersException {
        extract();
        if(param.size() == 0) {
            System.out.println("\n");
            return null;
        }
        System.out.print(param.toString());
        return null;
    }
}
