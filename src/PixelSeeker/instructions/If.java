package PixelSeeker.instructions;

import PixelSeeker.DataStorage.Element;
import PixelSeeker.DataStorage.NameManagement;
import PixelSeeker.DataStorage.NumericalElement;
import PixelSeeker.exceptions.ExpressionExtractionFailureException;
import PixelSeeker.exceptions.IncorrectParametersException;
import PixelSeeker.exceptions.InstructionSyntaxException;
import PixelSeeker.exceptions.RuntimeErrorException;
import PixelSeeker.expressions.Expression;

public class If extends Instruction{
    final static String identifier = "if";
    static String type = "IfStatement";
    public If(Expression param, InstructionSet instructionSet, NameManagement context) throws IncorrectParametersException, InstructionSyntaxException {
        super(param, 1, instructionSet, true, context);
    }

    @Override
    public Element execute() throws PixelSeeker.exceptions.InvalidVariableNameException, ExpressionExtractionFailureException, IncorrectParametersException, RuntimeErrorException {
        extract();
        Element p = param.getElement(0);
        if(!p.isNum())
            throw new IncorrectParametersException("Supplied incorrect type of expression. Required: Num");
        if(((NumericalElement) p).get()%2 == 1) {
            p = instructionSet.execute();
            if(p != null)
                return p;
        }
        return null;
    }
}
