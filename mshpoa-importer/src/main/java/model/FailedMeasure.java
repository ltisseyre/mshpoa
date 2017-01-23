package model;

/**
 * Created by ltisseyre on 22/01/17.
 */
public class FailedMeasure {
    private String failedRow;
    private String expectedRow;
    private String message;

    public FailedMeasure(String failedRow, String expectedRow, String message) {
        this.failedRow = failedRow;
        this.expectedRow = expectedRow;
        this.message = message;
    }

    public String getFailedRow() {
        return failedRow;
    }

    public void setFailedRow(String failedRow) {
        this.failedRow = failedRow;
    }

    public String getExpectedRow() {
        return expectedRow;
    }

    public void setExpectedRow(String expectedRow) {
        this.expectedRow = expectedRow;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
