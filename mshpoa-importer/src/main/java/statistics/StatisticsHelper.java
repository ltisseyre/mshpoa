package statistics;

import model.Measure;
import model.Statistics;
import model.WeatherStation;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ltisseyre on 23/01/17.
 */
public class StatisticsHelper {

    public void computeStats(List<WeatherStation> stations) {
        int weatherStationCount = stations.size();
        int failedMeasures = 0;
        HashMap<String, Statistics> stats = new HashMap<>();

        for (WeatherStation station : stations) {
            failedMeasures += station.getFailedMeasures().size();
            for (Measure measure : station.getMeasures()) {
                Statistics stat;
                String code = measure.getCode();
                Double value = measure.getValue();

                if(stats.containsKey(code)){
                    //update statistic
                    stat = stats.get(code);
                    stat.updateStat(value);
                } else {
                    //create new statistic
                    stat = new Statistics(value, value, value);
                }
                stats.put(code, stat);
            }
        }

        System.out.println("weatherStationCount : " + weatherStationCount);
        System.out.println("failedMeasures : " + failedMeasures);
        for (String s : stats.keySet()) {
            Statistics stat = stats.get(s);
            System.out.println("Sensor type : " + s);
            System.out.println("min : " + stat.getMin());
            System.out.println("max : " + stat.getMax());
            System.out.println("avg : " + stat.getAvg());
        }
    }
}
