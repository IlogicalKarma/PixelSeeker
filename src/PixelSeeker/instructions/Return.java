package PixelSeeker.instructions;

import PixelSeeker.DataStorage.Element;
import PixelSeeker.DataStorage.NameManagement;
import PixelSeeker.exceptions.ExpressionExtractionFailureException;
import PixelSeeker.exceptions.IncorrectParametersException;
import PixelSeeker.exceptions.InstructionSyntaxException;
import PixelSeeker.exceptions.InvalidVariableNameException;
import PixelSeeker.expressions.Expression;

public class Return extends Instruction{
    final static String identifier = "return";
    static String type = "Return";
    public Return(Expression param, NameManagement context) throws InstructionSyntaxException {
        super(param, 1, null, false, context);
    }

    @Override
    public Element execute() throws ExpressionExtractionFailureException, InvalidVariableNameException, IncorrectParametersException {
        return param;
    }
}
