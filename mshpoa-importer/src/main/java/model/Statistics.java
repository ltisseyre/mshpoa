package model;

/**
 * Created by ltisseyre on 23/01/17.
 */
public class Statistics {
    private Double min;
    private Double max;
    private Double avg;

    private int avgCount;

    public Statistics(Double min, Double max, Double avg) {
        this.min = min;
        this.max = max;
        this.avg = avg;
        avgCount = 1;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    public Double getAvg() {
        return avg;
    }

    public void setAvg(Double avg) {
        this.avg = avg;
    }

    public void updateStat(Double value){
        if(value != null){
            //min
            if(min != null){
                if(value<min){
                    min = value;
                }
            } else {
                min = value;
            }

            //max
            if(max != null){
                if(value>max){
                    max = value;
                }
            } else {
                max = value;
            }

            //avg
            if(avg != null){
                avg = (avg*avgCount + value)/(avgCount+1);
            } else {
                avg = value;
            }
            avgCount++;
        }
    }
}
