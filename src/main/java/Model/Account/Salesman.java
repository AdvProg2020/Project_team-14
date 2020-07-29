package Model.Account;

import Model.Confirmation;
import Model.Request.Request;
import Model.Storage;

import java.io.*;
import java.util.ArrayList;

public class Salesman extends Account implements Serializable {
    private String company;
    private Confirmation confirmationState;
    private int credit;
    private ArrayList<String> requestIDs;
    private ArrayList<String> commercials = new ArrayList<>();
    private static final long serialVersionUID = 6529685098267757690L;

    public Salesman(String username, String password, String firstName, String secondName, String Email, String telephone, String role, String company, int credit) {
        super(username, password, firstName, secondName, Email, telephone, role);
        new Request(this.getUsername());
        this.company = company;
        this.credit = credit;
        confirmationState = Confirmation.CHECKING;
    }

    public ArrayList<String> getCommercials() {
        return commercials;
    }

    public boolean isProductOnCommercial(String productID) {
        return commercials.contains(productID);
    }

    public static ArrayList<String> getAllCommercials() {
        ArrayList<String> arrayList = new ArrayList<>();
        for (Salesman salesman : Storage.getAllSalesmen()) {
            arrayList.addAll(salesman.getCommercials());
        }
        return arrayList;
    }

    public Confirmation getConfirmationState() {
        return confirmationState;
    }

    public void setConfirmationState(Confirmation confirmationState) {
        this.confirmationState = confirmationState;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public boolean isConfirmed() {
        return confirmationState.equals(Confirmation.ACCEPTED);
    }

    public String toStringForRequest() {
        return "First Name: " + this.getFirstName() + " Last Name: " + this.getSecondName() +
                " Username: " + this.getUsername() + " Company: " + this.getCompany();
    }

    public ArrayList<String> getRequestIDs() {
        return requestIDs;
    }

    public void setRequestIDs(ArrayList<String> requestIDs) {
        this.requestIDs = requestIDs;
    }

    public void setCommercials(ArrayList<String> commercials) {
        this.commercials = commercials;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String toString() {
        String result = super.toString();
        result += "Company: " + this.getCompany() + "\n";
        result += "Credit: " + credit + "\n";
        result += "Confirmation State: " + this.confirmationState.toString() + "\n";
        return result;
    }

}
