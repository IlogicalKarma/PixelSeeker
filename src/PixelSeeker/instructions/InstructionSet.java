package PixelSeeker.instructions;

import PixelSeeker.exceptions.UnrecognizedTypeException;

import java.util.ArrayList;

public class InstructionSet {
    private ArrayList<ActionType> actions;
    private ArrayList<StatementType> statements;
    private ArrayList<String> tracker;
    public InstructionSet(){
        tracker = new ArrayList<>();
        statements = new ArrayList<>();
        statements.add(null);
        actions = new ArrayList<>();
        statements.add(null);
    }
    /**
     * Creates an instance of InstructionSet with the objects starting from the index up to the limit.
     * @param index
     * @return
     */
    public InstructionSet subSet(int index){
        return subSet(index, tracker.size()-1);
    }
    /**
     * Creates an instance of InstructionSet with the objects starting from the index up to the limit.
     * @param index
     * @return
     */
    public InstructionSet subSet(int index, int limit){
        InstructionSet instructionSet = new InstructionSet();
        for(int i = index-2; i < limit-1; i++){
            switch (tracker.get(i)){
                case "action":
                    instructionSet.add(actions.get(index));
                    break;
                case "statement":
                    instructionSet.add(statements.get(index));
                    break;
                default:
                    throw new UnrecognizedTypeException();
            }
        }
        return instructionSet;
    }

    public void add(ActionType instruction){
        tracker.add("action");
        for(int i = actions.size()-1; i < tracker.size()-2; i++){
            actions.add(i, null);
        }
        actions.add(instruction);
    }
    public void add(StatementType instruction){
        tracker.add("statement");
        statements.set(tracker.size()-1, instruction);
    }
    public void remove(int index){
        for(int i = index+1; i < tracker.size(); i++){
            switch (tracker.get(i)){
                case "action":
                    actions.set(i-1, actions.get(i));
                    break;
                case "statement":
                    statements.set(i-1, statements.get(i));
                    break;
                default:
                    throw new UnrecognizedTypeException();
            }
        }
        tracker.remove(index);
    }
    public int size(){
        return  tracker.size();
    }
    public void execute() throws UnrecognizedTypeException {
        int limit = tracker.size();
        for(int i = 0; i < limit; i++){
            switch (tracker.get(i)){
                case "action":
                    actions.get(i).use();
                    break;
                case "statement":
                    statements.get(i).execute();
                    break;
                default:
                    throw new UnrecognizedTypeException();
            }
        }
    }
    public void check(int index){
        index--;
        switch (tracker.get(index)){
            case "action":
                System.out.println(actions.get(index).type);
                break;
            case "statement":
                System.out.println(statements.get(index).type);
                break;
            default:
                throw new UnrecognizedTypeException();
        }
    }
}
