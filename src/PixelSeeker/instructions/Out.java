package PixelSeeker.instructions;

import PixelSeeker.DataStorage.Element;
import PixelSeeker.DataStorage.NameManagement;
import PixelSeeker.exceptions.ExpressionExtractionFailureException;
import PixelSeeker.exceptions.IncorrectParametersException;
import PixelSeeker.exceptions.InstructionSyntaxException;
import PixelSeeker.expressions.Expression;

public class Out extends Instruction{
    final static String identifier = "out";
    static String type = "Out";
    public Out(Expression paramExpression, NameManagement context) throws InstructionSyntaxException {
        super(paramExpression, -1, null, false, context);
    }
    @Override
    public Element execute() throws ExpressionExtractionFailureException, IncorrectParametersException {
        extract();
        if(param.size() == 0) {
            System.out.println("\n");
            return null;
        }
        System.out.print(param.toString());
        return null;
    }
}
