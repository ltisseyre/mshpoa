import model.Measure;
import model.Statistics;
import model.WeatherStation;
import statistics.StatisticsHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ltisseyre on 22/01/17.
 */

public class Importer {

    /**
     * main method, it take at least one argument (file path)
     * @param args
     */
    public static void main(String [] args){
        if(args.length < 1){
            throw new IllegalArgumentException("At least one file is required.");
        }

        try{
            List<WeatherStation> stations = new ArrayList<>();

            //load configuration
            FileImporter fileImporter = new FileImporter();

            //import each file in args
            for(String arg : args){
                List<WeatherStation> s = fileImporter.importFile(arg);
                if(s != null){
                    stations.addAll(s);
                }
            }

            //Displays statistics in standard output (stdout)
            StatisticsHelper statisticsHelper = new StatisticsHelper();
            statisticsHelper.computeStats(stations);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
