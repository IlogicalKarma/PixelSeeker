package PixelSeeker.exceptions;

public class ExpressionExtractionFailureException extends Exception {
    public ExpressionExtractionFailureException(String cause){
        super(cause);
    }
    public ExpressionExtractionFailureException(String cause, Throwable err){
        super(cause,err);
    }
}
