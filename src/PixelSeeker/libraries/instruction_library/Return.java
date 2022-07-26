package PixelSeeker.libraries.instruction_library;

import PixelSeeker.Instruction;
import PixelSeeker.InstructionSet;
import PixelSeeker.exceptions.ExpressionExtractionFailureException;
import PixelSeeker.exceptions.IncorrectParametersException;
import PixelSeeker.exceptions.InstructionSyntaxException;
import PixelSeeker.exceptions.InvalidVariableNameException;
import PixelSeeker.expressions.Expression;
import PixelSeeker.storage.Context;
import PixelSeeker.storage.Data;

public class Return extends Instruction {
    public final static String identifier = "return";
    public final static boolean requiresInstructionSet = false;

    public Return(Expression paramExpression, InstructionSet instructionSet, Context context) throws InstructionSyntaxException {
        super(paramExpression, -1, null, false, context);
    }

    @Override
    public Data execute() throws ExpressionExtractionFailureException, InvalidVariableNameException, IncorrectParametersException {
        extract();
        return paramData;
    }
}
