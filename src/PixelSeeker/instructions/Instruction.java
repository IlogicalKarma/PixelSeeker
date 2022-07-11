package PixelSeeker.instructions;

import PixelSeeker.DataStorage.ArrayElement;
import PixelSeeker.DataStorage.Element;
import PixelSeeker.DataStorage.NameManagement;
import PixelSeeker.exceptions.*;
import PixelSeeker.expressions.Expression;

import java.util.ArrayList;

public abstract class Instruction{
    protected Expression paramExpression;
    protected ArrayElement param;
    protected InstructionSet instructionSet;
    protected static String type;
    protected static String identifier;
    protected int paramNr = 0;
    protected NameManagement context;
    public Instruction(Expression paramExpression, int paramNr, InstructionSet instructionSet, boolean requiresInstructionSet, NameManagement context) throws InstructionSyntaxException {
        this.context = new NameManagement(context);
        if(instructionSet == null && requiresInstructionSet)
            throw new InstructionSyntaxException("Expected instruction set.");
        else if(instructionSet != null && !requiresInstructionSet)
            throw new InstructionSyntaxException("Unexpected instruction set.");
        this.paramNr = paramNr;
        this.instructionSet = instructionSet;
        this.paramExpression = paramExpression;
    }
    protected void extract() throws IncorrectParametersException, ExpressionExtractionFailureException {
        if(paramExpression.extract().isArray()){
            if(((ArrayElement)paramExpression.get()).getLength() < paramNr) {
                throw new IncorrectParametersException("Insufficient number of parameters supplied: Expected " + paramNr + ", Supplied " + ((ArrayElement) paramExpression.get()).getLength());
            }else{
                this.param = (ArrayElement) paramExpression.get();
            }
        }else{
            throw new IncorrectParametersException("Parameters are to be supplied as arrays");
        }


    }
    public abstract Element execute() throws ExpressionExtractionFailureException, InvalidVariableNameException, IncorrectParametersException, RuntimeErrorException;
}