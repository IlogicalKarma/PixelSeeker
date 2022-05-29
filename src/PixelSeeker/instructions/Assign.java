package PixelSeeker.instructions;

import PixelSeeker.exceptions.ExpressionExtractionFailureException;
import PixelSeeker.exceptions.IncorrectParametersException;
import PixelSeeker.exceptions.InvalidVariableNameException;
import PixelSeeker.expressions.Expression;

public class Assign extends Instruction{
    static String type = "Assign";
    final static String identifier = "assign";
    public Assign(Expression param[]) throws IncorrectParametersException, ExpressionExtractionFailureException {
        super(param,2, null);
    }
    public void execute() throws ExpressionExtractionFailureException, IncorrectParametersException  {
        param[0].extract();
        if(!param[0].isVar())
            throw new IncorrectParametersException("Supplied incorrect type of expression(first parameter). Required: Var");
        param[1].extract();
        ((Var)param[0].getParcel().getObject()).setValue(param[1].getParcel());
    }
}
