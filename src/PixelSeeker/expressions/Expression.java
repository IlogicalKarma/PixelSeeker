package PixelSeeker.expressions;

import PixelSeeker.storage.*;
import PixelSeeker.exceptions.ExpressionExtractionFailureException;
import PixelSeeker.exceptions.NamingErrorException;
import PixelSeeker.exceptions.UnexpectedDataTypeException;

import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Pattern;

public class Expression implements Element {
    private Context context;
    private String raw;
    private Data data;
    private ArrayList<ArrayList<Element>> listsOfElements  = new ArrayList<>();
    public Expression(String raw, Context context) throws ExpressionExtractionFailureException{
        int index = 0;
        listsOfElements.add(new ArrayList<>());
        this.context = context;
        raw = raw.trim();
        this.raw = raw;
        if(raw.isEmpty())
            return;
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
                        listsOfElements.get(index).add(strToData(valueString));
                    }catch (NamingErrorException e){
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
                            while(!end) {
                                if(i >= raw.length())
                                    throw new ExpressionExtractionFailureException("Unfinished string quotations");
                                // Special chars
                                switch (raw.charAt(i)) {
                                    case '\\':

                                        // Chars affected by escape char
                                        if (++i < raw.length())
                                            switch (raw.charAt(i)) {
                                                case 'n':
                                                    valueString += '\n';
                                                    break;
                                                case '"':
                                                    valueString += '"';
                                                    break;
                                                case '\\':
                                                    valueString += '\\';
                                                    break;
                                                default:
                                                    valueString += '\\';
                                                    break;
                                            }
                                        break;
                                    case '"':
                                        valueString += "\"";
                                        end = true;
                                        break;
                                    default:
                                        valueString += raw.charAt(i);
                                }
                                i++;
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
                                listsOfElements.get(index++).add(strToData(valueString));
                                listsOfElements.add(new ArrayList<>());
                            }catch (NamingErrorException e){
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
                    listsOfElements.get(index).add(operator);
                }
            }
        }
        try {
            listsOfElements.get(index).add(strToData(valueString));
        }catch (NamingErrorException e){
            throw new ExpressionExtractionFailureException(e.getMessage(),e);
        }
        if(!elem)
            throw new ExpressionExtractionFailureException("Expected value in expression");
    }

    public Data extract() throws ExpressionExtractionFailureException{
        ArrayValue extracted = new ArrayValue();
        Data var1, var2;
        OperatorHandler.Operator operator;
        Element aux;
        for(ArrayList<Element> elements : listsOfElements) {
            int i = 1, j = 0, k = 0, sizeElements = elements.size();
            if(sizeElements == 0) {
                extracted.add(Data.getNum(0));
                continue;
            }
            aux = elements.get(0);
            if (aux instanceof Expression)
                var1 = ((Expression) aux).extract();
            else
                var1 = (Data) aux;
            if(var1.isArr() && var1.toArr().length == 1)
                var1.toArr()[0].copyTo(var1);
            if (sizeElements == 1 && var1.isNamed()) {
                    extracted.add(var1);
                    continue;
            }
            i = 1;
            while (i < sizeElements) {
                try {
                    operator = (OperatorHandler.Operator) elements.get(i++);
                    aux = elements.get(i++);
                    if (aux instanceof Expression)
                        var2 = ((Expression) aux).extract();
                    else
                        var2 = (Data) aux;
                    if(var2.isArr() && var2.toArr().length == 1)
                        var2.toArr()[0].copyTo(var2);
                    var1 = operator.apply(var1, var2);
                } catch (UnexpectedDataTypeException e) {
                    throw new ExpressionExtractionFailureException("Failed to apply operator", e);
                }
            }
            extracted.add(var1);
        }
        this.data = new Data(extracted);
        return this.data;
    }

    public Data get() throws ExpressionExtractionFailureException{
        return this.data;
    }

    private Element strToData(String str) throws ExpressionExtractionFailureException, NamingErrorException {
        Integer r;
        if (str == null)
            throw new ExpressionExtractionFailureException("Null value");
        str = str.trim().toLowerCase(Locale.ROOT);
        if(str.isEmpty())
            throw new ExpressionExtractionFailureException("Missing value");
        if (str.startsWith("\"") && str.endsWith("\"")) {
            return Data.getStr(str.substring(1, str.length() - 1));
        }
        if (str.startsWith("(") && str.endsWith(")")) {
            return new Expression(str.substring(1, str.length() - 1), context);
        }
        if (context.has(str))
            return context.get(str);

        try {
            r = Integer.parseInt(str);
            return Data.getNum(r);
        } catch (NumberFormatException e) {
        }
        if (context.has(str))
            return context.get(str);
            //
        else {
            return new Data(str, new NumericalValue(), context);
        }
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder("[ ");
        boolean first;
        for (int i = 0; i < listsOfElements.size(); i++){
            first = true;
            for (Element element : listsOfElements.get(i)) {
                if (first)
                    first = false;
                else
                    output.append(' ');
                output.append(element);
            }
            output.append(" | Value: " + (data == null ? "null" : data.toString()));
        }

        return output.append("] ").toString();
    }
}

