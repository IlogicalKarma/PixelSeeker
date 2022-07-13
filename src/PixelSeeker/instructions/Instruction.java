package PixelSeeker.instructions;

import PixelSeeker.DataStorage.Data;
import PixelSeeker.DataStorage.Context;
import PixelSeeker.exceptions.*;
import PixelSeeker.expressions.Expression;

import java.util.ArrayList;

public abstract class Instruction{
    protected Expression paramExpression;
    protected ArrayList<Data> param;
    protected Data paramElem;
    protected InstructionSet instructionSet;
    protected static String type;
    protected static String identifier;
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
        this.paramElem = paramExpression.extract();
        this.param = ((ArrayList<Data>)paramElem.get());
        if(param.size() < paramNr)
            throw new IncorrectParametersException("Insufficient number of parameters supplied: Expected " + paramNr + ", Supplied " + param.size());
    }
    public abstract Data execute() throws ExpressionExtractionFailureException, InvalidVariableNameException, IncorrectParametersException, RuntimeErrorException;
}