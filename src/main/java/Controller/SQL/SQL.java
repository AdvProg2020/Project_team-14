package Controller.SQL;

import java.sql.*;

public class SQL {
    private Connection connection;

    public SQL() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/test?" + "user=root&password=");
            Statement stmt = connection.createStatement();
            String sql;
            sql = "CREATE TABLE data " +
                    "(id INTEGER not NULL, " +
                    "name blob, " +
                    " PRIMARY KEY ( id ))";
            stmt.executeUpdate(sql);
        } catch (Exception s) {
            System.out.println(s.getMessage());
        }
    }

    public void insert(String string) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT  INTO data  (id,name) values (?,?)");
            statement.executeUpdate("DELETE FROM data");
            statement.setInt(1, 1);
            statement.setString(2, string);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException s) {
            System.out.println(s.getMessage());
        }
    }

    public String show() throws SQLException {
        String string = "";
        String sql = "SELECT id, name FROM data";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            rs.getInt("id");
            string = rs.getString("name");
        }
        rs.close();
        return string;
    }

    public void updateProgramme() {
        try {
            insert(Object.object.serialise());
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
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
