package PixelSeeker.expressions;

import PixelSeeker.MultiClassArray;
import PixelSeeker.Parcel;
import PixelSeeker.exceptions.CheckLookupException;
import PixelSeeker.exceptions.ExpressionExtractionFailureException;
import PixelSeeker.exceptions.UnexpectedDataTypeException;
import PixelSeeker.instructions.Var;
import PixelSeeker.instructions.VarManagement;

public class Expression{
    private String type;
    private String raw;
    private boolean bool = false, isVarV = false;
    private int value = 0;
    private Var var = null;
    private Parcel parcel = null;
    private String string = "";
    private OperatorHandler operators = OperatorHandler.getInstance();
    private MultiClassArray elements = new MultiClassArray();
    public Expression(String raw) throws ExpressionExtractionFailureException{
        raw = raw.trim();
        this.raw = raw;
        String valueString = new String();
        String op = new String();
        int i = 0, elem = 0, nests, nestIndex;
        OperatorHandler.Operator operator;
        char current;
        while(i < raw.length()) {
            current = raw.charAt(i++);
            if (elem % 2 == 0) {
                if (operators.operatorWhitelist(current)) {
                    i--;
                    elem++;
                    elements.add(value(valueString));
                    valueString = new String();
                }else{
                    if(current == '('){
                        nestIndex = i-1;
                        nests = 1;
                        while(i < raw.length() && nests != 0)
                            if(raw.charAt(i++) == '(')
                                nests++;
                            else if(raw.charAt(i-1) == ')')
                                nests--;
                        if(nests != 0)
                            throw new ExpressionExtractionFailureException("Incorrect expression brackets placement.");
                        valueString = raw.substring(nestIndex,i);
                    }else if(current == '"'){
                        nestIndex = i-1;
                        nests = 1;
                        while(i < raw.length() && nests != 0)
                            if(raw.charAt(i++) == '"')
                                nests--;
                        if(nests != 0)
                            throw new ExpressionExtractionFailureException("Incorrect expression brackets placement.");
                        valueString = raw.substring(nestIndex,i);
                    }else
                        valueString += current;
                }
            } else {
                if(operators.operatorWhitelist(current)) {
                    op += current;
                }else{
                    i--;
                    elem++;
                    operator = operators.operatorLookup(op);
                    op = new String();
                    if (operator == null)
                        throw new ExpressionExtractionFailureException("Invalid operator: " + op);
                    elements.add(operator, operator.getClass());
                }
            }
        }
        elements.add(value(valueString));
        if(elem%2 == 1)
            throw new ExpressionExtractionFailureException("Expected value in expression.");
    }
    public void extract() throws ExpressionExtractionFailureException{
        int i = 1, size = elements.size();
        isVarV = false;
        Parcel v = elements.get(0);
        if(i < size && v.getClassObject().equals(Var.class))
            v = ((Var)v.getObject()).getValue();
        while(i < size) {
            try {
                if(elements.get(i+1).getClassObject().equals(Var.class))
                    v = ((OperatorHandler.Operator) elements.get(i).getObject()).apply(v, ((Var) elements.get(i+1).getObject()).getValue());
                else
                    v = ((OperatorHandler.Operator) elements.get(i).getObject()).apply(v, elements.get(i + 1));
            }catch (UnexpectedDataTypeException e){
                throw new ExpressionExtractionFailureException("Failed to apply operator.",e);
            }
            i += 2;
        }
        this.parcel = v;
        if(v.getClassObject().equals(Integer.class)) {
            type = "Num";
            value = (int) v.getObject();
            bool = value % 2 == 1;
        }else if(v.getClassObject().equals(String.class)){
            type = "String";
            string = (String) v.getObject();
        }else if(v.getClassObject().equals(Var.class)){
            isVarV = true;
            var = (Var) v.getObject();
            if(var.getValue().getClassObject().equals(Integer.class)) {
                type = "Num";
                value = (int) var.getValue().getObject();
                bool = value % 2 == 1;
            }else if(var.getValue().getClassObject().equals(String.class)){
                type = "String";
                string = (String) var.getValue().getObject();
            }
        }
    }
    private Parcel value(String string) throws ExpressionExtractionFailureException{
        string = string.trim();
        Integer r;
        if(string == null)
            throw new ExpressionExtractionFailureException("Null value");
        if(string.startsWith("\""))
            return new Parcel(string.substring(1,string.length()-1), String.class);
        string = string.toLowerCase();
        if(string.startsWith("("))
            return new Parcel(new Expression(string.substring(1,string.length()-1)).getValue(), Integer.class);
        try{
            r = CheckHandler.returnFind(string);
            return new Parcel(r,Integer.class);
        } catch (CheckLookupException e){}
        if(VarManagement.getVar(string) != null)
            return new Parcel(VarManagement.getVar(string), Var.class);
        try {
            r = Integer.parseInt(string);
            return new Parcel(r,Integer.class);
        } catch (NumberFormatException e){
            return new Parcel(new Var(string), Var.class);
        }

    }

    public String getType() {
        return type;
    }

    public int getValue() {
        return value;
    }

    public boolean getBool(){
        return bool;
    }

    public String getString() {
        return string;
    }

    public Parcel getParcel() {
        return parcel;
    }

    public Var getVar() {
        return var;
    }

    public boolean isVar(){
        return isVarV;
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
        output += " | Value: " + value + " }";
        return output;
    }
}

