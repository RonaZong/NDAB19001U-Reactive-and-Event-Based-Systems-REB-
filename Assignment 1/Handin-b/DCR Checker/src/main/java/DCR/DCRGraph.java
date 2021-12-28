package DCR;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class DCRGraph {
    protected HashSet<String> events = new HashSet<>();

    private HashMap<String, HashSet<String>> conditionsFor = new HashMap<>();
    private HashMap<String, HashSet<String>> milestonesFor = new HashMap<>();
    private HashMap<String, HashSet<String>> responsesTo = new HashMap<>();
    private HashMap<String, HashSet<String>> excludesTo = new HashMap<>();
    private HashMap<String, HashSet<String>> includesTo = new HashMap<>();

    public DCRMarking marking = new DCRMarking();

    public HashSet<String> getEvents() {
        return events;
    }

    public void setEvents(HashSet<String> events) {
        this.events = events;
    }

    public HashMap<String, HashSet<String>> getConditionsFor() {
        return conditionsFor;
    }

    public void setConditionsFor(HashMap<String, HashSet<String>> conditionsFor) {
        this.conditionsFor = conditionsFor;
    }

    public HashMap<String, HashSet<String>> getMilestonesFor() {
        return milestonesFor;
    }

    public void setMilestonesFor(HashMap<String, HashSet<String>> milestonesFor) {
        this.milestonesFor = milestonesFor;
    }

    public HashMap<String, HashSet<String>> getResponsesTo() {
        return responsesTo;
    }

    public void setResponsesTo(HashMap<String, HashSet<String>> responsesTo) {
        this.responsesTo = responsesTo;
    }

    public HashMap<String, HashSet<String>> getExcludesTo() {
        return excludesTo;
    }

    public void setExcludesTo(HashMap<String, HashSet<String>> excludesTo) {
        this.excludesTo = excludesTo;
    }

    public HashMap<String, HashSet<String>> getIncludesTo() {
        return includesTo;
    }

    public void setIncludesTo(HashMap<String, HashSet<String>> includesTo) {
        this.includesTo = includesTo;
    }

    public Boolean enabled(final DCRMarking marking, final String event){
        if (!events.contains(event)){
            return true;
        }

        if (!marking.included.contains(event)){
            return false;
        }

        try {
            final Set<String> inccon = new HashSet<>(conditionsFor.get(event));
            inccon.retainAll(marking.included);
            if(!marking.executed.containsAll(inccon)){
                return false;
            }
        }
        catch (Exception e){

        }


        try {
            final Set<String> incmil = new HashSet<>(milestonesFor.get(event));
            incmil.retainAll(marking.included);
            for (final String p:marking.pending){
                if (incmil.contains(p)){
                    return false;
                }
            }
        }catch (Exception e){

        }
        return true;
    }

    public DCRMarking execute(final DCRMarking marking, final String event){
        if(!events.contains(event)){
            return marking;
        }

        if (!this.enabled(marking, event)){
            return marking;
        }

        DCRMarking result = new DCRMarking();
        result.executed = new HashSet<>(marking.executed);
        result.included = new HashSet<>(marking.included);
        result.pending = new HashSet<>(marking.pending);

        result.executed.add(event);

        result.pending.remove(event);
        try {
            result.pending.addAll(responsesTo.get(event));
        }catch (Exception e){

        }
        try {
            result.included.removeAll(excludesTo.get(event));
        }catch (Exception e){}
        try {
            result.included.addAll(includesTo.get(event));
        }catch (Exception e){}

        return result;

    }

    public Set<String> getIncludedPending(){
        HashSet<String> result = new HashSet<>(marking.included);

        result.retainAll(marking.pending);
        return result;
    }

    public boolean isAccepting(){
        return getIncludedPending().isEmpty();
    }
}

