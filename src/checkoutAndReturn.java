import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class checkoutAndReturn extends SQLConnector{
    // child class of SQLConnector

    public String[] bookCheckoutProcedure(int book_id){
        /*
        starts the procedure for checking out the book.
        goes through each book in the list to check if the book the user wants exists in the set
        if it does exist then the user is asked to confirm the book they want to checkout
        if true then the method takes the stock of the book, puts it in the 'bookProperties' array and reduces the value by 1.
         */
        Scanner scan = new Scanner(System.in);
        String[] bookProperties = new String[2];
        int rowCounter = 0;
        try {
            setResultSet("*" ,"books");
            while (rs.next()){
                rowCounter++;
                if (rs.getInt(1) == book_id){
                    rs.absolute(rowCounter);
                    System.out.println("Confirm book:\nAuthor: " + rs.getString(2) + "\nBook Name: " + rs.getString(3) + "\nStock: " + rs.getInt(4) + "\n");
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

    public boolean checkoutBookUpdateBookList(String @NotNull [] bookProperties, int book_id){
        // this method updates the stock of the book after 'bookCheckoutProcedure' is run.
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

    public void checkoutAndReturnBookUpdateUserList(int bookID, int userID, boolean toContinue1, boolean returningBook){
        /*
        this method is responsible for returning the book and checking out the book by setting the user's book checked out
        value to the book id if checking out, and to zero when the book is returned. This keeps track of what book the user
        has checked out through the id of the book.
         */
        if(toContinue1){
            try{
                setResultSet("*" , "users");
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

    public void searchInBookList(int id, String bookNameOrAuthor){
        // this method searches the book list when the book id, name, or author of book is passed through the function.
        int rowCounter2 = 1;
        boolean bookFound = false;
        try{
            setResultSet("*", "books");
            while(rs.next() && bookFound == false){
                rs.absolute(rowCounter2);
                if ((rs.getString(2).equals(bookNameOrAuthor)) || (rs.getString(3).equals(bookNameOrAuthor)) || rs.getInt(1) == id){
                    System.out.println("\nID-----------Author-----------Name-----------Stock");
                    System.out.println(rs.getInt(1) + "              " + rs.getString(2) + "              " + rs.getString(3) + "           " + rs.getInt(4) + "\n");
                    System.out.println("--------------------------------------------------");
                    return;
                }
                rowCounter2++;
            }
            if(!(bookFound)){
                System.out.println("This book is not in the library!");
            }
        } catch (SQLException se){
            System.out.println(se);
            System.out.println("Error Code 11");
        }
    }

    public boolean returnBook(int bookID){
        // this method manages the returning of a book from the user.
        int rowCounter = 1;
        boolean rowFound = false;
        try {
            setResultSet("id", "books");
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
        // after returning the book, the book list is updated by setting the updated stock
        String[] bookProperties = new String[2];
        int rowCounter = 0;
        try {
            setResultSet("*", "books");
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

    public void printBookList() {
        // prints out the entire book list
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
        /*
        checks if the user has a book
        this is important because if the user tries to return a book that they did not checkout, it can cause faulty
        stock in the system
         */
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
