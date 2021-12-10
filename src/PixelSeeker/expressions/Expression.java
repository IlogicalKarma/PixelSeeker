package PixelSeeker.expressions;

import PixelSeeker.MultiClassArray;
import PixelSeeker.exceptions.ExpressionExtractionFailureException;
/**/
public class Expression{
    private String raw;
    private int i = 0;
    private OperatorHandler.Operator[] operators = OperatorHandler.operators.operatorsArray;
    private MultiClassArray elements = new MultiClassArray();
    public Expression(String raw) throws ExpressionExtractionFailureException{
        raw = raw.trim();
        String valueString = new String();
        Integer value;
        Expression expression;
        int nested, j;
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
    }
    private void toBoolean(int i){
        try {
            Object object = elements.get(i);
            Class classObject = elements.get(i).getClassObject();
            if (classObject == Class.forName("java.lang.Boolean")) {

            }else if(classObject == Class.forName("java.lang.Integer")){

            }else if(classObject == Class.forName("PixelSeeker.expressions.OperatorHandler$Operator")){

            }else if (classObject == Class.forName("PixelSeeker.expressions.Expression")){

            }
        }catch (ClassNotFoundException e){
            e.printStackTrace();
            System.out.println(e.toString());
        }
    }
    private int value(String string) throws ExpressionExtractionFailureException{
        string = string.trim().toLowerCase();
        if(string == null)
            throw new ExpressionExtractionFailureException("Null value");
        if(string.equals("true"))
            return 1;
        if(string.equals("false"))
            return 0;
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException e){
            throw new ExpressionExtractionFailureException("Illegal value: \"" + string + '"');
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
    /*public Expression nestedExpression() throws ExpressionExtractionFailureException{
        String expression = new String();
        int nested = 1,j;
        char current;
        for(j = i+1; nested > 0 && j < raw.length(); j++){
            current = raw.charAt(j);
            if(current == '(')
                nested++;
            else if(current == ')')
                nested--;
            expression += current;
        }
        i = j;
        expression = expression.substring(0,expression.length()-1);
        if(nested == 0)
            return new Expression(expression);
        throw new ExpressionExtractionFailureException("Nested expression unclosed/unopened");
    }
    public int extract() throws ExpressionExtractionFailureException{
        int v = 0;
        boolean start = true;
        for(i = 0; i < raw.length();){
            String valueString = new String();
            OperatorHandler.Operator currentOperator = operatorLookup(raw.charAt(i));
            while (raw.charAt(i) == ' '){ i++;
                System.out.println("spatiu"); }
            if(raw.charAt(i) == '('){
                v = nestedExtraction();
                System.out.println(v + "asa");
                if(start)
                    start = false;
                continue;
            }
            if (start)
                valueString += raw.charAt(i);
            valueString = valueString.trim();
            System.out.println("aici avem i de ");
            while (++i < raw.length()-1) {
                if (operatorLookup(raw.charAt(i)) != null) {
                    break;
                }
                valueString += raw.charAt(i);
            }
                if (start) {
                    v = value(valueString);
                } else
                    v = currentOperator.apply(v, value(valueString));
                if(start)
                    start = false;
        }
        System.out.println(v + "sadasg");
        return v;
    }*/

    @Override
    public String toString() {
        String output = "Expression: { ";
        boolean first = true;
        for(int i = 0; i < elements.size(); i++){
            if(first)
                first = false;
            else
                output += "; ";
                output += (elements.get(i).getClassObject() == this.getClass() ? elements.get(i).getObject().toString() : elements.get(i).getClassObject().getName());

        }
        output += " }";
        return output;
    }
}

