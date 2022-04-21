
import java.sql.*;
import java.sql.DriverManager;


public class SQLConnector {

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
        return "DELETE FROM " + table2 + " WHERE " + rowName + "='" + rowValue + "'";
    }

    public void addToDatabase(int ID, String val1, String val2, String val3, String table) {
        int counter = 0;
        try {
            statement.executeUpdate(addDatabaseSQLString(ID,val1, val2, val3, table));
        } catch (SQLException se) {
            System.out.println(se + "\n");
            System.out.println("A duplicate entry has been added, please try again!\n");
            counter++;
        } finally {
            if (counter == 0) {
                System.out.println("Added " + ID + " " + val1 + " " + val2 + " " + val3 + " to the Database!\n");
            } else {
                System.out.println("Data: " + ID + " " + val1 + " " + val2 + " " + val3 + " has NOT been successfully added!\n");
            }
        }
    }

    public void deleteFromDatabase(String table3, String rowName1, String rowValue1) {
        int counter1 = 0;
        try {
            statement.executeUpdate(deleteDatabaseSQLString(table3, rowName1, rowValue1));
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

    public void getUserCreatedID() {
        try {
            ResultSet rs = statement.executeQuery("SELECT id FROM users ");
            int numberOfRows = 0;
            while (rs.next()){
                numberOfRows++;
            }
            System.out.println(rs.getInt(numberOfRows-1 ));
        } catch (SQLException se){
            System.out.println(se);
        }

    }

    public boolean checkIfIDExists(int idInputted){
        boolean idExists = false;
        try {
            ResultSet rs = statement.executeQuery("SELECT id FROM users ");
            while(rs.next()){
                if (rs.getInt(1) == idInputted){
                    idExists = true;
                }
            }
        } catch (SQLException se){
            System.out.println(se);
        }
        return idExists;
    }
}


/*
    public int getUserCreatedID2(){
        int id_value = 0;
        try {
            ResultSet rs = statement.executeQuery("select * from users ");

            int numberOfRows = 0;
            while (rs.next()){
                numberOfRows++;
            }
            rs.absolute(1);
            System.out.println(numberOfRows);
            id_value = rs.getInt(numberOfRows-1);

        } catch (SQLException se) {
            System.out.println(se);
        }
        return id_value;

    }

 */

