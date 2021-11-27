package PixelSeeker;

import PixelSeeker.Exceptions.ExpressionExtractionFailureException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class Expression{
    private ArrayList<String> elements;
    Expression(String raw){
        elements = new ArrayList<>(Arrays.asList(raw.split(" ")));
    }
    boolean extract() throws ExpressionExtractionFailureException{
        boolean nv = false;
        boolean ev = true;
        boolean v = true;
        for(int i = 0; i < elements.size(); i++){
            switch (elements.get(i).toLowerCase(Locale.ROOT)){
                case "!":
                case "not":
                    if(!ev)
                        throw new ExpressionExtractionFailureException();
                    v = !v;
                    break;
                case "&":
                case "&&":
                case "and":
                case "=":
                case "equal":
                    if(ev || nv)
                        throw new ExpressionExtractionFailureException();
                    ev = true;
                    break;
                case "false":
                    v = !v;
                case "true":
                    ev = false;
                    break;
                default:
                    throw new ExpressionExtractionFailureException();
            }
        }
        if(ev)
            throw new ExpressionExtractionFailureException();
        return v;
    }
}
//Bafta la munca
