package PixelSeeker.exceptions;

public class RuntimeErrorException extends Exception{
    public RuntimeErrorException(String cause, Throwable e){
        super(cause,e);
    }
    public RuntimeErrorException(String cause){
        super(cause);
    }
    public RuntimeErrorException(Throwable e){
        super(e);
    }
}
