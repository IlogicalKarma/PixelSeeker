package PixelSeeker;

public abstract class ActionType extends Instruction{
    ActionType(){
        super.type = "Action";
    }
    public abstract void use();
}
