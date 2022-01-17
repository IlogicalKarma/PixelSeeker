package PixelSeeker.expressions;

import PixelSeeker.MultiClassArray;
import PixelSeeker.Parcel;
import PixelSeeker.exceptions.ExpressionExtractionFailureException;

public class Expression{
    private String raw;
    private boolean bool = false;
    private int value = 0;
    private OperatorHandler.Operator[] operators = OperatorHandler.getInstance().getOperatorsArray();
    private MultiClassArray elements = new MultiClassArray();
    public Expression(String raw) throws ExpressionExtractionFailureException{
        raw = raw.trim();
        this.raw = raw;
        String valueString = new String();
        Integer value;
        Expression expression;
        int nested, j, i = 0;
        boolean valueBypass = false, notEmpty = false;
        OperatorHandler.Operator operator;
        while(i < raw.length()){
            System.out.println(i + " " + raw.charAt(i));
            char current = raw.charAt(i++);
            if(current == '(') {
                nested = 1;
                j = i;
                while(nested != 0){
                    if(j == raw.length())
                        throw new ExpressionExtractionFailureException("Expected \")\"");
                    j++;
                    if(raw.charAt(j) == ')')
                        nested--;
                    else if(raw.charAt(j) == '(')
                        nested++;
                }
                int lastIndex = j;
                if (!valueString.trim().isEmpty())
                    throw new ExpressionExtractionFailureException("Illegal expression positioning: " + valueString.trim());
                if (lastIndex == -1)
                    throw new ExpressionExtractionFailureException("Unfinished expression: " + valueString.trim());
                expression = new Expression(raw.substring(i, lastIndex));
                elements.add(expression, expression.getClass());
                i = lastIndex + 1;
                valueBypass = true;
            } else if(operatorLookup(current) != null) {
                operator = operatorLookup(current);
                if (!valueBypass){
                    value = Integer.valueOf(value(valueString));
                    elements.add(value, value.getClass());
                }else
                    valueBypass = false;
                elements.add(operator, operator.getClass());
                valueString = new String();
                current = raw.charAt(i++);
            }
            valueString += current;
        }
        if (!valueBypass){
            value = Integer.valueOf(value(valueString));
            elements.add(value, value.getClass());
        }
        toBoolean();
    }
    private void toBoolean() throws ExpressionExtractionFailureException{
        int i = 0, size = elements.size(), v = 0, currentV;
        OperatorHandler.Operator cOperator = null;
        Parcel parcel;
        for(; i < size; i++){
            parcel = elements.get(i);
            ;
            if(i%2 == 0){
                if(!(parcel.getObject() instanceof Integer || parcel.getObject() instanceof Expression) )
                    throw new ExpressionExtractionFailureException("Expected value or expression at element " + i);
                if(parcel.getObject() instanceof Expression) {
                    if(i == 0) {
                        v = ((Expression) parcel.getObject()).value;
                        continue;
                    }else
                        currentV = ((Expression) parcel.getObject()).value;
                }else {
                    if(i == 0) {
                        v = ((Integer) parcel.getObject());
                        continue;
                    } else
                        currentV = ((Integer) parcel.getObject());
                }
                    v = cOperator.apply(v,currentV);
            }else{
                if(!(parcel.getObject() instanceof OperatorHandler.Operator))
                    throw new ExpressionExtractionFailureException("Expected operator at element " + i);
                cOperator = (OperatorHandler.Operator) parcel.getObject();
            }
        }
        if(i%2 == 0)
            throw new ExpressionExtractionFailureException("Expected value at element " + i);
        bool = v % 2 == 1;
        value = v;
    }
    private int value(String string) throws ExpressionExtractionFailureException{
        string = string.trim().toLowerCase();
        if(string == null)
            throw new ExpressionExtractionFailureException("Null value");
        if(string.equals("true"))
            return 1;
        if(string.equals("false"))
            return 0;
        for(CheckHandler.Check check : CheckHandler.getChecksArray()){
            if(check.verify(string))
                return check.extractProcedure();
        }
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException e){
            throw new ExpressionExtractionFailureException("Illegal value: \"" + string + "\"");
        }
    }
    private OperatorHandler.Operator operatorLookup(char character){
        for(int i = 0; i < operators.length; i++){
            if(operators[i].verify(String.valueOf(character))) {
                return operators[i];
            }
        }
        return null;
    }

    @Override
    public String toString() {
        String output = "Expression: { ";
        boolean first = true;
        for(int i = 0; i < elements.size(); i++){
            if(first)
                first = false;
            else
                output += " ";
                output += (elements.get(i).getObject().toString());

        }
        output += " | Value: " + bool + " }";
        return output;
    }
}

