package DCR;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class ConChecker {
    public static void main(String[] args) throws Exception {
        int[] a = new int[]{1,2,3};

        HashMap<String, List<String>> logMaps = ParseLog.getLogs(args[0]);
        PatternGenerator patternGenerator = new PatternGenerator();

        File file = new File("result.txt");
        if(!file.exists()){
            file.createNewFile();
        }
        FileWriter fileWritter = new FileWriter(file.getName(),true);
        BufferedWriter bufferWritter = new BufferedWriter(fileWritter);

        for (int i = 1; i<=4; i++){
            Method method = patternGenerator.getClass().getMethod("pattern"+i);
//            List<String> pattern = new ArrayList<>();
            List<String> pattern = (List<String>) method.invoke(patternGenerator);
            System.out.println("pattern "+ i +" is:");
            bufferWritter.write("pattern "+ i +" is:\n");
            for (String p: pattern){
                System.out.println(p);
            }
            System.out.println("*********The result of pattern "+i+" is: *************");
            bufferWritter.write("*********The result of pattern "+i+" is: *************\n");


            int total = 0;
            int accept = 0;
            int reject = 0;
            for (String s : logMaps.keySet()){
                DCRGraph dcrGraph = new DCRGraph();
                dcrGraph = ParseDCR.parseInput(dcrGraph, pattern);
                if (isAccept(bufferWritter, s, logMaps.get(s), dcrGraph)){
                    accept++;
                }
                else {
//                    System.out.println(s+ ": not accepted");
//                    bufferWritter.write(s+"\n");
                    reject++;
                }
                total++;
            }
            System.out.println("total amount is: "+total);
            System.out.println("reject amount is: " + reject);
            System.out.println("accept amount is: " + accept+"\n\n\n");
            bufferWritter.write("total amount is: "+total+"\n");
            bufferWritter.write("reject amount is: " + reject+"\n");
            bufferWritter.write("accept amount is: " + accept+"\n\n\n");

        }
        bufferWritter.close();

    }

    private static boolean isAccept(BufferedWriter bufferedWriter, String logName, List<String> logs, DCRGraph dcrGraph) throws IOException {
        for (String log: logs){
            if (!dcrGraph.enabled(dcrGraph.marking, log)){
                System.out.println("In " +logName+ ", Event " + log + " is not enabled, so reject.");
                bufferedWriter.write("In " +logName+ ", Event " + log + " is not enabled, so reject.\n");

                return false;
            }
            else {
                dcrGraph.marking = dcrGraph.execute(dcrGraph.marking, log);
            }
        }
        if (!dcrGraph.isAccepting()){
            System.out.println("In " +logName+ ", Final state rejects.");
            bufferedWriter.write("In " +logName+ ", Final state rejects.\n");
        }
        return dcrGraph.isAccepting();
    }
}
