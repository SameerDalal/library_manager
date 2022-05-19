import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class checkoutAndReturn extends SQLConnector{

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

    public boolean checkoutBookUpdateBookList(String @NotNull [] bookProperties, int book_id){
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

}
