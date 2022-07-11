package PixelSeeker.instructions;

import PixelSeeker.DataStorage.Element;
import PixelSeeker.DataStorage.NameManagement;
import PixelSeeker.exceptions.ExpressionExtractionFailureException;
import PixelSeeker.exceptions.IncorrectParametersException;
import PixelSeeker.exceptions.InstructionSyntaxException;
import PixelSeeker.expressions.Expression;

public class RunExpression extends Instruction{
    static String type = "RunExpression";
    final static String identifier = "assign";
    public RunExpression(Expression param, NameManagement context) throws InstructionSyntaxException {
        super(param,-1, null, false, context);
    }
    public Element execute() throws ExpressionExtractionFailureException, IncorrectParametersException {
        extract();
        return null;
    }
}
