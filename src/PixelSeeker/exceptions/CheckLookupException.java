package PixelSeeker.exceptions;

public class CheckLookupException extends Exception{
    public CheckLookupException(String string){
        super("Failed to identify check from string: " + string);
    }
    public CheckLookupException(){
        super();
    }
}
