package DCR;

import com.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ParseLog {
    public static final Character SPILIT = ';';
    public static  HashSet<String> allEvents = new HashSet<>();

    public static HashMap<String, List<String>> getLogs(String path){
        CSVReader reader;
        HashMap<String, List<String>> logMaps = new HashMap<>();
        try {
            reader = new CSVReader(new FileReader(path), SPILIT);
            String[] line = reader.readNext();
            while ((line = reader.readNext())!=null){
                allEvents.add(line[2]);

                if (!logMaps.containsKey(line[0])){
                    List<String> eventNames =  new ArrayList<>();
                    eventNames.add(line[2]);
                    logMaps.put(line[0], eventNames);
                }
                else {
                    logMaps.get(line[0]).add(line[2]);
                }
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return logMaps;
    }
}
