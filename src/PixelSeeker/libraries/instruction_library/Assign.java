package PixelSeeker.libraries.instruction_library;

import PixelSeeker.Instruction;
import PixelSeeker.InstructionSet;
import PixelSeeker.exceptions.ExpressionExtractionFailureException;
import PixelSeeker.exceptions.IncorrectParametersException;
import PixelSeeker.exceptions.InstructionSyntaxException;
import PixelSeeker.expressions.Expression;
import PixelSeeker.storage.Context;
import PixelSeeker.storage.Data;

public class Assign extends Instruction {
    public final static boolean requiresInstructionSet = false;
    public final static String identifier = "assign";

    public Assign(Expression paramExpression, InstructionSet instructionSet, Context context) throws InstructionSyntaxException {
        super(paramExpression, 2,null , requiresInstructionSet, context);
    }

    public Data execute() throws ExpressionExtractionFailureException, IncorrectParametersException {
        extract();
        for (int i = 0; i < param.length; i++)
            param[i].copyTo(param[0]);
        return null;
    }
}
