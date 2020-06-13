package Model.Account;

import java.io.*;
import java.util.ArrayList;

import static Model.Storage.*;

public class Boss extends Account implements Serializable {
    private String fatherBoss;

    public Boss(String username, String password, String firstName, String secondName, String Email, String telephone, String role) {
        super(username, password, firstName, secondName, Email, telephone, role);
    }

    public String getFatherBoss() {
        return fatherBoss;
    }

    public void setFatherBoss(String fatherBoss) {
        this.fatherBoss = fatherBoss;
    }

    public static boolean isThereBoss() {
        return !getAllBosses().isEmpty();
    }

    public int getCredit() {
        return 0;
    }

    public String toString() {
        return super.toString();
    }

}
