package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ltisseyre on 22/01/17.
 */
public class WeatherStation {
    String name;
    List<Measure> measures;
    List<FailedMeasure> failedMeasures;

    public WeatherStation() {
        this.measures = new ArrayList<>();
        this.failedMeasures = new ArrayList<>();
    }

    public List<FailedMeasure> getFailedMeasures() {
        return failedMeasures;
    }

    public String getName() {
        return name;
    }

    public List<Measure> getMeasures() {
        return measures;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMeasures(List<Measure> measures) {
        this.measures = measures;
    }

    public void setFailedMeasures(List<FailedMeasure> failedMeasures) {
        this.failedMeasures = failedMeasures;
    }

    public void addMeasure(Measure measure){
        this.measures.add(measure);
    }

    public void addFailedMeasure(FailedMeasure failedMeasure){
        this.failedMeasures.add(failedMeasure);
    }
}
