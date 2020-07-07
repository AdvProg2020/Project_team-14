package Controller.Security;

public class Methods {
    public static boolean checkStringLength(String command) {
        return command.length() >= 10000;
    }

    public static boolean mayContainScript(String command) {
        return command.contains("<") || command.contains(">") || command.contains("\\") || command.contains("/");
    }

}
