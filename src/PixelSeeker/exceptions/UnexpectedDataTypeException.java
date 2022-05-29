package PixelSeeker.exceptions;

public class UnexpectedDataTypeException extends Exception{
    public UnexpectedDataTypeException(String info, Throwable err){
        super(info,err);
    }
    public UnexpectedDataTypeException(String info){
        super(info);
    }
}
