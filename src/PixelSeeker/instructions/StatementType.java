package PixelSeeker.instructions;

import PixelSeeker.instructions.Instruction;
import PixelSeeker.instructions.InstructionSet;

import PixelSeeker.expressions.Expression;

public abstract class StatementType extends Instruction {
    protected InstructionSet instructionSet;
    protected Expression expression;
    StatementType(Expression expression, InstructionSet instructionSet){
        this.instructionSet = instructionSet;
        this.expression = expression;
        super.type = "Statement";
    }
    public abstract void execute();

}
