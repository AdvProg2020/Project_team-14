package Controller.SQL;

import java.io.*;
import java.util.Base64;

class FileManager {

    public FileManager() {
        File file = new File("database.Neuer");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void updateFile(String string) throws IOException {
        FileWriter myWriter = new FileWriter("database.Neuer");
        myWriter.write(SQL.encode(string));
        myWriter.close();
    }

    public byte[] readFromFile() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(
                new FileReader(new File("database.Neuer")));
        String s = bufferedReader.readLine();
        s = SQL.decode(s);
        return Base64.getDecoder().decode(s);
    }

}

