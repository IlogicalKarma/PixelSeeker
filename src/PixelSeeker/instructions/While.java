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
    public While(Expression paramExpression, InstructionSet instructionSet, NameManagement context) throws InstructionSyntaxException {
        super(paramExpression, 1, instructionSet, true, context);
    }

    @Override
    public Element execute() throws PixelSeeker.exceptions.InvalidVariableNameException, ExpressionExtractionFailureException, IncorrectParametersException, RuntimeErrorException {
        Element r, p;
        do{
            extract();
            p = param.get(0);
            if(!p.isNum())
                throw new IncorrectParametersException("Supplied incorrect type of expression. Required: Num");
            r = instructionSet.execute();
            if(r != null)
                return r;
        }while (p.toBool());
        return null;
    }
}
