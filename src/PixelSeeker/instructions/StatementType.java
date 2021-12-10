package PixelSeeker.instructions;

import PixelSeeker.instructions.Instruction;
import PixelSeeker.instructions.InstructionSet;

import java.beans.Expression;

public abstract class StatementType extends Instruction {
    private InstructionSet instructionSet;
    private Expression expression;
    StatementType(){
        super.type = "Statement";
    }
    public abstract void execute();
    public abstract void edit();
    public abstract void setInstructionSet(InstructionSet instructionSet);

}
