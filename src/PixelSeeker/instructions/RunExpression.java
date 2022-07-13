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
    public RunExpression(Expression paramExpression, NameManagement context) throws InstructionSyntaxException, ExpressionExtractionFailureException {
        super(paramExpression,-1, null, false, context);
    }
    public Element execute() throws ExpressionExtractionFailureException, IncorrectParametersException {
        extract();
        return null;
    }
}
