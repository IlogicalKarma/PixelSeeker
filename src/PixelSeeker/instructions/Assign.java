package PixelSeeker.instructions;

import PixelSeeker.DataStorage.Element;
import PixelSeeker.DataStorage.NameManagement;
import PixelSeeker.exceptions.ExpressionExtractionFailureException;
import PixelSeeker.exceptions.IncorrectParametersException;
import PixelSeeker.exceptions.InstructionSyntaxException;
import PixelSeeker.expressions.Expression;

import javax.naming.Name;

public class Assign extends Instruction{
    static String type = "Assign";
    final static String identifier = "assign";
    public Assign(Expression paramExpression, NameManagement context) throws InstructionSyntaxException {
        super(paramExpression,2, null, false,context);
    }
    public Element execute() throws ExpressionExtractionFailureException, IncorrectParametersException  {
        extract();
        if(param.get(0).isNamed())
            throw new IncorrectParametersException("Supplied incorrect type of expression(first parameter). Required: Var");
        param.get(0).name(param.get(1).getName());
        return null;
    }
}
