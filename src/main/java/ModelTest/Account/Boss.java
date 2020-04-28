package ModelTest.Account;

import java.io.*;

import static ModelTest.Storage.*;

public class Boss extends Account implements Serializable {

    public Boss(String username, String password, String firstName, String secondName, String Email, String telephone, String role) {
        super(username, password, firstName, secondName, Email, telephone, role);
    }

    public static boolean isThereBoss() { return !getAllBosses().isEmpty(); }

    public String toString() {
        return super.toString();
    }

}
