package model;

import java.util.Date;

/**
 * Created by ltisseyre on 22/01/17.
 */
public class Measure {
    private String code;
    private String unit;
    private Double value;
    private Date date;

    public Measure(String code) {
        this.code = code;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public String getUnit() {
        return unit;
    }

    public Double getValue() {
        return value;
    }

    public Date getDate() {
        return date;
    }
}
