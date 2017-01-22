package model;

import java.util.List;

/**
 * Created by ltisseyre on 22/01/17.
 */
public class WeatherStation {
    String name;
    List<Measure> measures;
    List<FailedMeasure> failedMeasures;

    public List<Measure> getMeasures() {
        return measures;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addMeasure(Measure measure){
        this.measures.add(measure);
    }

    public void addFailedMeasure(FailedMeasure failedMeasure){
        this.failedMeasures.add(failedMeasure);
    }
}
