package PixelSeeker.instructions;

import PixelSeeker.DataStorage.Data;
import PixelSeeker.DataStorage.Context;
import PixelSeeker.exceptions.ExpressionExtractionFailureException;
import PixelSeeker.exceptions.IncorrectParametersException;
import PixelSeeker.exceptions.InstructionSyntaxException;
import PixelSeeker.exceptions.InvalidVariableNameException;
import PixelSeeker.expressions.Expression;


public class DefFunction extends Instruction{

    final static String identifier = "def";
    static String type = "DefFunction";
    private String name;
    public DefFunction(Expression paramExpression, InstructionSet instructionSet, Context context) throws InstructionSyntaxException {
        super(paramExpression, 1, instructionSet, true, context);
    }

    @Override
    public Data execute() throws ExpressionExtractionFailureException, InvalidVariableNameException, IncorrectParametersException {
        //new Function(name, param, instructionSet);
        return null;
    }
}
