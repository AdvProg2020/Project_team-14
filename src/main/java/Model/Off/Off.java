package Model.Off;

import Model.RandomString;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Off implements Serializable {
    protected Date start;
    protected Date end;
    protected int percentage;

    //start and end must be in format "dd-MM-yyyy HH-mm-ss" otherwise exception will be thrown

    public Off(String start, String end, int percentage) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
        try {
            this.start = formatter.parse(start);
            this.end = formatter.parse(end);
        } catch (ParseException e) {
            //e.printStackTrace();
        }
        this.percentage = percentage;
    }

    public void setEnd(String end) /*throws ParseException*/ {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
        try {
            this.end = formatter.parse(end);
        } catch (ParseException e) {
            //e.printStackTrace();
        }
    }

    public void setStart(String start) /*throws ParseException*/ {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
        try {
            this.start = formatter.parse(start);
        } catch (ParseException e) {
            //e.printStackTrace();
        }
    }

    public Date getEnd() {
        return end;
    }

    public Date getStart() {
        return start;
    }

    public void setPercentage(String percentage) {
        this.percentage = Integer.parseInt(percentage);
    }

    public int getPercentage() {
        return percentage;
    }

    public boolean isAuthenticAccordingToDate() {
        Date now = new Date();
        return now.before(end) && now.after(start);
    }

    public String toString() {
        String result = "";
        result += "Percentage: " + percentage + "\n";
        result += "Start Date: " + start.toString() + "\n";
        result += "End Date: " + end.toString() + "\n";
        return result;
    }
}
