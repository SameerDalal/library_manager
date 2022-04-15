
import java.sql.*;
import java.sql.DriverManager;


public class MySQL {

    Connection connection = null;
    Statement statement = null;

    public void SQLConnector() {

        // registering JDBC Driver

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Connecting to Database...\n");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_database", "root", "");
            System.out.println("Connected to Database Successfully!\n");
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        } catch (Exception e) {
            System.out.println(e + "\n");
        }
    }

    public void SQLDisconnector() {
        try {
            connection.close();
        } catch (SQLException se) {
            System.out.println(se + "\n");
            System.out.println("Disconnected from Database unsuccessfully!\n");
        }
    }

    public String addDatabaseSQLString(int ID1, String val1, String val2, String val3, String table1) {
        return "INSERT INTO " + table1 + " " + "VALUES (" + ID1 + "," + "'" + val1 + "','" + val2 + "'," + val3 + ")";
    }

    public String deleteDatabaseSQLString(String table2, String rowName, String rowValue) {
        System.out.println("DELETE FROM " + table2 + " WHERE " + rowName + "='" + rowValue + "'\n");
        return "DELETE FROM " + table2 + " WHERE " + rowName + "='" + rowValue + "'";

    }

    public static String sqlSplitter(String sql2) {
        String[] sqlSplit = sql2.split(",", 2);
        return sqlSplit[1];

    }

    public void addToDatabase(int ID, String val1, String val2, String val3, String table) {
        String sql = "";
        int counter = 0;
        try {
            sql = addDatabaseSQLString(ID, val1, val2, val3, table);
            statement.executeUpdate(sql);
        } catch (SQLException se) {
            System.out.println(se + "\n");
            System.out.println("A duplicate entry has been added, please try again!\n");
            counter++;
        } finally {
            if (counter == 0) {
                System.out.println("Added " + sqlSplitter(sql) + " to the Database!\n");
            } else {
                System.out.println("Data: " + sqlSplitter(sql) + " has NOT been successfully added!\n");
            }
        }
    }

    public void deleteFromDatabase(String table3, String rowName1, String rowValue1) {
        String sql1 = "";
        int counter1 = 0;
        try {
            sql1 = deleteDatabaseSQLString(table3, rowName1, rowValue1);
            statement.executeUpdate(sql1);

        } catch (SQLException se) {
            System.out.println(se);
            counter1++;
        } finally {
            if (counter1 == 0) {
                System.out.println("Deleted from the Database!\n");
            } else {
                System.out.println("Data has NOT been successfully deleted!\n");
            }
        }
    }

    public int getUserAccountData() {
        int id_value = 1;
        try {
            ResultSet rs = statement.executeQuery("select * from users ");
            rs.absolute(1);
            id_value = rs.getInt(1);

        } catch (SQLException se) {
            System.out.println(se);
        }
        return id_value;

    }
}
