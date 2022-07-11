package PixelSeeker.instructions;

import PixelSeeker.DataStorage.Element;
import PixelSeeker.DataStorage.NameManagement;
import PixelSeeker.DataStorage.NumericalElement;
import PixelSeeker.exceptions.ExpressionExtractionFailureException;
import PixelSeeker.exceptions.IncorrectParametersException;
import PixelSeeker.exceptions.InstructionSyntaxException;
import PixelSeeker.exceptions.RuntimeErrorException;
import PixelSeeker.expressions.Expression;

public class While extends Instruction{
    final static String identifier = "while";
    static String type = "WhileStatement";
    public While(Expression param, InstructionSet instructionSet, NameManagement context) throws IncorrectParametersException, InstructionSyntaxException {
        super(param, 1, instructionSet, true, context);
    }

    @Override
    public Element execute() throws PixelSeeker.exceptions.InvalidVariableNameException, ExpressionExtractionFailureException, IncorrectParametersException, RuntimeErrorException {
        extract();
        if(!param.getElement(0).isNum())
            throw new IncorrectParametersException("Supplied incorrect type of expression. Required: Num");
        NumericalElement p = (NumericalElement)param.getElement(0);
        Element r;

        while (p.toBool()){
            r = instructionSet.execute();
            if(r != null)
                return r;
            paramExpression.extract();
            if(!param.getElement(0).isNum())
                throw new IncorrectParametersException("Supplied incorrect type of expression. Required: Num");
            p = (NumericalElement)param.getElement(0);
        }
        return null;
    }
}
