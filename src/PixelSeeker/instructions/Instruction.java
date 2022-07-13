package PixelSeeker.instructions;

import PixelSeeker.DataStorage.ArrayElement;
import PixelSeeker.DataStorage.Element;
import PixelSeeker.DataStorage.NameManagement;
import PixelSeeker.exceptions.*;
import PixelSeeker.expressions.Expression;

import java.util.ArrayList;

public abstract class Instruction{
    protected Expression paramExpression;
    protected ArrayList<Element> param;
    protected Element paramElem;
    protected InstructionSet instructionSet;
    protected static String type;
    protected static String identifier;
    protected int paramNr = 0;
    protected NameManagement context;
    public Instruction(Expression paramExpression, int paramNr, InstructionSet instructionSet, boolean requiresInstructionSet, NameManagement context) throws InstructionSyntaxException {
        this.context = new NameManagement(context);
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
        this.param = ((ArrayList<Element>)paramElem.get());
        if(param.size() < paramNr)
            throw new IncorrectParametersException("Insufficient number of parameters supplied: Expected " + paramNr + ", Supplied " + param.size());
    }
    public abstract Element execute() throws ExpressionExtractionFailureException, InvalidVariableNameException, IncorrectParametersException, RuntimeErrorException;
}