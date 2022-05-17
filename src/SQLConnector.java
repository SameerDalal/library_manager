import java.sql.*;
import java.sql.DriverManager;
import java.util.Scanner;


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
            counter++;
            System.out.println("Error Code 3");
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
            System.out.println("Error Code 4");
        } finally {
            if (counter1 == 0) {
                System.out.println("Deleted from the Database!\n");
            } else {
                System.out.println("Data has NOT been successfully deleted!\n");
            }
        }
    }

    public int getUserCreatedID() {
        int userCreatedID = 0;
        String username = "";
        String userEmailAddress = "";

        int numberOfRows = 0;
        try {
            ResultSet rs = statement.executeQuery("SELECT * FROM users ");
            while (rs.next()){
                numberOfRows++;
            }
            rs.absolute(numberOfRows);
            userCreatedID = rs.getInt("id");
        } catch (SQLException se){
            System.out.println(se);
            System.out.println("Error Code 5");
        }
        System.out.println("Your unique ID: " + userCreatedID + "\n");
        return userCreatedID;
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
            System.out.println("Error Code 6");
        }
        return idExists;
    }

    public String[] bookCheckoutProcedure(int book_id){
        Scanner scan = new Scanner(System.in);
        String[] bookProperties = new String[2];
        int rowCounter = 0;
        try {
            ResultSet rs = statement.executeQuery("SELECT * FROM books ");
            while (rs.next()){
                rowCounter++;
                if (rs.getInt(1) == book_id){
                    rs.absolute(rowCounter);
                    System.out.println("Confirm book:\nAuthor: " + rs.getString(2) + "\nBook Name: " + rs.getString(3) + "\nStock: " +rs.getInt(4) + "\n");
                    System.out.println("1).Confirm\n2).Decline\n");
                    int confirmOrDecline = scan.nextInt();
                    if (confirmOrDecline == 1 && rs.getInt(4) > 0){
                        bookProperties[0] = String.valueOf(rs.getInt(4)-1);
                        bookProperties[1] = "True";
                    }
                }
            }
        } catch (SQLException se) {
            System.out.println(se);
            System.out.println("Error Code 7");
        }
        return bookProperties;
    }

    public boolean checkoutBookUpdateBookList(String[] bookProperties, int book_id){
        boolean toContinue = false;
        if (bookProperties[1] == "True"){
            try {
                statement.executeUpdate("UPDATE `books` SET `stock` = " + "'" + bookProperties[0] + "'" + " WHERE `books`.`id` = " + book_id);
                toContinue = true;
            } catch (SQLException se){
                System.out.println(se);
                System.out.println("Error Code 8");
            }
        }
        return toContinue;
    }

    public void checkoutBookUpdateUserList(int bookID, int userID, boolean toContinue1, boolean returningBook){
        if(toContinue1){
            try{
                ResultSet rs = statement.executeQuery("SELECT * FROM users ");
                if(rs.next()) {
                    statement.executeUpdate("UPDATE `users` SET `Book Checked Out` = " + "'" + bookID + "'" + " WHERE `users`.`id` = " + userID);
                    if (!returningBook) {
                        System.out.println("Successfully checked out the book!");
                    } else {
                        System.out.println("Successfully returned the book!");
                    }
                }
            } catch (SQLException se){
                System.out.println(se);
                System.out.println("Error Code 9");
            }
        }
    }

    public void printBookList(){
        System.out.println("ID-----------Author-----------Name-----------Stock");
        int rowCounter1 = 1;

        try {
            ResultSet rs = statement.executeQuery("SELECT * FROM books ");
            while (rs.next()){
                rs.absolute(rowCounter1);
                System.out.println(rs.getInt(1) + "              " + rs.getString(2) + "              " + rs.getString(3) + "              " + rs.getInt(4));
                System.out.println("---------------------------------------------------");
                rowCounter1++;
            }

        } catch (SQLException se){
            System.out.println(se);
            System.out.println("Error Code 10");
        }

    }

    public void searchInBookList(int id, String bookNameOrAuthor){
        int rowCounter2 = 1;
        boolean bookFound = false;
        try{
            ResultSet rs = statement.executeQuery("SELECT * FROM books ");
            while(rs.next() && bookFound == false){
                rs.absolute(rowCounter2);
                if ((rs.getString(2).equals(bookNameOrAuthor)) || (rs.getString(3).equals(bookNameOrAuthor)) || rs.getInt(1) == id){
                    System.out.println("\nID-----------Author-----------Name-----------Stock");
                    System.out.println(rs.getInt(1) + "              " + rs.getString(2) + "              " + rs.getString(3) + "           " + rs.getInt(4) + "\n");
                    System.out.println("--------------------------------------------------");
                    bookFound = true;
                }
                rowCounter2++;
            }
            if(!(bookFound)){
                System.out.println("This book is not in the library!");
            }
        }catch (SQLException se){
            System.out.println(se);
            System.out.println("Error Code 11");
        }
    }

    public boolean returnBook(int bookID){
        int rowCounter = 1;
        boolean rowFound = false;
        try {
            ResultSet rs = statement.executeQuery("SELECT id FROM books ");
            while (rs.next() && rowFound == false){
                rs.absolute(rowCounter);
                if (Integer.parseInt(rs.getString(1)) == (bookID)) {
                    rowFound = true;
                }
                rowCounter++;

            }
            if(!(rowFound)) {
                System.out.println("Failed to Return!");
            }
        } catch (SQLException se) {
            System.out.println(se);
            System.out.println("Error Code 12");
        }
        return rowFound;
    }

    public String[] returnBookUpdateBookList(int book_id){
        String[] bookProperties = new String[2];
        int rowCounter = 0;
        try {
            ResultSet rs = statement.executeQuery("SELECT * FROM books ");
            while (rs.next()){
                rowCounter++;
                if (rs.getInt(1) == book_id){
                    rs.absolute(rowCounter);
                    if (rs.getInt(4) > 0){
                        bookProperties[0] = String.valueOf(rs.getInt(4)+1);
                        bookProperties[1] = "True";
                    }
                }
            }
        } catch (SQLException se) {
            System.out.println(se);
            System.out.println("Error Code 13");
        }
        return bookProperties;

    }

    public boolean checkUserHasBook(int user_id, int bookid){
        boolean userHasBook = false;
        String bookIDFromTable = "";
        try{

            int rowCounter = 1;
            boolean rowFound = false;

            ResultSet rs = statement.executeQuery("SELECT * FROM users ");
            while (rs.next() && !(rowFound)){
                rs.absolute(rowCounter);
                if(rs.getInt(1) == user_id){
                    bookIDFromTable = rs.getString(4);
                    rowFound = true;
                }
                rowCounter++;
            }
        } catch (SQLException se){
            System.out.println(se);
            System.out.println("Error Code 14");
        }
        if(bookid == Integer.parseInt(bookIDFromTable)){
            userHasBook = true;
        }
        return userHasBook;
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