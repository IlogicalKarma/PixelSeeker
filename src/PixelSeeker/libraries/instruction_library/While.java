package PixelSeeker.libraries.instruction_library;

import PixelSeeker.Instruction;
import PixelSeeker.InstructionSet;
import PixelSeeker.exceptions.*;
import PixelSeeker.expressions.Expression;
import PixelSeeker.storage.Context;
import PixelSeeker.storage.Data;

public class While extends Instruction {
    public final static String identifier = "while";
    public final static boolean requiresInstructionSet = true;

    public While(Expression paramExpression, InstructionSet instructionSet, Context context) throws InstructionSyntaxException {
        super(paramExpression, 1, instructionSet, true, context);
    }

    @Override
    public Data execute() throws InvalidVariableNameException, ExpressionExtractionFailureException, IncorrectParametersException, RuntimeErrorException {
        extract();
        Data r, p;
        while (paramData.toBool()) {
            r = instructionSet.execute();
            if (r != null)
                return r;
            extract();
        }
        return null;
    }
}
