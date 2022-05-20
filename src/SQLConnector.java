import java.sql.*;
import java.sql.DriverManager;

public class SQLConnector {
    //parent class
    static Connection connection = null;
    static Statement statement = null;
    static ResultSet rs;

    public void connectToSQL() {
        // registering JDBC Driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Connecting to Database...\n");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_database", "root", "");
            System.out.println("Connected to Database Successfully!\n");
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        } catch (Exception e) {
            System.out.println(e + "\n");
            System.out.println("Error Code 1");
        }
    }

    public void SQLDisconnector() {
        try {
            // disconnecting JDBC Driver
            connection.close();
        } catch (SQLException se) {
            System.out.println(se + "\n");
            System.out.println("Disconnected from Database unsuccessfully!\n");
            System.out.println("Error Code 2");
        }
    }

    public void setResultSet(String whatToSelect, String tableName) {
        // sets what the ResultSet Object should be. This method is called upon to set a new value
        // to the result set instead of creating new ResultSet Objects each time you need to execute a query
        try {
            rs = statement.executeQuery("SELECT " + whatToSelect + " FROM " + tableName + " ");
        } catch (SQLException se) {
            System.out.println(se);
        }
    }
}