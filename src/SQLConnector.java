import java.sql.*;
import java.sql.DriverManager;

public class SQLConnector {

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
            connection.close();
        } catch (SQLException se) {
            System.out.println(se + "\n");
            System.out.println("Disconnected from Database unsuccessfully!\n");
            System.out.println("Error Code 2");
        }
    }

    public void setResultSet(String whatToSelect, String tableName) {
        try {
            rs = statement.executeQuery("SELECT " + whatToSelect + " FROM " + tableName + " ");
        } catch (SQLException se) {
            System.out.println(se);
        }
    }

    public void printBookList() {
        System.out.println("ID-----------Author-----------Name-----------Stock");
        int rowCounter1 = 1;
        try {
            ResultSet rs = statement.executeQuery("SELECT * FROM books ");
            while (rs.next()) {
                rs.absolute(rowCounter1);
                System.out.println(rs.getInt(1) + "              " + rs.getString(2) + "              " + rs.getString(3) + "              " + rs.getInt(4));
                System.out.println("---------------------------------------------------");
                rowCounter1++;
            }
        } catch (SQLException se) {
            System.out.println(se);
            System.out.println("Error Code 10");
        }
    }

    public boolean checkUserHasBook(int user_id, int bookid) {
        boolean userHasBook = false;
        String bookIDFromTable = "";
        try {
            int rowCounter = 1;
            boolean rowFound = false;

            ResultSet rs = statement.executeQuery("SELECT * FROM users ");
            while (rs.next() && !(rowFound)) {
                rs.absolute(rowCounter);
                if (rs.getInt(1) == user_id) {
                    bookIDFromTable = rs.getString(4);
                    rowFound = true;
                }
                rowCounter++;
            }
        } catch (SQLException se) {
            System.out.println(se);
            System.out.println("Error Code 14");
        }
        if (bookid == Integer.parseInt(bookIDFromTable)) {
            userHasBook = true;
        }
        return userHasBook;
    }
}