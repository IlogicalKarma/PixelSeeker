package PixelSeeker.instructions;

import PixelSeeker.DataStorage.Data;
import PixelSeeker.DataStorage.Context;
import PixelSeeker.exceptions.ExpressionExtractionFailureException;
import PixelSeeker.exceptions.IncorrectParametersException;
import PixelSeeker.exceptions.InstructionSyntaxException;
import PixelSeeker.expressions.Expression;

public class Assign extends Instruction{
    static String type = "Assign";
    final static String identifier = "assign";
    public Assign(Expression paramExpression, Context context) throws InstructionSyntaxException {
        super(paramExpression,2, null, false,context);
    }
    public Data execute() throws ExpressionExtractionFailureException, IncorrectParametersException  {
        extract();
        if(param.get(0).isNamed())
            throw new IncorrectParametersException("Supplied incorrect type of expression(first parameter). Required: Var");
        param.get(0).name(param.get(1).getName());
        return null;
    }
}
