package Model.Off;

import Model.Account.Customer;
import Model.RandomString;
import Model.Storage;

import java.awt.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;

import static Model.Storage.*;

public class OffCode extends Off implements Serializable {
    private int ceiling;
    private String offCodeID;
    private int numberOfTimesCanBeUsed;
    private ArrayList<String> userNamesCanUseIt = new ArrayList<>();
    private static final long serialVersionUID = 6529685098267757690L;

    public OffCode(String start, String end, int percentage, int ceiling, int numberOfTimesCanBeUsed, ArrayList<String> userNamesCanUseIt) {
        super(start, end, percentage);
        offCodeID = RandomString.createID("OffCode");
        allOffCodes.add(this);
        this.ceiling = ceiling;
        this.numberOfTimesCanBeUsed = numberOfTimesCanBeUsed;
        this.userNamesCanUseIt.addAll(userNamesCanUseIt);
        for (String username : userNamesCanUseIt) {
            Customer customer = ((Customer) Storage.getAccountWithUsername(username));
            assert customer != null;
            customer.addOffCode(this.offCodeID, numberOfTimesCanBeUsed);
        }

        //because IDs are generally too long I decided to to make the length the random String 5 in order for more comfort
    }

    private void updateWithNewUsername(String oldUsername, String newUsername) {
        for (String username : userNamesCanUseIt) {
            if (username.equals(oldUsername)) {
                userNamesCanUseIt.remove(oldUsername);
                userNamesCanUseIt.add(newUsername);
            }
        }
    }

    public static void updateAllOffCodesWithNewUsername(String oldUsername, String newUsername) {
        for (OffCode offCode : allOffCodes) {
            offCode.updateWithNewUsername(oldUsername, newUsername);
        }
    }

    public static ArrayList<OffCode> getAllCustomerOffCodesByUsername(String username) {
        ArrayList<OffCode> customerOffCodes = new ArrayList<>();
        for (OffCode offCode : allOffCodes) {
            if (offCode.canCustomerUseItWithUsername(username)) customerOffCodes.add(offCode);
        }
        return customerOffCodes;
    }

    public static OffCode getOffCodeByID(String offCodeID) {
        for (OffCode offCode : allOffCodes) {
            if (offCode.offCodeID.equals(offCodeID)) {
                return offCode;
            }
        }
        return null;
    }

    public void setCeiling(int ceiling) {
        this.ceiling = ceiling;
    }

    public void setNumberOfTimesCanBeUsed(int numberOfTimesCanBeUsed) {
        this.numberOfTimesCanBeUsed = numberOfTimesCanBeUsed;
    }

    public int getNumberOfTimesCanBeUsed() {
        return numberOfTimesCanBeUsed;
    }

    public boolean canCustomerUseItWithUsername(String username) {
        return userNamesCanUseIt.contains(username);
    }

    public static boolean isThereOffCodeWithID(String offCodeID) {
        return getOffCodeByID(offCodeID) != null;
    }

    private boolean isAuthenticAccordingToNumberOfTimesCanBeUsed() {
        return this.numberOfTimesCanBeUsed > 0;
    }

    public String getOffCodeID() {
        return offCodeID;
    }

    public int getCeiling() {
        return ceiling;
    }

    //it checks whether the code is authentic or not by checking both date and number of times used
    //but before using this method we have to make sure that the codeID is even valid

    public boolean isAuthentic() {
        return isAuthenticAccordingToNumberOfTimesCanBeUsed() && isAuthenticAccordingToDate();
    }

    //this method receives an integer and return the amount of final price after using offCode on that

    private int getFinalPrice(int price) {
        if ((price * percentage) / 100 > ceiling) {
            return price - ceiling;
        } else {
            return price - (price * percentage) / 100;
        }
    }

    public static int getFinalPrice(int price, String offCodeID) {
        OffCode offCode = OffCode.getOffCodeByID(offCodeID);
        assert offCode != null;
        return offCode.getFinalPrice(price);
    }

    public static boolean isOffCodeAuthenticWithID(String offCodeID) {
        if (getOffCodeByID(offCodeID) == null) {
            return false;
        }
        OffCode offCode = getOffCodeByID(offCodeID);
        assert offCode != null;
        return offCode.isAuthentic();
    }

    //the method is boolean and if number of times that can be used is a positive number reduces that
    //by one and then return true otherwise doesn't do anything and just return false

    public void reduceNumberOfTimesItCanBeUsed() {
        if (numberOfTimesCanBeUsed > 0) {
            numberOfTimesCanBeUsed--;
        }
    }

    //return string formatted off code for boss menu


    public void setOffCodeID(String offCodeID) {
        this.offCodeID = offCodeID;
    }

    public ArrayList<String> getUserNamesCanUseIt() {
        return userNamesCanUseIt;
    }

    public void setUserNamesCanUseIt(ArrayList<String> userNamesCanUseIt) {
        this.userNamesCanUseIt = userNamesCanUseIt;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String toStringForBoss() {
        StringBuilder result = new StringBuilder(super.toString());
        result.append("Max:").append(ceiling).append("\n");
        result.append("Number Of Times Can Be Still Used:").append(numberOfTimesCanBeUsed).append("\n");
        result.append("Users That Can Use It:" + "\n");
        for (String user : userNamesCanUseIt) {
            result.append(user).append("\n");
        }
        return result.toString();
    }
}
