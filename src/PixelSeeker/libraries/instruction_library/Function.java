package PixelSeeker.libraries.instruction_library;

import PixelSeeker.Instruction;
import PixelSeeker.InstructionSet;
import PixelSeeker.exceptions.*;
import PixelSeeker.expressions.Expression;
import PixelSeeker.storage.Context;
import PixelSeeker.storage.Data;
import PixelSeeker.storage.FunctionValue;

public class Function extends Instruction {
    public final static String identifier = "fun";
    public final static boolean requiresInstructionSet = true;
    public Function(Expression paramExpression, InstructionSet instructionSet, Context context) throws InstructionSyntaxException {
        super(paramExpression, -1, instructionSet, requiresInstructionSet, null);
    }

    @Override
    public Data execute() throws ExpressionExtractionFailureException, RuntimeErrorException, InvalidVariableNameException, IncorrectParametersException {
        extract();
        //new Function(name, param, instructionSet);
        new Data(new FunctionValue(
                (Data param)->{
                    return instructionSet.execute();
                }
        ));

        return null;
    }
}
