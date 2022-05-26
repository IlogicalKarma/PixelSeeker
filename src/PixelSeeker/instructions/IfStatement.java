package PixelSeeker.instructions;

import PixelSeeker.expressions.Expression;

public class IfStatement extends StatementType{
    public IfStatement(Expression expression, InstructionSet instructionSet) {
        super(expression, instructionSet);
    }
    @Override
    public void execute() {
        if(expression.getBool())
            instructionSet.execute();
    }
}
