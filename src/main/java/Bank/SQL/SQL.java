package Bank.SQL;

import Model.RandomString;

import java.sql.*;
import java.util.Base64;

public class SQL {
    private Connection connection;
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

    public SQL() {
        try {
            String DB_URL = "jdbc:mysql://localhost/";
            String USER = "root";
            String PASS = "";
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            connection.createStatement();
            Statement stmt;
            stmt = connection.createStatement();
            String sql = "CREATE DATABASE lahm";
            stmt.executeUpdate(sql);
        } catch (Exception s) {
            System.out.println(s.getMessage());
        }
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/lahm?" + "user=root&password=");
            Statement stmt = connection.createStatement();
            String sql;
            sql = "CREATE TABLE taylor " + "(id INTEGER not NULL, " + "name blob, " + " PRIMARY KEY ( id ))";
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
            data = encode(data);
            PreparedStatement statement = connection.prepareStatement("INSERT  INTO taylor  (id,name) values (?,?)");
            statement.executeUpdate("DELETE FROM taylor");
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
        String sql = "SELECT id, name FROM taylor";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            rs.getInt("id");
            string = rs.getString("name");
        }
        string = decode(string);
        rs.close();
        return Base64.getDecoder().decode(string);
    }

    public void updateProgramme() {
        try {
            insert(Object.object.serialise(new Object()));
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

    private String encode(String string) {
        String result = RandomString.getRandomString(100) + string;
        result = base64Encoder.encodeToString(result.getBytes());
        result = ".xs,dsghjkdf,a3uih238ewajnksdhjf hsjkd jdzjfhgs " + result;
        result = base64Encoder.encodeToString(result.getBytes());
        result = new StringBuilder(result).reverse().toString();
        return result;
    }

    private String decode(String string) {
        string = new StringBuilder(string).reverse().toString();
        String result = new String(Base64.getDecoder().decode(string));
        result = result.substring(".xs,dsghjkdf,a3uih238ewajnksdhjf hsjkd jdzjfhgs ".length());
        result = new String(Base64.getDecoder().decode(result));
        result = result.substring(100);
        return result;
    }

}
