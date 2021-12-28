package DCR;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class PatternGenerator {
    public static HashSet<String> allEvents;

    public PatternGenerator(){
        allEvents = ParseLog.allEvents;
    }

    public List<String> pattern1(){
//         pattern 1.
        List<String> pattern1 = new ArrayList<>();
        pattern1.add("Fill out application(0,1,0)");
        for (String event: allEvents){
            if (!event.equals("Fill out application")){
                pattern1.add("Fill out application"+"-->*"+event);
                pattern1.add(event+"(0,1,0)");
            }
        }
        return pattern1;
    }

    public List<String> pattern2(){
        // pattern 2.
        List<String> pattern2 = new ArrayList<>();
//        for (String event: allEvents){
//            pattern2.add(event+"(0,1,0)");
//        }
        pattern2.add("Reject(0,1,0)");
        pattern2.add("Applicant informed(0,1,0)");
        pattern2.add("Change phase to Abort(0,1,0)");
        pattern2.add("Reject*-->Applicant informed");
        pattern2.add("Reject*-->Change phase to Abort");
        return pattern2;
    }

    public List<String > pattern3(){
        List<String> pattern3 = new ArrayList<>();
        pattern3.add("First payment(0,1,1)");
        pattern3.add("First payment-->%First payment");
        return pattern3;
    }

    public List<String> pattern4(){
        List<String> pattern4 = new ArrayList<>();
        pattern4.add("Lawyer Review(0,1,0)");
        pattern4.add("Architect Review(0,1,0)");
        pattern4.add("Lawyer Review-->%Architect Review");
        pattern4.add("Architect Review-->%Lawyer Review");
        return pattern4;
    }
}
