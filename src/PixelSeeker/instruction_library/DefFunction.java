package PixelSeeker.instruction_library;

import PixelSeeker.Instruction;
import PixelSeeker.InstructionSet;
import PixelSeeker.exceptions.ExpressionExtractionFailureException;
import PixelSeeker.exceptions.IncorrectParametersException;
import PixelSeeker.exceptions.InstructionSyntaxException;
import PixelSeeker.exceptions.InvalidVariableNameException;
import PixelSeeker.expressions.Expression;
import PixelSeeker.storage.Context;
import PixelSeeker.storage.Data;

public class DefFunction extends Instruction {
    public final static String identifier = "def";
    public final static boolean requiresInstructionSet = true;
    static String type = "DefFunction";
    private String name;

    public DefFunction(Expression paramExpression, InstructionSet instructionSet, Context context) throws InstructionSyntaxException {
        super(paramExpression, 1, instructionSet, requiresInstructionSet, context);
    }

    @Override
    public Data execute() throws ExpressionExtractionFailureException, InvalidVariableNameException, IncorrectParametersException {
        //new Function(name, param, instructionSet);
        return null;
    }
}
