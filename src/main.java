import java.util.Scanner;

public class main {

    Scanner scan = new Scanner(System.in);
    SQLConnector ms = new SQLConnector();
    checkoutAndReturn checkAndReturn = new checkoutAndReturn();
    idIdentifiers ii =  new idIdentifiers();
    addToDataBase atd = new addToDataBase();
    public int id_number = 0;

    public static void main(String[] args){
        main m = new main();
        m.loginToUserAccount();
    }

    public void loginToUserAccount() {
        ms.connectToSQL();
        System.out.println("1). New User\n2). Existing User\n3). Library Staff - Add book to database\n4). Quit");
        switch (scan.nextInt()) {
            case (1):
                int id_value = 0;

                System.out.println("Enter your name: ");
                String name = scan.next();

                System.out.println("Enter your email address: ");
                String email_address = scan.next();

                String bookCheckedOut = "0";

                atd.addToDatabase(id_value, name, email_address, bookCheckedOut, "users ");

                id_number = ii.getUserCreatedID();

                goTo();
                break;

            case(2):
                System.out.println("To log into your account type your id number: ");
                id_number = scan.nextInt();

                boolean IDFalse = true;
                while (IDFalse) {
                    if (!(ii.checkIfIDExists(id_number))) {
                        System.out.println("The ID you entered is not in the database, try again.");
                        id_number = scan.nextInt();
                    } else {
                        IDFalse = false;
                    }
                }
                goTo();
                break;

            case (3):
                Scanner scan1 = new Scanner(System.in).useDelimiter("\n");
                System.out.println("ID:");
                int ID = scan1.nextInt();

                System.out.println("Author: ");
                String author = scan1.next();

                System.out.println("Name: ");
                String nameOfBook = scan1.next();

                System.out.println("Stock: ");
                String stock = scan1.next();

                atd.addToDatabase(ID, author, nameOfBook, stock , "books ");
                loginToUserAccount();
                break;

            case (4):
                scan.close();
                ms.SQLDisconnector();
                System.exit(0);
                break;
        }
    }

    public void goTo(){

        System.out.println("Options: \n1). Checkout a book\n2). Search for a book in the database\n3). Return a book\n4). Quit");
        switch (scan.nextInt()){
            case (1):
                System.out.println("Enter the book ID of the book you would like to checkout or enter '0' to return to the menu\n");
                int bookID = scan.nextInt();
                if (bookID == 0){
                    goTo();
                } else {
                    checkAndReturn.checkoutAndReturnBookUpdateUserList(bookID,id_number,checkAndReturn.checkoutBookUpdateBookList(checkAndReturn.bookCheckoutProcedure(bookID),bookID), false);
                }
                break;

            case (2):
                System.out.println("1). Print out entire book list\n2). Search book by ID, Author, or Book Name");
                int path = scan.nextInt();
                if(path == 1){
                    checkAndReturn.printBookList();
                } else {
                    System.out.println("Search by:\n1). ID\n2). Book Name Or Author");
                    int id = 0;
                    String bookNameOrAuthor = "";
                    if(scan.nextInt() == 1){
                        System.out.println("Enter ID: ");
                        id = scan.nextInt();
                    } else {
                        Scanner scan1 = new Scanner(System.in);
                        System.out.println("Enter Book Name or Book Author: ");
                        bookNameOrAuthor = scan1.nextLine();
                    }
                    checkAndReturn.searchInBookList(id,bookNameOrAuthor);
                }
                break;

            case (3):
                System.out.println("Enter the id of the book you are returning: ");
                bookID = scan.nextInt();
                if(checkAndReturn.returnBook(bookID)){
                    if (checkAndReturn.checkUserHasBook(id_number,bookID)) {
                        checkAndReturn.checkoutAndReturnBookUpdateUserList(0, id_number, true, true);
                        checkAndReturn.checkoutBookUpdateBookList(checkAndReturn.returnBookUpdateBookList(bookID), bookID);
                    } else {
                        System.out.println("Either you didn't check out this book or this book is not in the library! ");
                    }
                } else {
                    System.out.println("Did not successfully return! ");
                }
                break;
            case (4):
                scan.close();
                ms.SQLDisconnector();
                System.exit(0);
                break;
        }
        goTo();
    }
    // calling the 'goTo' method recursively, at a large scale could cause a stack overflow error
    // before the method is completed, the same method is added to the call stack resulting in the overflow
}