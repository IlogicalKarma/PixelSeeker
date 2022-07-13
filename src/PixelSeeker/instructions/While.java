package PixelSeeker.instructions;

import PixelSeeker.storage.Data;
import PixelSeeker.storage.Context;
import PixelSeeker.exceptions.ExpressionExtractionFailureException;
import PixelSeeker.exceptions.IncorrectParametersException;
import PixelSeeker.exceptions.InstructionSyntaxException;
import PixelSeeker.exceptions.RuntimeErrorException;
import PixelSeeker.expressions.Expression;

public class While extends Instruction{
    final static String identifier = "while";
    static String type = "WhileStatement";
    public While(Expression paramExpression, InstructionSet instructionSet, Context context) throws InstructionSyntaxException {
        super(paramExpression, 1, instructionSet, true, context);
    }

    @Override
    public Data execute() throws PixelSeeker.exceptions.InvalidVariableNameException, ExpressionExtractionFailureException, IncorrectParametersException, RuntimeErrorException {
        Data r, p;
        extract();
        while (paramData.toBool()){
            r = instructionSet.execute();
            if(r != null)
                return r;
            extract();
        }
        return null;
    }
}
