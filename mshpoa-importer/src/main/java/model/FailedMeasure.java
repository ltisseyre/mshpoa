package model;

/**
 * Created by ltisseyre on 22/01/17.
 */
public class FailedMeasure {
    String failedRow;
    String expectedRow;

    public FailedMeasure(String failedRow, String expectedRow) {
        this.failedRow = failedRow;
        this.expectedRow = expectedRow;
    }
}
