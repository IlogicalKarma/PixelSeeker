package PixelSeeker.exceptions;

public class MissingInstructionSetException extends Exception{
    public MissingInstructionSetException(){
        super("This instruction requires an instruction set");
    }
}
