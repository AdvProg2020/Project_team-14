package Controller.SQL;

import java.sql.*;
import java.util.Base64;

public class SQL {
    private Connection connection;

    public SQL() {
        try {
            //Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/test?" + "user=root&password=");
            Statement stmt = connection.createStatement();
            String sql;
            sql = "CREATE TABLE Neuer " + "(id INTEGER not NULL, " + "name blob, " + " PRIMARY KEY ( id ))";
            stmt.executeUpdate(sql);
        } catch (Exception s) {
            System.out.println(s.getMessage());
        }
    }

    private void close() {
        try {
            connection.close();
        } catch (Exception s) {
            s.printStackTrace();
        }
    }

    public void insert(String data) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT  INTO Neuer  (id,name) values (?,?)");
            statement.executeUpdate("DELETE FROM Neuer");
            statement.setInt(1, 1);
            statement.setString(2, data);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException s) {
            s.printStackTrace();
        }
    }

    public byte[] show() throws SQLException {
        String string = "";
        String sql = "SELECT id, name FROM Neuer";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            rs.getInt("id");
            string = rs.getString("name");
        }
        rs.close();
        return Base64.getDecoder().decode(string);
    }

    public void updateProgramme() {
        try {
            new Object();
            insert(Object.object.serialise());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void startProgramme() {
        try {
            Object.deserialize(show());
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
}
