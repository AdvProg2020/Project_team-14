package Model.Account;

import java.io.*;
import java.util.ArrayList;

import static Model.Storage.*;

public class Boss extends Account implements Serializable {
    private String fatherBoss;
    private static final long serialVersionUID = 6529685098267757690L;

    public Boss(String username, String password, String firstName, String secondName, String Email, String telephone, String role) {
        super(username, password, firstName, secondName, Email, telephone, role);
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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
