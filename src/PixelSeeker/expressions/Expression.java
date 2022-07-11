package PixelSeeker.expressions;

import PixelSeeker.DataStorage.*;
import PixelSeeker.exceptions.ExpressionExtractionFailureException;
import PixelSeeker.exceptions.NamingException;
import PixelSeeker.exceptions.UnexpectedDataTypeException;

import java.util.ArrayList;
import java.util.Locale;

public class Expression extends Element{
    private NameManagement context;
    private String type;
    private String raw;
    private boolean bool = false, isVar = false;
    private int value = 0;
    private String string = "";
    private OperatorHandler operatorHandler = OperatorHandler.getInstance();
    private ArrayList<Element> elements  = new ArrayList<>();
    private ArrayList<OperatorHandler.Operator> operators = new ArrayList<>();
    public Expression(String raw, NameManagement context) throws ExpressionExtractionFailureException{
        super(5, context);
        this.context = context;
        raw = raw.trim();
        this.raw = raw;
        //System.out.println(raw);   //here
        if(raw.isEmpty())
            throw new ExpressionExtractionFailureException("Empty expression");
        String valueString = new String();
        String op = new String();
        int i = 0, elem = 0, nests, nestIndex;
        OperatorHandler.Operator operator;
        char current;
        while(i < raw.length()) {
            current = raw.charAt(i++);
            if (elem % 2 == 0) {
                if (operatorHandler.operatorWhitelist(current)) {
                    i--;
                    elem++;
                    try {
                        elements.add(value(valueString));
                    }catch (NamingException e){
                        throw new ExpressionExtractionFailureException(e);
                    }
                    valueString = new String();
                }else{
                    switch (current){
                        case '"':
                            boolean end = false;
                            valueString += current;
                            while(i < raw.length() && !end){
                                switch (raw.charAt(i)) {
                                    case '\\':
                                        if (raw.charAt(i + 1) == '"' || raw.charAt(i + 1) == '\\') {
                                            i++;
                                        }
                                        break;
                                    case '"':
                                        end = true;
                                        break;
                                }
                                valueString += raw.charAt(i++);
                            }
                            break;
                        case '(':
                            nestIndex = i-1;
                            nests = 1;
                            do
                                if (raw.charAt(i) == '(')
                                    nests++;
                                else if (raw.charAt(i) == ')')
                                    nests--;
                            while(++i < raw.length() && nests != 0);
                            if(nests != 0){
                                throw new ExpressionExtractionFailureException("Incorrect expression brackets placement");}
                            valueString = raw.substring(nestIndex, i);
                            break;
                        case ')':
                            throw new ExpressionExtractionFailureException("Incorrect expression brackets placement");
                        case '{':
                            nestIndex = i-1;
                            nests = 1;
                            do
                                if(raw.charAt(i) == '{')
                                    nests++;
                                else if(raw.charAt(i) == '}')
                                    nests--;
                            while(++i < raw.length() && nests != 0);
                            if(nests != 0){
                                throw new ExpressionExtractionFailureException("Incorrect array brackets placement");}
                            valueString += raw.substring(nestIndex, i);
                            break;
                        case '}':
                            throw new ExpressionExtractionFailureException("Incorrect array brackets placement");
                        /*case ',':

                            break;*/
                        default:
                            valueString += current;
                    }
                }
            } else {
                if(operatorHandler.operatorWhitelist(current)) {
                    op += current;
                }else{
                    i--;
                    elem++;
                    operator = operatorHandler.operatorLookup(op);
                    op = new String();
                    if (operator == null)
                        throw new ExpressionExtractionFailureException("Invalid operator: " + op);
                    operators.add(operator);
                }
            }
        }
        try {
            elements.add(value(valueString));
        }catch (NamingException e){
            throw new ExpressionExtractionFailureException(e.getMessage(),e);
        }
        if(elem%2 == 1)
            throw new ExpressionExtractionFailureException("Expected value in expression");
    }

    public Element extract() throws ExpressionExtractionFailureException{
        int i = 1, j = 0, k = 0, sizeElements = elements.size();
        isVar = false;
        Element v = elements.get(0), v1;
        if(v.isExpr())
            v = ((Expression)v).extract();
        else if(v.isArray()) {
            for (; k < ((ArrayElement)v).getLength(); k++)
                ((ArrayElement) v).get().set(k, ((Expression)(((ArrayElement) v).get().get(k))).extract());
        }
        if(2 > sizeElements && v.isNamed()) { //here
             set(v);
             return v;
        }
        i = 1;
        while(i < sizeElements && j < operators.size()) {
            try {
                v1 = elements.get(1);
                if(v1.isExpr())
                    v1 = ((Expression)v1).extract();
                else if(v1.isArray()) {
                    for (; k < ((ArrayElement)v1).getLength(); k++)
                        ((ArrayElement) v1).get().set(k, ((Expression)(((ArrayElement) v1).get().get(k))).extract());
                }
                v = operators.get(j).apply(v, v1);
            }catch (UnexpectedDataTypeException e){
                throw new ExpressionExtractionFailureException("Failed to apply operator",e);
            }
            i++;
            j++;
        }
        set(v);
        return v;
    }

    @Override
    public Element get() throws ExpressionExtractionFailureException{
        return (Element) super.value;
    }

    private Element value(String string) throws ExpressionExtractionFailureException, NamingException {
        if (string == null)
            throw new ExpressionExtractionFailureException("Null value");
        string = string.trim().toLowerCase(Locale.ROOT);
        Integer r;
        int i, nests;

        if (string.startsWith("\"") && string.endsWith("\"")) {
            return new StringElement(string.substring(1, string.length() - 1), context);
        }
        if (string.startsWith("(") && string.endsWith(")")) {
            return new Expression(string.substring(1, string.length() - 1), context);
        }
        if (string.startsWith("{") && string.endsWith("}")) {
            String elemRaw = new String();
            char c;
            nests = 0;
            ArrayElement array = new ArrayElement();
            if(string.isEmpty())
                return array;

            // arrayRaw.split(",");
            for(i = 1; i < string.length()-1; i++){
                c = string.charAt(i);
                switch (c){
                    case ',':
                        if(nests == 0) {
                            array.get().add(new Expression(elemRaw, context));
                            elemRaw = new String();
                        }else
                            elemRaw += c;
                        break;
                    case '{':
                        nests++;
                        elemRaw += c;
                        break;
                    case '}':
                        nests--;
                        elemRaw += c;
                        break;
                    default:
                        elemRaw += c;
                }

            }
            array.get().add(new Expression(elemRaw, context));
            return array;
        }
        if (context.has(string))
            return context.get(string);

        try {
            r = Integer.parseInt(string);
            return new NumericalElement(r, context);
        } catch (NumberFormatException e) {
        }
        if (context.has(string))
            return context.get(string);
            //
        else {
            if(string.contains("(") || string.contains("\"") || string.contains(")"))
                throw new ExpressionExtractionFailureException("Illegal symbol found in declaration");
            Element elem = new NumericalElement(string, 0, context);
            return elem;
        }
    }

    @Override
    public String toString() {
        String output = "Expression: { ";
        boolean first = true;
        for(int i = 0; i < elements.size(); i++){
            if(first)
                first = false;
            else
                output += ", ";
                output += (elements.get(i).toString());
        }
        output += " | Value: " + value + " }";
        return output;
    }
}

