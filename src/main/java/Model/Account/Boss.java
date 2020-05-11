package Model.Account;

import java.io.*;

import static Model.Storage.*;

public class Boss extends Account implements Serializable {

    public Boss(String username, String password, String firstName, String secondName, String Email, String telephone, String role) {
        super(username, password, firstName, secondName, Email, telephone, role);
    }

    public static boolean isThereBoss() { return !getAllBosses().isEmpty(); }

    public int getCredit() {
        return 0;
    }

    public String toString() {
        return super.toString();
    }

}
