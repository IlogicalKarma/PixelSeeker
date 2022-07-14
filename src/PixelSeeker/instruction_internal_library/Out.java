package PixelSeeker.instruction_internal_library;

import PixelSeeker.Instruction;
import PixelSeeker.InstructionSet;
import PixelSeeker.exceptions.ExpressionExtractionFailureException;
import PixelSeeker.exceptions.IncorrectParametersException;
import PixelSeeker.exceptions.InstructionSyntaxException;
import PixelSeeker.expressions.Expression;
import PixelSeeker.storage.Context;
import PixelSeeker.storage.Data;

public class Out extends Instruction {
    public final static String identifier = "out";
    public final static boolean requiresInstructionSet = false;

    public Out(Expression paramExpression, InstructionSet instructionSet, Context context) throws InstructionSyntaxException {
        super(paramExpression, -1, null, false, context);
    }

    @Override
    public Data execute() throws ExpressionExtractionFailureException, IncorrectParametersException {
        if (param.length == 0) {
            System.out.println("\n");
            return null;
        }
        System.out.print(paramData.toUserString());
        return null;
    }
}
