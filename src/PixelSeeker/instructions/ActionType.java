package PixelSeeker.instructions;

import PixelSeeker.instructions.Instruction;

public abstract class ActionType extends Instruction {
    ActionType(){
        super.type = "Action";
    }
    public abstract void use();
}
