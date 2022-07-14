package PixelSeeker;

import PixelSeeker.storage.Data;
import PixelSeeker.storage.Context;
import PixelSeeker.exceptions.ExpressionExtractionFailureException;
import PixelSeeker.exceptions.IncorrectParametersException;
import PixelSeeker.exceptions.InstructionSyntaxException;
import PixelSeeker.exceptions.InvalidVariableNameException;
import PixelSeeker.exceptions.RuntimeErrorException;
import PixelSeeker.expressions.Expression;

public abstract class Instruction{
    protected Expression paramExpression;
    protected Data[] param;
    protected Data paramData;
    protected InstructionSet instructionSet;
    protected int paramNr = 0;
    protected Context context;
    public Instruction(Expression paramExpression, int paramNr, InstructionSet instructionSet, boolean requiresInstructionSet, Context context) throws InstructionSyntaxException {
        this.context = new Context(context);
        this.paramExpression = paramExpression;
        if(instructionSet == null && requiresInstructionSet)
            throw new InstructionSyntaxException("Expected instruction set.");
        else if(instructionSet != null && !requiresInstructionSet)
            throw new InstructionSyntaxException("Unexpected instruction set.");
        this.paramNr = paramNr;
        this.instructionSet = instructionSet;
    }
    protected void extract() throws IncorrectParametersException, ExpressionExtractionFailureException {
        this.paramData = paramExpression.extract();
        this.param = paramData.toArr();
        if(param.length < paramNr)
            throw new IncorrectParametersException("Insufficient number of parameters supplied: Expected " + paramNr + ", Supplied " + param.length);
    }
    public abstract Data execute() throws ExpressionExtractionFailureException, InvalidVariableNameException, IncorrectParametersException, RuntimeErrorException;
}