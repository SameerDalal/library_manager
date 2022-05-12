import java.util.Scanner;

public class main {

    Scanner scan = new Scanner(System.in);
    SQLConnector ms = new SQLConnector();
    public int id_number = 0;


    public static void main(String[] args){
        main m = new main();
        m.loginToUserAccount();


        //EmailSender em = new EmailSender();
        //em.sendEmail("sameerdalal747@gmail.com", "hello", "test");
    }

    public void loginToUserAccount() {
        ms.SQLConnector();

        System.out.println("1). New User\n2). Existing User\n3). Library Staff - Add book to database\n4). Quit");

        switch (scan.nextInt()) {
            case (1):

                int id_value = 0;

                System.out.println("Enter your name: ");
                String name = scan.next();

                System.out.println("Enter your email address: ");
                String email_address = scan.next();

                String bookCheckedOut = "0";

                ms.addToDatabase(id_value, name, email_address, bookCheckedOut, "users ");

                id_number = ms.getUserCreatedID();

                goTo();
                break;

            case(2):
                System.out.println("To log into your account type your id number: ");
                id_number = scan.nextInt();

                boolean IDFalse = true;
                while (IDFalse) {
                    if (!(ms.checkIfIDExists(id_number))) {
                        System.out.println("The ID you entered is not in the database, try again.");
                        id_number = scan.nextInt();
                    } else {
                        IDFalse = false;
                    }
                }
                goTo();
                break;
            case (3):
                System.out.println("ID:");
                int ID = scan.nextInt();

                System.out.println("Author: ");
                String author = scan.next();

                System.out.println("Name: ");
                String nameOfBook = scan.next();

                System.out.println("Stock: ");
                String stock = scan.next();

                ms.addToDatabase(ID, author, nameOfBook, stock , "books ");
                goTo();
                break;
            case (4):
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
                    ms.checkoutBookUpdateUserList(bookID,id_number,ms.checkoutBookUpdateBookList(ms.checkoutProcedure(bookID),bookID));
                    goTo();
                }
                break;
            case (2):
                System.out.println("1). Print out entire book list\n2). Search book by ID, Author, or Book Name");
                int path = scan.nextInt();
                if(path == 1){
                    ms.printBookList();
                } else {
                    System.out.println("Search by:\n1). ID\n2). Book Name Or Author");
                    int id = 0;
                    String bookNameOrAuthor = "";
                    if(scan.nextInt() == 1){
                        System.out.println("Enter ID: ");
                        id = scan.nextInt();
                    } else {
                        System.out.println("Enter Book Name or Book Author: ");

                        bookNameOrAuthor = scan.nextLine();
                        //this line does not work. For some reason scan.nextLine() prints out spaces and skips the option for user input.
                        System.out.println(bookNameOrAuthor);
                    }

                    ms.searchInBookList(id,bookNameOrAuthor);


                }
                goTo();

                break;
            case (3):
                // return a book
                break;
            case (4):
                System.exit(0);
                break;
        }
        ms.SQLDisconnector();
    }
}