package DCR;

import java.util.HashSet;
import java.util.List;

public class ParseDCR {

    public static DCRGraph parseInput(DCRGraph dcrGraph, List<String> inputStrings){
        for (String inputString : inputStrings){
            if (inputString.contains("(")){
                String[] splitInput = inputString.split("\\(");
                String eventName = splitInput[0];
                dcrGraph.events.add(eventName);
                String[] states = splitInput[1].substring(0, splitInput[1].length()-1).split(",");
                int executed = Integer.parseInt(states[0]);
                int included = Integer.parseInt(states[1]);
                int pending = Integer.parseInt(states[2]);

                if (executed ==1 ){
                    dcrGraph.marking.executed.add(eventName);
                }
                if (included ==1 ){
                    dcrGraph.marking.included.add(eventName);
                }
                if (pending ==1 ){
                    dcrGraph.marking.pending.add(eventName);
                }
            }

            else if (inputString.contains("-->*")){
                String[] splitInput = inputString.split("-->\\*");
                if (dcrGraph.getConditionsFor().containsKey(splitInput[1])){
                    dcrGraph.getConditionsFor().get(splitInput[1]).add(splitInput[0]);
                }
                else {
                    HashSet<String> temp = new HashSet<>();
                    temp.add(splitInput[0]);
                    dcrGraph.getConditionsFor().put(splitInput[1],temp);
                }

            }

            else if (inputString.contains("*-->")){
                String[] splitInput = inputString.split("\\*-->");
                if (dcrGraph.getResponsesTo().containsKey(splitInput[0])){
                    dcrGraph.getResponsesTo().get(splitInput[0]).add(splitInput[1]);
                }
                else {
                    HashSet<String> temp = new HashSet<>();
                    temp.add(splitInput[1]);
                    dcrGraph.getResponsesTo().put(splitInput[0],temp);
                }
            }

            else if (inputString.contains("-->%")){
                String[] splitInput = inputString.split("-->%");
                if (dcrGraph.getExcludesTo().containsKey(splitInput[0])){
                    dcrGraph.getExcludesTo().get(splitInput[0]).add(splitInput[1]);
                }
                else {
                    HashSet<String> temp = new HashSet<>();
                    temp.add(splitInput[1]);
                    dcrGraph.getExcludesTo().put(splitInput[0],temp);
                }
            }

            else if (inputString.contains("-->+")){
                String[] splitInput = inputString.split("-->\\+");
                if (dcrGraph.getIncludesTo().containsKey(splitInput[0])){
                    dcrGraph.getIncludesTo().get(splitInput[0]).add(splitInput[1]);
                }
                else {
                    HashSet<String> temp = new HashSet<>();
                    temp.add(splitInput[1]);
                    dcrGraph.getIncludesTo().put(splitInput[0],temp);
                }
            }

            else if (inputString.contains("--><>")){
                String[] splitInput = inputString.split("--><>");
                if (dcrGraph.getMilestonesFor().containsKey(splitInput[1])){
                    dcrGraph.getMilestonesFor().get(splitInput[1]).add(splitInput[0]);
                }
                else {
                    HashSet<String> temp = new HashSet<>();
                    temp.add(splitInput[0]);
                    dcrGraph.getMilestonesFor().put(splitInput[1],temp);
                }
            }
        }

        return dcrGraph;
    }
}
