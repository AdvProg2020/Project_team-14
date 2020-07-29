package Model.Off;

import Model.Account.Customer;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import Model.RandomString;

import static Model.Storage.*;

public class SpecialOffCode extends RandomString implements Runnable, Serializable {

    //for example now every 3600 seconds one OffCode is given to a user
    private int timeInSeconds = 3600;
    private String specialOffCodeID;
    //it means for example now the OffCode given now is authentic for 24 hours
    private int durationInHour = 24;
    private int ceiling = 10000;
    private boolean activeness = false;
    private int percentage = 20;
    private int numberOfTimesItCanBeUsed = 1;
    private static final long serialVersionUID = 6529685098267757690L;

    //----[ updated newly ]----- constructor has parameter, change class parameter
    public SpecialOffCode(int period, int percentage, int ceiling, int numberOfTimesItCanBeUsed) {
        durationInHour = period * 24;
        this.percentage = percentage;
        this.ceiling = ceiling;
        this.numberOfTimesItCanBeUsed = numberOfTimesItCanBeUsed;
        allSpecialOffCodes.add(this);
        specialOffCodeID = createID("SpecialOffCode---");
        this.run();
    }

    //----[ updated newly ]----- constructor has parameter, change class parameter
    public SpecialOffCode() {
        allSpecialOffCodes.add(this);
        specialOffCodeID = createID("SpecialOffCode");
    }

    public void activate() {
        activeness = true;
        run();
    }

    public void deactivate() {
        activeness = false;
    }

    public void setTimeInSeconds(int timeInSeconds) {
        this.timeInSeconds = timeInSeconds;
    }

    public String getSpecialOffCodeID() {
        return specialOffCodeID;
    }

    public void setOffCodeDurationInHour(int durationInHour) {
        this.durationInHour = durationInHour;
    }

    public void setCeiling(int ceiling) {
        this.ceiling = ceiling;
    }

    public void setNumberOfTimesItCanBeUsed(int numberOfTimesItCanBeUsed) {
        this.numberOfTimesItCanBeUsed = numberOfTimesItCanBeUsed;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public int getTimeInSeconds() {
        return timeInSeconds;
    }

    public void setSpecialOffCodeID(String specialOffCodeID) {
        this.specialOffCodeID = specialOffCodeID;
    }

    public int getDurationInHour() {
        return durationInHour;
    }

    public void setDurationInHour(int durationInHour) {
        this.durationInHour = durationInHour;
    }

    public int getCeiling() {
        return ceiling;
    }

    public boolean isActiveness() {
        return activeness;
    }

    public void setActiveness(boolean activeness) {
        this.activeness = activeness;
    }

    public int getPercentage() {
        return percentage;
    }

    public int getNumberOfTimesItCanBeUsed() {
        return numberOfTimesItCanBeUsed;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Override
    public void run() {
        if (!activeness) {
            return;
        }
        Date nowDate = new Date();
        Date tomorrowDate = new Date(nowDate.getTime() + TimeUnit.HOURS.toMillis(durationInHour)); // Adds 24 hours
        Format formatter = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
        String today = formatter.format(nowDate);
        String tomorrow = formatter.format(tomorrowDate);
        try {
            if (Customer.getRandomUsername() != null) {
                new OffCode(today, tomorrow, percentage, ceiling, numberOfTimesItCanBeUsed,
                        new ArrayList<>(Collections.singleton(Customer.getRandomUsername())));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(timeInSeconds));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
