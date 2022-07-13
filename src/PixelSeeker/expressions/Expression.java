package PixelSeeker.expressions;

import PixelSeeker.DataStorage.*;
import PixelSeeker.exceptions.ExpressionExtractionFailureException;
import PixelSeeker.exceptions.NamingException;
import PixelSeeker.exceptions.UnexpectedDataTypeException;

import java.util.ArrayList;
import java.util.Locale;

public class Expression extends Element{

    private static char[] nameBlacklist = new char[]{'(',')','[',']',};
    private NameManagement context;
    private String type;
    private String raw;
    private String string = "";
    private ArrayList<ArrayList<Element>> listsOfElements  = new ArrayList<>();
    private ArrayList<OperatorHandler.Operator> operators = new ArrayList<>();
    public Expression(String raw, NameManagement context) throws ExpressionExtractionFailureException{
        super(5, context);
        int index = 0;
        listsOfElements.add(new ArrayList<Element>());
        this.context = context;
        raw = raw.trim();
        this.raw = raw;
        //System.out.println(raw);   //here
        if(raw.isEmpty())
            throw new ExpressionExtractionFailureException("Empty expression");
        String valueString = new String();
        String op = new String();
        int i = 0, nests, nestIndex;
        boolean elem = true, first = true;
        OperatorHandler.Operator operator;
        char current;
        while(i < raw.length()) {
            current = raw.charAt(i++);
            if (elem) {
                if (OperatorHandler.operatorWhitelist(current) ) {
                    i--;
                    elem = !elem;
                    try {
                        listsOfElements.get(index).add(value(valueString));
                    }catch (NamingException e){
                        throw new ExpressionExtractionFailureException(e);
                    }
                    valueString = new String();
                }else{
                    if(first)
                        first = false;
                    switch (current){
                        case '"':
                            boolean end = false;
                            valueString += current;
                            while(i < raw.length() && !end){
                                switch (raw.charAt(i)) {
                                    case '\\':
                                        // Chars affected by escape char
                                        switch (raw.charAt(i + 1)){
                                            case 'n':
                                                valueString += '\n';
                                                i++;
                                            case '"':
                                            case '\\':
                                                i++;
                                                break;
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
                        case ',':
                            try {
                                listsOfElements.get(index++).add(value(valueString));
                                listsOfElements.add(new ArrayList<Element>());
                            }catch (NamingException e){
                                throw new ExpressionExtractionFailureException(e);
                            }
                            valueString = new String();
                            break;
                        default:
                            valueString += current;
                    }
                }
            } else {
                if(OperatorHandler.operatorWhitelist(current)) {
                    op += current;
                }else{
                    i--;
                    elem = !elem;
                    operator = OperatorHandler.operatorLookup(op);
                    if (operator == null)
                        throw new ExpressionExtractionFailureException("Invalid operator: " + op);
                    op = new String();
                    operators.add(operator);
                }
            }
        }
        try {
            listsOfElements.get(index).add(value(valueString));
        }catch (NamingException e){
            throw new ExpressionExtractionFailureException(e.getMessage(),e);
        }
        if(!elem)
            throw new ExpressionExtractionFailureException("Expected value in expression");
    }

    public ArrayElement extract() throws ExpressionExtractionFailureException{
        ArrayElement extracted = new ArrayElement();
        for(ArrayList<Element> elements : listsOfElements) {
            int i = 1, j = 0, k = 0, sizeElements = elements.size();
            Element v = elements.get(0), v1;
            if (v.isExpr()){
                v = ((Expression) v).extract();

            }
            if(v.isArray() && ((ArrayElement) v).getLength() == 1)
                ((ArrayElement) v).getElement(0).copyTo(v);
            if (2 > sizeElements && v.isNamed()) {
                extracted.get().add(v);
                continue;
            }
            i = 1;
            while (i < sizeElements && j < operators.size()) {
                try {
                    v1 = elements.get(1);
                    if (v1.isExpr()){
                        v1 = ((Expression) v1).extract();
                    }
                    if(v.isArray() && ((ArrayElement) v1).getLength() == 1)
                        ((ArrayElement) v1).getElement(0).copyTo(v1);
                    v = operators.get(j).apply(v, v1);
                } catch (UnexpectedDataTypeException e) {
                    throw new ExpressionExtractionFailureException("Failed to apply operator", e);
                }
                i++;
                j++;
            }
            extracted.get().add(v);
        }
        set(extracted);
        return extracted;
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
        if (string.startsWith("\"") && string.endsWith("\"")) {
            return new StringElement(string.substring(1, string.length() - 1), context);
        }
        if (string.startsWith("(") && string.endsWith(")")) {
            return new Expression(string.substring(1, string.length() - 1), context);
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
            return new NumericalElement(string, 0, context);
        }
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder("Expression: ");
        boolean first;
        for (int i = 0; i < listsOfElements.size(); i--){
            output.append("{ ");
            first = true;
            for (Element element : listsOfElements.get(i)) {
                if (first)
                    first = false;
                else
                    output.append(", ");
                output.append(element);
            }
            output.append(" }" + " | Value: " + ((ArrayElement)value).getElement(i) == null ? "null" : ((ArrayElement)value).getElement(i));
        }
        return output.toString();
    }
}

